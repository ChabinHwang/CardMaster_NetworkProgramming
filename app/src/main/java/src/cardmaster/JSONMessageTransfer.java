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

    /**
     *
     * @param header
     * @param data 첫 번쨰 인자가 Key, 두번째 인자가 Value
     * @return
     */
    public static JSONObject MakeJSON(String header, String... data) {
        // JSON 객체 생성
        JSONObject jsonObject = new JSONObject();

        // header 추가
        jsonObject.put("request", header);

        // data를 처리하여 추가
        JSONObject dataObject = new JSONObject();
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException("data 인자는 키와 값이 짝수로 들어와야 합니다.");
        }

        for (int i = 0; i < data.length; i += 2) {
            String key = data[i];
            String value = data[i + 1];
            dataObject.put(key, value);
        }

        jsonObject.put("data", dataObject);
        return jsonObject;
    }

}
