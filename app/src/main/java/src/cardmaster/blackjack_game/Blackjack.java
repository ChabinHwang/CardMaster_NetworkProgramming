package src.cardmaster.blackjack_game;

import src.cardmaster.chat.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Blackjack extends JPanel {

    // 전역 변수로 버튼 선언
    public JButton standButton;
    public JPanel frame;
    public JPanel mainPanel;
    public JPanel dealerPanel;
    public JPanel spacerPanel;
    public JLabel blackjackLabel;
    public JPanel playerPanel;
    public JLabel timerLabel;
    public JPanel buttonPanel;
    public JPanel actionPanel;
    public JTextField betField;
    public JButton betButton;
    public JButton hitButton;
    public JButton startButton;
    public JButton quitButton;
    public JPanel balancePanel;
    public JLabel balanceLabel;
    public JPanel chatPanel;

    public Blackjack(){

        frame = new JPanel();
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 메인 패널 (딜러와 플레이어 카드 및 간격 포함)
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        // 딜러 패널 (상단)
        dealerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dealerPanel.setBackground(Color.GREEN);
        mainPanel.add(dealerPanel);

        // 딜러와 플레이어 사이의 간격 패널
        spacerPanel = new JPanel(new BorderLayout());
        spacerPanel.setPreferredSize(new Dimension(800, 100)); // 높이 100
        spacerPanel.setBackground(Color.GREEN); // 연한 배경색

        // BLACKJACK 텍스트 라벨 추가
        blackjackLabel = new JLabel("BLACKJACK", SwingConstants.CENTER);
        blackjackLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 36));
        blackjackLabel.setForeground(Color.GRAY);
        spacerPanel.add(blackjackLabel, BorderLayout.CENTER);

        mainPanel.add(spacerPanel);

        // 플레이어 패널 (하단)
        playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerPanel.setBackground(Color.GREEN);
        mainPanel.add(playerPanel);

        // 타이머 표시 라벨
        timerLabel = new JLabel("30", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        timerLabel.setPreferredSize(new Dimension(100, 50));

        // 버튼 패널 (하단)
        buttonPanel = new JPanel(new BorderLayout());
        actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        betField = new JTextField(5);
        betButton = new JButton("배팅하기");
        standButton = new JButton("스탠드"); // 버튼을 전역으로 설정
        hitButton = new JButton("추가 카드 받기");
        startButton = new JButton("시작하기");
        quitButton = new JButton("나가기");
        actionPanel.add(new JLabel("배팅 금액: "));
        actionPanel.add(betField);
        actionPanel.add(betButton);
        actionPanel.add(hitButton);
        actionPanel.add(standButton); // 버튼 추가
        actionPanel.add(startButton);
        actionPanel.add(quitButton);
        buttonPanel.add(actionPanel, BorderLayout.CENTER);

        // 잔액 표시 (오른쪽)
        balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balanceLabel = new JLabel("잔액: ");
        balancePanel.add(balanceLabel);
        buttonPanel.add(balancePanel, BorderLayout.EAST);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(timerLabel, BorderLayout.NORTH); // 타이머 레이블 추가
        chatPanel = new ChatPanel();
        chatPanel.setPreferredSize(new Dimension(800, 150));
        chatPanel.setBorder(BorderFactory.createTitledBorder("채팅창"));
        buttonPanel.add(chatPanel, BorderLayout.SOUTH);
        add(frame);
        setVisible(true);
    }


    public void updateDealerPanel(JPanel panel, ArrayList<String> cards, boolean revealAll) {
        panel.removeAll();
        for (int i = 0; i < cards.size(); i++) {
            String text = (i == 1 && !revealAll) ? "?" : cards.get(i);
            panel.add(createCardPanel(text));
        }
        panel.revalidate();
        panel.repaint();
    }

    public void updatePlayerPanel(JPanel panel, ArrayList<String> cards) {
        panel.removeAll();
        for (String card : cards) {
            panel.add(createCardPanel(card));
        }
        panel.revalidate();
        panel.repaint();
    }

    private static JPanel createCardPanel(String text) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(100, 150)); // 카드 크기 설정
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(label);
        return card;
    }
}
