package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class blackJackDealer implements dealerI{
    private final room room;
    private final messageGenerator mg;
    private final Deck deck = new Deck();
    private final List<Card> dealerCards = new CopyOnWriteArrayList<>();
    // playerHands: 플레이어별 카드 목록. (플레이어 이름을 키로 가지는 맵)
    private final Map<String, List<Card>> playerHands = new ConcurrentHashMap<String, List<Card>>();
    // 플레이어 현재 배팅
    private final Map<String, Integer> currentBets = new ConcurrentHashMap<>();
    // 현재 턴에서 플레이어가 행동했는지 여부를 나타내는 플래그.
    private final AtomicBoolean playerAct = new AtomicBoolean(false);
    private final AtomicBoolean turnChanged = new AtomicBoolean(false);
    private volatile client playerTurn;
    private ScheduledExecutorService timerExecutor;
    // roundTime: 한 턴의 제한 시간(기본값: 30초).
    private volatile int roundTime = 30;
    //counter: 현재 턴의 대기 상태를 제어하는 래치.
    private CountDownLatch counter;

    public blackJackDealer(room room, messageGenerator mg) {
        this.room = room;
        this.mg = mg;
    }

    public client checkPlayerTurn(){
        return this.playerTurn;
    }

    public void changePlayerTurn(client player){
        turnChanged.set(true);
        this.playerTurn = player;
    }

    public void play(List<client> players, Map<String, Boolean> activePlayers, int numberOfActivePlayer){
        timerExecutor = Executors.newScheduledThreadPool(1);
        deck.reset();
        deck.shuffle();
        waitForAct(players, activePlayers);
        dealInitialCards(players, activePlayers);
        waitForAct(players, activePlayers);
        timerExecutor.shutdown();
    }

    /**
     * 딜러카드와 유저 카드를 배분하고, 이를 클라이언트에 발신
     * @param players 플레이어
     */
    private void dealInitialCards(List<client> players, Map<String, Boolean> activePlayers){
        try{
            dealerCards.add(deck.drawCard());
            dealerCards.add(deck.drawCard());
            for(client player : players){
                if(!activePlayers.get(player.getName())) continue;
                List<Card> hand = new ArrayList<>();
                hand.add(deck.drawCard());
                hand.add(deck.drawCard());
                playerHands.put(player.getName(), hand);
                player.sendMessage(mg.sendDealerPlayerCard(hand, dealerCards, room.getGameId()).toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private int getHandValue(List<Card> hand) {
        int value = 0;
        int aceCount = 0;
        for (Card card : hand) {
            if(card.getValue()>=10){
                value += 10;
            }else if(card.getValue()==1){
                value += 11;
                aceCount++;
            }else{
                value += card.getValue();
            }
        }
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    public void handleBet(int amount, String bet){
        if(playerTurn.getUserInstance().getMoney()<amount){
            playerTurn.sendMessage(mg.errorMessage("잔액 부족").toString());
            return;
        }
        playerTurn.getUserInstance().betMoney(amount);
        currentBets.put(playerTurn.getName(), amount);
        room.broadcastGameUpdate(playerTurn.getUserInstance().getId(), amount);
        playerAct.set(true);
    }

    private void handleTimeouts(client player, Map<String, Boolean> activePlayers){
        activePlayers.remove(player.getName());
        activePlayers.put(player.getName(),false);
        player.sendMessage(mg.errorMessage("timeout").toString());
    }

    public void playRounds(String action){
        try{
            System.out.println(playerTurn.getName());
            int playerValue = getHandValue(playerHands.get(playerTurn.getName()));
            int dealerValue = getHandValue(dealerCards);
            int prize = 0;
            String result;
            if(action.equals("hit")){
                playerHands.get(playerTurn.getName()).add(deck.drawCard());
                int handValue = getHandValue(playerHands.get(playerTurn.getName()));
                if(handValue>21){
                    result = "bust";
                    playerTurn.sendMessage(mg.gameResult(0, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
                    playerAct.set(true);
                    return;
                }
                playerValue = getHandValue(playerHands.get(playerTurn.getName()));
                dealerValue = getHandValue(dealerCards);
            }else if(action.equals("stand")) {
                while (getHandValue(dealerCards) < 17) {
                    dealerCards.add(deck.drawCard());
                }
            }
            if(dealerValue > 21 || (playerValue > dealerValue)&&(playerValue <= 21)){
                result = "win";
                prize = currentBets.get(playerTurn.getName())*2;
                playerTurn.getUserInstance().addMoney(prize);
                playerTurn.sendMessage(mg.updateMoney(prize).toString());
            }else if(playerValue==dealerValue){
                result = "push";
                prize = currentBets.get(playerTurn.getName());
                playerTurn.getUserInstance().addMoney(prize);
                playerTurn.sendMessage(mg.updateMoney(prize).toString());
            }else{
                result = "lose";
                prize = 0;
            }
            playerAct.set(true);
            playerTurn.sendMessage(mg.gameResult(prize, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    private void waitForAct(List<client> players, Map<String, Boolean> activePlayers){
        try{
            for(client player : players){
                if(!activePlayers.get(player.getName())) continue;
                playerTurn = player;
                System.out.println(playerTurn.getName());
                player.sendMessage(mg.errorMessage("your turn!").toString());
                this.counter = new CountDownLatch(1);
                ScheduledFuture<?> future = timerExecutor.scheduleAtFixedRate(()->{//비동기로 진행
                    if(roundTime > 0 && !playerAct.get() && !players.isEmpty() && !turnChanged.get()){
                        room.broadcastTimer(roundTime, room.getGameId());
                        roundTime-=2;
                    }else{
                        if(roundTime<=0) handleTimeouts(player, activePlayers);
                        roundTime = 30;
                        playerAct.set(false);
                        turnChanged.set(false);
                        room.broadcastTimer(roundTime, room.getGameId());
                        counter.countDown();
                    }
                },0,2, TimeUnit.SECONDS);
                try{
                    counter.await();//비동기로 진행되는 위 코드에서 countDown 내리면 이게 진행
                    future.cancel(true);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
