package src.cardmaster.chat;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatApp {


    public static void init() {

        // JFrame 생성
        JFrame frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 누르면 창만 닫기
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // JTextArea (채팅 메시지 표시용)
        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false); // 사용자가 직접 수정 불가능
        chatArea.setBackground(Color.WHITE); // 배경색 흰색
        chatArea.setForeground(Color.BLACK); // 글씨색 검정
        chatArea.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16)); // 글씨체 설정

        // JScrollPane 추가 (스크롤 가능하게)
        JScrollPane scrollPane = new JScrollPane(chatArea);

        // JTextField (사용자 입력용)
        JTextField inputField = new JTextField();
        inputField.setBackground(Color.WHITE); // 배경색 흰색
        inputField.setForeground(Color.BLACK); // 글씨색 검정
        inputField.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16)); // 글씨체 설정

        // JButton (메시지 전송 버튼)
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 14)); // 글씨체 설정

        // JButton (닫기 버튼)
        JButton closeButton = new JButton("닫기");
        closeButton.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 14)); // 글씨체 설정

        // 하단 입력 패널 생성
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        buttonPanel.add(closeButton);
        inputPanel.add(buttonPanel, BorderLayout.EAST);


        // 버튼 클릭 이벤트 처리
//        sendButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String message = inputField.getText();
//                if (!message.isEmpty()) {
//                    // 채팅창에 메시지 표시
//                    chatArea.append(id+": " + message + "\n");
//
//                    // 입력 필드 초기화
//                    inputField.setText("");
//                    //JSON객체 만들어서, 서버에 전송 후 응답 반환(반환 내용은 OK임으로, 메세지에 표시하지는 않음
//
//                    chatArea.append(id+": " + message + "\n"); // 메시지 표시
//                    inputField.setText("");
//                }
//            }
//        });

        // 엔터키 이벤트 처리
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick(); // 버튼 클릭과 동일한 동작
            }
        });

        // 닫기 버튼 클릭 이벤트 처리
        closeButton.addActionListener(e -> frame.dispose()); // 창 닫기

        // 컴포넌트 추가
        frame.add(scrollPane, BorderLayout.CENTER); // 채팅창 (스크롤 포함)
        frame.add(inputPanel, BorderLayout.SOUTH); // 입력 패널

        // 화면 표시
        frame.setVisible(true);
    }
}
