package src.cardmaster;
import org.json.JSONObject;

import java.io.IOException;

import static src.cardmaster.main_signup_login.Main.br;
import static src.cardmaster.main_signup_login.Main.pw;

public class JSONMessageTransfer {
    public static JSONObject JSONSandAndReceive(JSONObject jsonObject) throws IOException {
        pw.println(jsonObject);
        pw.flush();
        // 서버 응답 읽기
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            responseBuilder.append(line);
            if (responseBuilder.toString().trim().endsWith("}")) {
                break; // JSON 끝임을 확인하고 루프 종료
            }
        }
        return new JSONObject(responseBuilder.toString());
    }

}
