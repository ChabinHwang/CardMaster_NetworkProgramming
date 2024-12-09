package src.cardmaster.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatPanel extends JPanel {

    public JTextArea chatArea;
    public JTextField inputField;
    public JButton sendButton;
    public JScrollPane scrollPane;
    public JPanel inputPanel;
    public JPanel buttonPanel;

    public ChatPanel(){
        chatArea = new JTextArea();
        chatArea.setEditable(false); // 사용자가 직접 수정 불가능
        chatArea.setBackground(Color.WHITE); // 배경색 흰색
        chatArea.setForeground(Color.BLACK); // 글씨색 검정
        chatArea.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16)); // 글씨체 설정
        chatArea.setLineWrap(true);

        // JScrollPane 추가 (스크롤 가능하게)
        scrollPane = new JScrollPane(chatArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(750, 80));

        // JTextField (사용자 입력용)
        inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(700, 20));
        inputField.setBackground(Color.WHITE); // 배경색 흰색
        inputField.setForeground(Color.BLACK); // 글씨색 검정
        inputField.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16)); // 글씨체 설정

        // JButton (메시지 전송 버튼)
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 14)); // 글씨체 설정

        // 하단 입력 패널 생성
        inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);

        // 버튼 패널
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sendButton);
        inputPanel.add(buttonPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER); // 채팅창 (스크롤 포함)
        add(inputPanel, BorderLayout.SOUTH); // 입력 패널

        // 화면 표시
        setVisible(true);
    }
}
