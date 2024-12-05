package src.cardmaster.main_signup_login;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static src.cardmaster.main_signup_login.Main.br;
import static src.cardmaster.main_signup_login.Main.pw;
public class UserDatabase {

    public static boolean addUser(String id, String password) throws IOException {
        // JSON 데이터 생성
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "register");

        // data 객체 생성 및 추가
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("pwd", password);
        jsonData.put("data", data);
        pw.println(jsonData);
        pw.flush();
        // 서버 응답 읽기
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
        }
        // JSON 응답 처리
        JSONObject response = new JSONObject(responseBuilder.toString());
        if (response.has("error")) {
            return false;
        } else return true;

    }

    public static boolean validateUser(String id, String password) throws IOException {
        // JSON 데이터 생성
        JSONObject jsonData = new JSONObject();
        jsonData.put("request", "login");

        // data 객체 생성 및 추가
        JSONObject data = new JSONObject();
        data.put("id", id);
        data.put("pwd", password);
        jsonData.put("data", data);
        pw.println(jsonData);
        pw.flush();
        // 서버 응답 읽기
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
        }
        // JSON 응답 처리
        JSONObject response = new JSONObject(responseBuilder.toString());
        if (response.has("error")) {
            return false;
        } else return true;
    }
}