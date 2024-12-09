package src.cardmaster.main_signup_login;

import Server.Card;
import Server.gameList;
import com.mysql.cj.xdevapi.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import src.cardmaster.chat.ChatPanel;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientNetwork extends Thread{
    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;
    private ClientUI cu;
    private int money;
    public ClientNetwork(ClientUI cu){
        this.cu = cu;try{
            sock = new Socket(cu.ip, 10000);
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        }catch(IOException e){
            System.out.println("[client] network error" + e.toString());
        }
        start();
    }

    @Override
    public void run(){
        try{
            String data;
            while((data=br.readLine())!=null){
                JSONObject responseRoot = new JSONObject(data);
                System.out.println(responseRoot);
                String response = responseRoot.getString("response");
                JSONObject responseData = responseRoot.getJSONObject("data");
                int gameId;
                String name;
                int money;
                ArrayList<String> player = new ArrayList<String>();
                ArrayList<String> dealer = new ArrayList<String>();
                switch (response){
                    case "loginSuccess":
                        int blackJackPlayer = responseData.getInt("game0");
                        int casinoPlayer = responseData.getInt("game1");
                        int baccaratPlayer = responseData.getInt("game2");
                        name = responseData.getString("name");
                        money = responseData.getInt("money");
                        this.money = money;
                        int numberOfGames = responseData.getInt("numberOfGames");
                        cu.setLocationRelativeTo(null);
                        cu.setSize(800, 500);
//                        cu.menuFrame.menuPanel.update(blackJackPlayer, baccaratPlayer, casinoPlayer);
                        cu.menuFrame.welcomePanel.welcomeLabel.setText("<html><b>"+name+"</b> 님, 환영합니다.</html>");
                        cu.menuFrame.welcomePanel.pointsLabel.setText("잔액 : "+money+" points");
                        cu.setContentPane(cu.menuFrame);
                        break;
                    case "registerSuccess":
                        JOptionPane.showMessageDialog(cu, responseData.getString("message"));cu.setTitle("main");
                        cu.setLocationRelativeTo(null);
                        cu.setSize(800, 600);
                        cu.setContentPane(cu.homeScreen);
                        break;
                    case "roomList":
                        JSONObject request = new JSONObject();
                        JSONObject rqData = new JSONObject();
                        rqData.put("roomId", 0);
                        request.put("request", "selectRoom");
                        request.put("data", rqData);
                        pw.println(request.toString());
                        pw.flush();
                        break;
                    case "roomState":
                        int numberOfPlayer = responseData.getInt("numberOfPlayer");
                        gameId = responseData.getInt("gameId");
                        String roomName = responseData.getString("roomName");
                        if(gameId==0){
                            cu.setTitle("BlackJack");
                            cu.setLocationRelativeTo(null);
                            cu.setSize(800, 600);
                            cu.blackjack.balanceLabel.setText("잔액: "+this.money);
                            cu.setContentPane(cu.blackjack);
                        }else if(gameId==1){
                            cu.setTitle("CasinoWar");
                            cu.setLocationRelativeTo(null);
                            cu.setSize(800, 600);
                            cu.casinowar.balanceLabel.setText("잔액: "+this.money);
                            cu.setContentPane(cu.casinowar);
                        }else {
                            cu.setTitle("Baccarat");
                            cu.setLocationRelativeTo(null);
                            cu.setSize(800, 600);
                            cu.baccarat.balanceLabel.setText("잔액: "+this.money);
                            cu.setContentPane(cu.baccarat);
                        }
                        break;
                    case "timer_update":
                        int remainingTime = responseData.getInt("remaining_time");
                        gameId = responseData.getInt("gameId");
                        if(gameId==0){
                            cu.blackjack.timerLabel.setText(String.valueOf(remainingTime));
                        }else if(gameId==1){
                            cu.casinowar.timerLabel.setText(String.valueOf(remainingTime));
                        }else {
                            cu.baccarat.timerLabel.setText(String.valueOf(remainingTime));
                        }
                        break;
                    case "error":
                        JOptionPane.showMessageDialog(cu, responseData.getString("message"));
                        break;
                    case "gameUpdate":
                        String playerTurn = responseData.getString("playerTurn");
                        int currentBet = responseData.getInt("currentBet");
                        JOptionPane.showMessageDialog(cu, playerTurn + ": bet" + ": "+currentBet);
                        break;
                    case "dealerPlayerCards":
                        JSONArray playerCards = responseData.getJSONArray("playerCard");
                        JSONObject dealerCards = responseData.getJSONObject("dealerCard");
                        gameId = responseData.getInt("gameId");
                        for(int i=0; i<playerCards.length(); i++){
                            JSONObject card = playerCards.getJSONObject(i);
                            player.add(card.getString("suit")+card.getInt("rank"));
                        }
                        dealer.add(dealerCards.getString("suit") + dealerCards.getInt("rank"));
                        cu.setSize(800, 750);
                        if(gameId==0){
                            cu.blackjack.frame.setSize(800, 750);
                            cu.blackjack.updateDealerPanel(cu.blackjack.dealerPanel, dealer, false);
                            cu.blackjack.updatePlayerPanel(cu.blackjack.playerPanel, player);
                        }else if(gameId==1){
                            cu.casinowar.frame.setSize(800, 750);
                            cu.casinowar.updateDealerPanel(cu.casinowar.dealerPanel, dealer);
                            cu.casinowar.updatePlayerPanel(cu.casinowar.playerPanel, player);
                        }else {
                            cu.baccarat.frame.setSize(800, 750);
                            cu.baccarat.updateCardPanel(cu.baccarat.dealerPanel, dealer, "dealer");
                            cu.baccarat.updateCardPanel(cu.baccarat.playerPanel, player, "player");
                        }
                        break;
                    case "cainoWarCard":
                        JSONArray playerCard = responseData.getJSONArray("playerCard");
                        for(int i=0; i<playerCard.length(); i++){
                            JSONObject card = playerCard.getJSONObject(i);
                            player.add(card.getString("suit")+card.getInt("rank"));
                        }
                        cu.setSize(800, 750);
                        cu.casinowar.frame.setSize(800, 750);
                        cu.casinowar.updatePlayerPanel(cu.casinowar.playerPanel, player);
                        break;
                    case "gameResult":
                        JSONArray resultPCard = responseData.getJSONArray("playerCard");
                        JSONArray resultDCard = responseData.getJSONArray("dealerCard");
                        gameId = responseData.getInt("gameId");
                        String result = responseData.getString("result");
                        this.money += responseData.getInt("prize");
                        for(int i=0; i<resultPCard.length(); i++){
                            JSONObject card = resultPCard.getJSONObject(i);
                            player.add(card.getString("suit")+card.getInt("rank"));
                        }
                        for(int i=0; i<resultDCard.length(); i++){
                            JSONObject card = resultDCard.getJSONObject(i);
                            dealer.add(card.getString("suit")+card.getInt("rank"));
                        }
                        cu.setSize(800, 750);
                        if(gameId==0){
                            cu.blackjack.frame.setSize(800, 750);
                            cu.blackjack.updateDealerPanel(cu.blackjack.dealerPanel, dealer, true);
                            cu.blackjack.updatePlayerPanel(cu.blackjack.playerPanel, player);
                            cu.blackjack.balanceLabel.setText("잔액: "+this.money);
                        }else if(gameId==1){
                            cu.casinowar.frame.setSize(800, 750);
                            cu.casinowar.updateDealerPanel(cu.casinowar.dealerPanel, dealer);
                            cu.casinowar.updatePlayerPanel(cu.casinowar.playerPanel, player);
                            cu.casinowar.balanceLabel.setText("잔액: "+this.money);
                        }else {
                            cu.baccarat.frame.setSize(800, 750);
                            cu.baccarat.updateCardPanel(cu.baccarat.dealerPanel, dealer, "dealer");
                            cu.baccarat.updateCardPanel(cu.baccarat.playerPanel, player, "player");
                            cu.baccarat.balanceLabel.setText("잔액: "+this.money);
                        }
                        JOptionPane.showMessageDialog(cu, result);
                        break;
                    case "message":
                        String message = responseData.getString("message");
                        gameId = responseData.getInt("gameId");
                        String userId = responseData.getString("userId");
                        if(gameId==0){
                            ((ChatPanel)cu.blackjack.chatPanel).chatArea.append(userId+":"+message);
                            ((ChatPanel)cu.blackjack.chatPanel).scrollPane.revalidate();
                            ((ChatPanel)cu.blackjack.chatPanel).scrollPane.repaint();
                        }else if(gameId==1){
                            ((ChatPanel)cu.casinowar.chatPanel).chatArea.append(userId+":"+message);
                            ((ChatPanel)cu.casinowar.chatPanel).scrollPane.revalidate();
                            ((ChatPanel)cu.casinowar.chatPanel).scrollPane.repaint();
                        }else {
                            ((ChatPanel)cu.baccarat.chatPanel).chatArea.append(userId+":"+message);
                            ((ChatPanel)cu.baccarat.chatPanel).scrollPane.revalidate();
                            ((ChatPanel)cu.baccarat.chatPanel).scrollPane.repaint();
                        }
                        break;
                    case "leaveRoomResponse":
                        name = responseData.getString("name");
                        money = responseData.getInt("money");
                        cu.setLocationRelativeTo(null);
                        cu.setSize(800, 500);
                        this.money = money;
                        cu.menuFrame.welcomePanel.welcomeLabel.setText("<html><b>"+name+"</b> 님, 환영합니다.</html>");
                        cu.menuFrame.welcomePanel.pointsLabel.setText("잔액 : "+money+" points");
                        cu.setContentPane(cu.menuFrame);
                }

            }
        }catch(Exception ex){System.out.println(ex);}
    }
    public void sendCreateRequest(String id, String pw){
        if(id.trim().equals("")||pw.trim().equals("")){
            JOptionPane.showMessageDialog(cu, "아이디와 비밀번호를 입력하세요.");
            return;
        }
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "register");
        data.put("id", id)
                .put("pwd", pw);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }
    public void sendLoginRequest(String id, String pw){
        if(id.trim().equals("")||pw.trim().equals("")){
            JOptionPane.showMessageDialog(cu, "아이디와 비밀번호를 입력하세요.");
            return;
        }
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "login");
        data.put("id", id)
                .put("pwd", pw);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendEnterRequest(int gameid){
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "selectGame");
        data.put("gameNum", gameid);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendStartRequest(){
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "start");
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendBetRequest(int amount, String bet, int gameId){
        if(amount>this.money){
            JOptionPane.showMessageDialog(cu, "금액이 부족합니다. 다시 배팅해주세요");
            return;
        }
        this.money -= amount;
        if(gameId==0){
            cu.blackjack.balanceLabel.setText("잔액: "+this.money);
        }else if(gameId==1){
            cu.casinowar.balanceLabel.setText("잔액: "+this.money);
        }else {
            cu.baccarat.balanceLabel.setText("잔액: "+this.money);
        }
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "bet");
        data.put("amount", amount)
                        .put("bet", bet);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendMessage(String message){
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "message");
        data.put("message", message);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendHitOrStand(String hs){
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "hitOrStand");
        data.put("hitOrStand", hs);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendWarOrSurrender(int amount, String ws){
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "call");
        data.put("gotoWar", ws);
        data.put("amount", amount);
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }

    public void sendQuitRequest(){
        JSONObject request = new JSONObject();
        JSONObject data = new JSONObject();
        request.put("request", "leave");
        request.put("data", data);
        this.pw.println(request.toString());
        this.pw.flush();
    }
}
