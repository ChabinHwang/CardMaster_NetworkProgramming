package src.cardmaster.main_signup_login;

import org.json.JSONObject;

import java.io.IOException;

import static src.cardmaster.JSONMessageTransfer.JSONSandAndReceive;
import static src.cardmaster.main_signup_login.Main.br;
import static src.cardmaster.main_signup_login.Main.pw;
public class UserDatabase {

    public static boolean addUser(String id, String password) throws IOException {
        // JSON 데이터 생성
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "register");

        JSONObject response = IDandPW(id, password, jsonData);
        if (response.has("error")) {
            return false;
        } else return true;

    }

    public static JSONObject validateUser(String id, String password) throws IOException {
        // JSON 데이터 생성
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "Server.login");

        return IDandPW(id, password, jsonData);
    }

    private static JSONObject IDandPW(String id, String password, JSONObject jsonData) throws IOException {
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("pwd", password);
        jsonData.put("data", data);

        return JSONSandAndReceive(jsonData);
    }
}