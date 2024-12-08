package src.cardmaster.Baccarat_game;

import src.cardmaster.chat.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class Baccarat extends JPanel {

    public JPanel frame;
    public JPanel mainPanel;
    public JPanel dealerPanel;
    public JPanel spacerPanel;
    public JLabel baccaratLabel;
    public JPanel playerPanel;
    public JLabel timerLabel;
    public JPanel buttonPanel;
    public JPanel betPanel;
    public JTextField betField;
    public JButton betButton;
    public JButton startButton;

    // 베팅 옵션 버튼
    public JRadioButton playerBet;
    public JRadioButton bankerBet;
    public JRadioButton tieBet;
    public ButtonGroup betGroup;
    public JPanel balancePanel;
    public JLabel balanceLabel;
    public JPanel chatPanel;

    public Baccarat(){

        // JFrame 생성
        frame = new JPanel();
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 메인 패널
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.GREEN); // 초록색 배경 유지
        frame.add(mainPanel, BorderLayout.CENTER);

        // 딜러 패널 (상단)
        dealerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dealerPanel.setBackground(Color.GREEN);
        mainPanel.add(dealerPanel);

        // 딜러와 플레이어 사이의 간격 패널
        spacerPanel = new JPanel(new BorderLayout());
        spacerPanel.setPreferredSize(new Dimension(800, 100)); // 높이 100
        spacerPanel.setBackground(Color.GREEN); // 초록색 배경

        // BACCARAT 텍스트 라벨 추가
        baccaratLabel = new JLabel("BACCARAT", SwingConstants.CENTER);
        baccaratLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 36));
        baccaratLabel.setForeground(Color.GRAY);
        spacerPanel.add(baccaratLabel, BorderLayout.CENTER);

        mainPanel.add(spacerPanel);

        timerLabel = new JLabel("30", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        timerLabel.setPreferredSize(new Dimension(100, 50));

        // 플레이어 패널 (하단)
        playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerPanel.setBackground(Color.GREEN);
        mainPanel.add(playerPanel);

        // 버튼 패널
        buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE); // 버튼 영역 하얀색 배경

        // 배팅 패널 (왼쪽 정렬)
        betPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        betPanel.setBackground(Color.WHITE); // 배팅 영역 하얀색 배경

        betField = new JTextField(5);
        betButton = new JButton("배팅하기");
        startButton = new JButton("시작하기");

        // 베팅 옵션 버튼
        playerBet = new JRadioButton("Player");
        bankerBet = new JRadioButton("Banker");
        tieBet = new JRadioButton("Tie");
        betGroup = new ButtonGroup();
        betGroup.add(playerBet);
        betGroup.add(bankerBet);
        betGroup.add(tieBet);

        // 배팅 패널 구성
        betPanel.add(new JLabel("배팅 금액: "));
        betPanel.add(betField);
        betPanel.add(betButton);
        betPanel.add(playerBet);
        betPanel.add(bankerBet);
        betPanel.add(tieBet);
        betPanel.add(startButton);
//        betPanel.add(startButton); // 게임 시작 버튼 추가

        // 버튼 패널에 배팅 패널 추가
        buttonPanel.add(betPanel, BorderLayout.CENTER);

        // 잔액 표시 (오른쪽 정렬)
        balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balancePanel.setBackground(Color.WHITE); // 잔액 영역 하얀색 배경
        balanceLabel = new JLabel("잔액: ");
        balanceLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
        balanceLabel.setForeground(Color.BLACK);
        balancePanel.add(balanceLabel);

        // 버튼 패널에 잔액 표시 추가
        buttonPanel.add(balancePanel, BorderLayout.EAST);
        chatPanel = new ChatPanel();
        chatPanel.setPreferredSize(new Dimension(800, 150));
        chatPanel.setBorder(BorderFactory.createTitledBorder("채팅창"));
        buttonPanel.add(chatPanel, BorderLayout.SOUTH);

        // 버튼 패널을 프레임 하단에 추가
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(timerLabel, BorderLayout.NORTH);
        add(frame);
        setVisible(true);
    }

    public void updateCardPanel(JPanel panel, ArrayList<String> cards, String label) {
        panel.removeAll();
        panel.add(new JLabel(label + " Cards: "));
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
