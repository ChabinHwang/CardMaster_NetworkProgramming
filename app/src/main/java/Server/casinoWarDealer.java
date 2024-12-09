package Server;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class casinoWarDealer implements dealerI {
    private final room room;
    private final Deck deck = new Deck();
    private final List<Card> dealerCards = new CopyOnWriteArrayList<>();
    private final Map<String, List<Card>> playerHands = new ConcurrentHashMap<>();
    private final Map<String, Integer> currentBets = new ConcurrentHashMap<>();
    private final AtomicBoolean playerAct = new AtomicBoolean(false);
    private volatile client playerTurn;
    private ScheduledExecutorService timerExecutor;
    private int roundTime = 30;
    private CountDownLatch counter;
    private messageGenerator mg;

    public casinoWarDealer(room room, messageGenerator mg){
        this.room = room;
        this.mg = mg;
    }

    public client checkPlayerTurn(){
        return this.playerTurn;
    }

    public void changePlayerTurn(client player){
        this.playerTurn = player;
    }

    public void play(List<client> players, Map<String, Boolean> activePlayers, int numberOfActivePlayer){
        timerExecutor = Executors.newScheduledThreadPool(1);
        deck.reset();
        deck.shuffle();
        waitForAct(players, activePlayers);
        dealInitialCards(players, activePlayers);
        playRounds("");
        waitForAct(players, activePlayers);
        timerExecutor.shutdown();
    }
    private void dealInitialCards(List<client> players, Map<String, Boolean> activePlayers){
        dealerCards.add(deck.drawCard());
        try{
            for(client player : players){
                if(!activePlayers.get(player.getName())) continue;
                List<Card> hand = new ArrayList<>();
                hand.add(deck.drawCard());
                playerHands.put(player.getName(),hand);
                player.sendMessage(mg.casinoWarCard(playerHands.get(player.getName())).toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void playRounds(String action) {
        int prize;
        String result;
        try {
            int playerScore = playerHands.get(playerTurn.getName()).getFirst().getRank();
            if (playerScore == 1) playerScore = 14;
            int dealerScore = dealerCards.getFirst().getRank();
            if (playerScore > dealerScore) {
                result = "win";
                prize = currentBets.get(playerTurn.getName()) * 2;
                playerTurn.getUserInstance().addMoney(currentBets.get(playerTurn.getName()) * 2);
                playerTurn.sendMessage(mg.gameResult(prize, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
                playerAct.set(true);
            } else if (playerScore < dealerScore) {
                result = "lose";
                prize = 0;
                playerTurn.sendMessage(mg.gameResult(prize, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
                playerAct.set(true);
            } else {
                result = "war";
                prize = 0;
                playerTurn.sendMessage(mg.gameResult(prize, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
            }
            JSONObject response = new JSONObject();
            JSONObject data = new JSONObject();
            response.put("response", "gameResult");
            data.put("result", result)
                    .put("playerCards", playerHands.get(playerTurn.getName()))
                    .put("dealerCards", dealerCards)
                    .put("prize", prize)
                    .put("player", playerTurn.getName());
            response.put("data", data);
            room.broadcast(response.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void resolveWar(String gotoWar, int additionalBet){
        int totalBet = currentBets.get(playerTurn.getName());
        String result;
        int prize;
        try{
            if(gotoWar.equals("true")){
                totalBet += additionalBet;
                playerHands.get(playerTurn.getName()).add(deck.drawCard());
                dealerCards.add(deck.drawCard());
                int playerScore = playerHands.get(playerTurn.getName()).getLast().getRank();
                if(playerScore==1) playerScore = 14;
                int dealerScore = dealerCards.getLast().getRank();
                if(playerScore > dealerScore){
                    result = "win";
                    prize = totalBet*2;
                    playerTurn.getUserInstance().addMoney(prize);
                }else if(playerScore > dealerScore){
                    result = "lose";
                    prize = 0;
                }else{
                    result = "draw";
                    prize = totalBet;
                    playerTurn.getUserInstance().addMoney(totalBet);
                }
            }else{
                result = "surrender";
                prize = totalBet/2;
            }
            playerTurn.sendMessage(mg.gameResult(prize, result, playerHands.get(playerTurn.getName()), dealerCards, room.getGameId()).toString());
            playerAct.set(true);
        }catch(Exception e){
            e.printStackTrace();
        }
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

    private void waitForAct(List<client> players, Map<String, Boolean> activePlayers){
        try{
            for(client player : players) {
                if (!activePlayers.get(player.getName())) continue;
                playerTurn = player;
                player.sendMessage(mg.errorMessage("your turn!").toString());
                this.counter = new CountDownLatch(1);
                ScheduledFuture<?> future = timerExecutor.scheduleAtFixedRate(() -> {
                    if (roundTime > 0 && !playerAct.get() && !players.isEmpty()) {
                        room.broadcastTimer(roundTime, room.getGameId());
                        roundTime--;
                    } else {
                        if (roundTime <= 0) handleTimeouts(player, activePlayers);
                        roundTime = 30;
                        playerAct.set(false);
                        room.broadcastTimer(roundTime, room.getGameId());
                        counter.countDown();
                    }
                }, 0, 1, TimeUnit.SECONDS);
                try {
                    counter.await();
                    future.cancel(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
