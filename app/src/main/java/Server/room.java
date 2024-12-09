package Server;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class room extends Thread{
    private final String roomName;
    private final List<client> players = new CopyOnWriteArrayList<>();
    private final Map<String, Boolean> activePlayers = new ConcurrentHashMap<>();
    private volatile client leader;
    private volatile dealerI dealer;
    private int maxPlayer = 8;
    private AtomicBoolean gameInProgress = new AtomicBoolean(false);
    private volatile String phase;
    private final messageGenerator mg;
    private final int gameId;

    public room(String roomName, messageGenerator mg, int gameId){
        this.mg = mg;
        this.roomName = roomName;
        this.gameId = gameId;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public Map<String, Boolean> getActivePlayers(){return this.activePlayers;}

    public boolean join(client client){
        if(numberOfPlayer() == 0) {
            leader = client;
        }
        broadcast(mg.updateRoomState(this).toString());
        if(numberOfPlayer() > maxPlayer){
            client.sendMessage(mg.errorMessage("enter Failed").toString());
            return false;
        }
        if(gameInProgress.get()){
            players.add(client);
            activePlayers.put(client.getName(), false);
        }else{
            players.add(client);
            activePlayers.put(client.getName(), true);
        }
        client.sendMessage(mg.updatePlayerState(activePlayers.get(client.getName())).toString());
        return true;
    }

    public void leave(client player){
        int nextPlayerIndex = -1;
        for(int i=0; i<players.size(); i++){
            if(player==players.get(i)){
                nextPlayerIndex = i+1;
                break;
            }
        }
        if(nextPlayerIndex!=-1&&players.size()>nextPlayerIndex){
            dealer.changePlayerTurn(players.get(nextPlayerIndex));
        }
        players.remove(player);
        activePlayers.remove(player.getName());
        if(player==leader&&!players.isEmpty()){
            leader = players.getFirst();
        }
    }

    public void broadcast(String message){
        if(!players.isEmpty()){
            for(client client : players){
                client.sendMessage(message);
            }
        }
    }

    public int numberOfPlayer(){
        return players.size();
    }

    public int getGameId(){
        return gameId;
    }

    public void run(){
        while(true){
            activePlayers.clear();
            for(client player : players){
                activePlayers.put(player.getName(),true);
            }
            if(gameId==0){
                dealer = new blackJackDealer(this, mg);
            }else if(gameId==1){
                dealer = new casinoWarDealer(this, mg);
            }else{
                dealer = new baccaratDealer(this, mg);
            }
            gameInProgress.set(true);
            dealer.play(players, activePlayers, numberOfActivePlayer());
            gameInProgress.set(false);
            while(!gameInProgress.get());
        }
    }

    public boolean isLeader(client player){
        return leader == player;
    }

    public void changeRoomState(boolean flag){
        gameInProgress.set(flag);
    }

    private int numberOfActivePlayer(){
        int tmp = 0;
        for(client player : players){
            if(activePlayers.get(player.getName()))tmp++;
        }
        return tmp;
    }

    public void broadcastTimer(int remainingTime, int gameId){
        JSONObject message = new JSONObject();
        message.put("response", "timer_update");
        message.put("data", new JSONObject().put("remaining_time", remainingTime)
                .put("gameId", gameId));
        broadcast(message.toString());
    }

    public void broadcastGameUpdate(String playerTurn, int currentBet){
        broadcast(mg.gameUpdate(playerTurn, currentBet).toString());
    }

    public void handleClientMessage(String clientMessage, client player){
        try{
            JSONObject requestRoot = new JSONObject(clientMessage);
            String request = requestRoot.getString("request");
            JSONObject data = requestRoot.getJSONObject("data");
            switch(request){
                case "bet":
                    if(!gameInProgress.get()) {
                        player.sendMessage(mg.errorMessage("not playing time").toString());
                        return;
                    }
                    if(!Objects.equals(dealer.checkPlayerTurn().getName(), player.getName())){
                        player.sendMessage(mg.errorMessage("not your turn").toString());
                        return;
                    }
                    int amount = data.optInt("amount", 0);
                    String bet = data.getString("bet");
                    dealer.handleBet(amount, bet);
                    break;
                case "hitOrStand":
                    if(!gameInProgress.get()) {
                        player.sendMessage(mg.errorMessage("not playing time").toString());
                        return;
                    }
                    if(!Objects.equals(dealer.checkPlayerTurn().getName(), player.getName())){
                        player.sendMessage(mg.errorMessage("not your turn").toString());
                        return;
                    }
                    String hitOrStand = data.getString("hitOrStand");
                    dealer.playRounds(hitOrStand);
                    break;
                case "call":
                    if(!gameInProgress.get()) {
                        player.sendMessage(mg.errorMessage("not playing time").toString());
                        return;
                    }
                    int additionalBet = data.optInt("amount", 0);
                    String gotoWar = data.getString("gotoWar");
                    casinoWarDealer casinodealer = (casinoWarDealer)dealer;
                    casinodealer.resolveWar(gotoWar, additionalBet);
                    break;
                case "leave":
                    leave(player);
                    player.sendMessage(mg.leaveRoomResponse(player.getUserInstance()).toString());
                    break;
                case "message":
                    for(client client : players){
                        client.sendMessage(mg.sendMessage(data.getString("message"), gameId, player.getUserInstance().getId()).toString());
                    }
                    break;
                default:
                    player.sendMessage(mg.errorMessage("unknown meesage type").toString());
            }
        }catch(Exception e){
            e.printStackTrace();
            player.sendMessage(mg.errorMessage("unknown meesage type").toString());
        }
    }
}
