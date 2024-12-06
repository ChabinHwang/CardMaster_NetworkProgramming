package Server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class client extends Thread {
    private Socket sock;
    private BufferedReader br;
    private PrintWriter pw;
    private boolean login;
    private User user;
    private game game;
    private room room;
    private boolean inGame;
    private userManager manager;
    private volatile boolean terminationFlag = false;
    private messageGenerator mg;
    private String id;

    public client(userManager manager, Socket sock) {
        this.sock = sock;
        this.manager = manager;
        mg = new messageGenerator();
        gameList.getInstance().init(this.mg);
        try {
            pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            login = false;
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public String getID(){
        return id;
    }

    public void run() {
        try {
            String data;
            while ((data = br.readLine()) != null && !terminationFlag) {
                JSONObject requestRoot = new JSONObject(data);
                System.out.println(requestRoot);
                String request = requestRoot.getString("request");
                JSONObject requestData = requestRoot.getJSONObject("data");
                if (request.equals("Server.login")) {
                    String id = requestData.getString("id");
                    String pwd = requestData.getString("pwd");
                    this.user = manager.UserExist(id, pwd);
                    if (this.user == null) {
                        sendMessage(mg.errorMessage("loginfail").toString());
                    } else {// 로그인 성공 시
                        login = true;
                        this.id = this.user.getId();
                        sendMessage(mg.loginSuccess(gameList.getInstance(), user).toString());
                    }
                } else if (request.equals("register")) {
                    String id = requestData.getString("id");
                    String pwd = requestData.getString("pwd");
                    if (manager.idExist(id)) {
                        sendMessage(mg.errorMessage("registerfail").toString());
                    } else {
                        if (manager.createUser(id, pwd)) {
                            sendMessage(mg.registerSuccess().toString());
                        } else {
                            sendMessage(mg.errorMessage("registerfail").toString());
                        }
                    }
                    // 이 아래 selectGame-selectRoom은 선택할 필요 없음. 어차피 게임은 3개고 start누를거니까
                }
                else if (request.equals("selectGame")) {
                    int gameId = Integer.parseInt(requestData.getString("gameNum"));
                    this.game = gameList.getInstance().getGameInstance(gameId);
                    sendMessage(mg.sendRoomList(game).toString());
                } else if (request.equals("selectRoom")) {
                    int roomId = Integer.parseInt(requestData.getString("roomId"));
                    this.room = game.enterRoom(roomId, this);
                }
                else if (request.equals("start")) {
                    if (room.isLeader(this)) {
                        if ((room.getState() == State.NEW)) {
                            room.start();
                        } else {
                            room.changeRoomState(true);
                        }
                    } else {
                        sendMessage(mg.errorMessage("you are not leader").toString());
                    }
                } else if (request.equals("sendMessage")) {
                    JSONObject value=requestRoot.getJSONObject("sendMessage");
                    requestRoot.remove("sendMessage");
                    requestRoot.put("othersMessage", value);
                    sendMessage(requestRoot.toString());
                } else {
                    room.handleClientMessage(data, this);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void sendMessage(String message) {
        pw.println(message);
        pw.flush();
    }

    public User getUserInstance() {
        return this.user;
    }

    public void changeStatus(boolean inGame) {
        this.inGame = inGame;
    }

    public void stopThread() {
        terminationFlag = true;
    }
}
