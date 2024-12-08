package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class baccaratDealer implements dealerI{
    private final room room;
    private final Deck deck = new Deck();
    private final List<Card> dealerCards = new CopyOnWriteArrayList<>();
    private final Map<String, List<Card>> playerHands = new ConcurrentHashMap<>();
    private final Map<String, String> betting = new ConcurrentHashMap<>();
    private final Map<String, Integer> currentBets = new ConcurrentHashMap<>();
    private final AtomicBoolean playerAct = new AtomicBoolean(false);
    private volatile client playerTurn;
    private ScheduledExecutorService timerExecutor;
    private int roundTime = 30;
    private CountDownLatch counter;
    private messageGenerator mg;

    public baccaratDealer(room room, messageGenerator mg){
        this.room = room;
        this.mg = mg;
    }

    public client checkPlayerTurn(){
        return this.playerTurn;
    }

    public void play(List<client> players, Map<String, Boolean> activePlayers, int numberOfActivePlayer){
        timerExecutor = Executors.newScheduledThreadPool(1);
        deck.reset();
        deck.shuffle();
        waitForAct(players, activePlayers);
        dealInitialCards(players, activePlayers);
        for(client player : players){
            playerTurn = player;
            if(!activePlayers.get(player.getName())) continue;
            playRounds("");
        }
    }

    private void dealInitialCards(List<client> players, Map<String, Boolean> activePlayers){
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
    }

    private int calculateScore(List<Card> cards){
        int score = 0;
        for (Card card : cards) {
            score += card.getRank();
        }
        return score % 10;
    }

    public void handleBet(int amount, String bet){
        if(playerTurn.getUserInstance().getMoney()<amount){
            playerTurn.sendMessage(mg.errorMessage("not enough money").toString());
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
        int playerScore = calculateScore(playerHands.get(playerTurn.getName()));
        int bankerScore = calculateScore(dealerCards);
        String result;
        int prize = 0;
        if(playerScore == bankerScore){
            result = "Tie";
        }else if(playerScore>bankerScore){
            result = "Player";
        }else{
            result = "Banker";
        }
        if(Objects.equals(betting.get(playerTurn.getName()), result)){
            if(result=="Tie"){
                prize = currentBets.get(playerTurn.getName())*8;
                playerTurn.getUserInstance().addMoney(currentBets.get(playerTurn.getName())*8);
            }else{
                prize = currentBets.get(playerTurn.getName())*2;
                playerTurn.getUserInstance().addMoney(currentBets.get(playerTurn.getName())*2);
            }
        }
        playerTurn.sendMessage(mg.gameResult(prize, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
    }

    private void waitForAct(List<client> players, Map<String, Boolean> activePlayers){
        for(client player : players){
            if(!activePlayers.get(player.getName())) continue;
            playerTurn = player;
            player.sendMessage(mg.errorMessage("your turn!").toString());
            this.counter = new CountDownLatch(1);
            ScheduledFuture<?> future = timerExecutor.scheduleAtFixedRate(()->{
                if(roundTime > 0 && !playerAct.get()){
                    room.broadcastTimer(roundTime, room.getGameId());
                    roundTime--;
                }else{
                    if(roundTime<=0) handleTimeouts(player, activePlayers);
                    roundTime = 30;
                    playerAct.set(false);
                    room.broadcastTimer(roundTime, room.getGameId());
                    counter.countDown();
                }
            },0,1, TimeUnit.SECONDS);
            try{
                counter.await();
                future.cancel(true);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
