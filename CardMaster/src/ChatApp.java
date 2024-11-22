import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatApp {
    public static void main(String[] args) {
        // JFrame 생성
        JFrame frame = new JFrame("Chat Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        // 하단 입력 패널 생성
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // 버튼 클릭 이벤트 처리
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    chatArea.append("You: " + message + "\n"); // 메시지 표시
                    inputField.setText(""); // 입력 필드 초기화
                }
            }
        });

        // 엔터키 이벤트 처리
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick(); // 버튼 클릭과 동일한 동작
            }
        });

        // 컴포넌트 추가
        frame.add(scrollPane, BorderLayout.CENTER); // 채팅창 (스크롤 포함)
        frame.add(inputPanel, BorderLayout.SOUTH); // 입력 패널

        // 화면 표시
        frame.setVisible(true);
    }
}
