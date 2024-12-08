package src.cardmaster.Casinowar_game;

import org.json.JSONObject;
import src.cardmaster.chat.ChatApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Casinowar extends JPanel {
    private int initialBalance = 100;
    private String id = "";
    public JPanel frame;
    public JPanel mainPanel;
    public JPanel dealerPanel;
    public JPanel spacerPanel;
    public JLabel casinoWarLabel;
    public JPanel playerPanel;
    public JLabel timerLabel;
    public JPanel buttonPanel;
    public JPanel actionPanel;
    public JTextField betField;
    public JButton betButton;
    public JButton warButton;
    public JButton chatButton;
    public JButton startButton;
    public JPanel balancePanel;
    public JLabel balanceLabel;
    public ArrayList<String> playerCards;
    public ArrayList<String> dealerCards;

    public Casinowar(){
        this.initialBalance = initialBalance; // 기본 잔액
        this.id = id;

        int[] balance = {initialBalance}; // 플레이어 잔액
        int[] betAmount = {0}; // 배팅 금액
        boolean[] gameActive = {true}; // 게임 상태

        // 딜러와 플레이어 카드
        dealerCards = new ArrayList<String>();
        playerCards = new ArrayList<String>();

        // JFrame 생성
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

        // "CASINO WAR" 텍스트 라벨 추가
        casinoWarLabel = new JLabel("CASINO WAR", SwingConstants.CENTER);
        casinoWarLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 36));
        casinoWarLabel.setForeground(Color.GRAY);
        spacerPanel.add(casinoWarLabel, BorderLayout.CENTER);

        mainPanel.add(spacerPanel);

        // 플레이어 패널 (하단)
        playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerPanel.setBackground(Color.GREEN);
        mainPanel.add(playerPanel);

        timerLabel = new JLabel("30", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        timerLabel.setPreferredSize(new Dimension(100, 50));

        // 버튼 패널 (하단)
        buttonPanel = new JPanel(new BorderLayout());
        actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField betField = new JTextField(5);
        betButton = new JButton("배팅하기");
        warButton = new JButton("WAR");
        chatButton = new JButton("채팅하기");
        startButton = new JButton("시작하기");
        warButton.setEnabled(false); // 기본적으로 비활성화
        actionPanel.add(new JLabel("배팅 금액: "));
        actionPanel.add(betField);
        actionPanel.add(betButton);
        actionPanel.add(warButton);
        actionPanel.add(chatButton); // 채팅하기 버튼 추가
        actionPanel.add(startButton);
        buttonPanel.add(actionPanel, BorderLayout.CENTER);

        // 잔액 표시 (오른쪽)
        balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balanceLabel = new JLabel("잔액: " + balance[0]);
        balancePanel.add(balanceLabel);
        buttonPanel.add(balancePanel, BorderLayout.EAST);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(timerLabel, BorderLayout.NORTH);
        add(frame);
        setVisible(true);
    }

    public void init(int initialBalance, String id) {
        // 초기 잔액을 인자로 받음



        // 배팅 버튼 동작
//        betButton.addActionListener(e -> {
//            if (!gameActive[0]) return;
//
//            try {
//                int bet = Integer.parseInt(betField.getText());
//                if (bet > balance[0]) {
//                    JOptionPane.showMessageDialog(frame, "잔액이 부족합니다.", "오류", JOptionPane.ERROR_MESSAGE);
//                } else if (bet <= 0) {
//                    JOptionPane.showMessageDialog(frame, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    betAmount[0] = bet;
//                    balance[0] -= bet;
//                    balanceLabel.setText("잔액: " + balance[0]);
//
//                    // 카드 나눠주기
//                    dealerCards.clear();
//                    playerCards.clear();
////                    dealerCards.add(deck.remove(0)); // 딜러 카드
////                    playerCards.add(deck.remove(0)); // 플레이어 카드
//
//                    updateDealerPanel(dealerPanel, dealerCards);
//                    updatePlayerPanel(playerPanel, playerCards);
//
//                    // 3초 후 결과 판단
//                    new Timer().schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            SwingUtilities.invokeLater(() -> {
//                                int dealerScore = calculateScore(dealerCards);
//                                int playerScore = calculateScore(playerCards);
//
//                                if (playerScore > dealerScore) {
//                                    balance[0] += betAmount[0] * 2;
//                                    JOptionPane.showMessageDialog(frame, "플레이어 승리!", "결과", JOptionPane.INFORMATION_MESSAGE);
//                                } else if (playerScore < dealerScore) {
//                                    JOptionPane.showMessageDialog(frame, "딜러 승리!", "결과", JOptionPane.INFORMATION_MESSAGE);
//                                } else {
//                                    JOptionPane.showMessageDialog(frame, "전쟁 발생! 'WAR' 버튼을 누르세요!", "결과", JOptionPane.INFORMATION_MESSAGE);
//                                    warButton.setEnabled(true);
//                                }
//
//                                balanceLabel.setText("잔액: " + balance[0]);
//                            });
//                        }
//                    }, 3000); // 3초 지연
//                }
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(frame, "숫자를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        // WAR 버튼 동작
//        warButton.addActionListener(e -> {
//            if (!gameActive[0]) return;
//
//            warButton.setEnabled(false); // 다시 비활성화
//
//            // 카드 추가 배분
////            dealerCards.add(deck.remove(0));
////            playerCards.add(deck.remove(0));
//
//            updateDealerPanel(dealerPanel, dealerCards);
//            updatePlayerPanel(playerPanel, playerCards);
//
//            // 3초 후 최종 결과 판단
////            new Timer().schedule(new TimerTask() {
////                @Override
////                public void run() {
////                    SwingUtilities.invokeLater(() -> {
////                        int dealerScore = calculateScore(dealerCards);
////                        int playerScore = calculateScore(playerCards);
////
////                        if (playerScore > dealerScore) {
////                            balance[0] += betAmount[0] * 3; // 전쟁 승리 시 보상
////                            JOptionPane.showMessageDialog(frame, "전쟁에서 승리! 큰 보상을 받았습니다!", "결과", JOptionPane.INFORMATION_MESSAGE);
////                        } else {
////                            JOptionPane.showMessageDialog(frame, "전쟁에서 패배했습니다. 다음 기회를 노리세요.", "결과", JOptionPane.INFORMATION_MESSAGE);
////                        }
////
////                        balanceLabel.setText("잔액: " + balance[0]);
////                    });
////                }
////            }, 3000); // 3초 지연
//        });
//
//        // 채팅하기 버튼 동작
//        chatButton.addActionListener(e -> {
//            SwingUtilities.invokeLater(() -> {
////                ChatApp.main(new String[]{}); // ChatApp 실행
//            });
//        });
//
//        frame.setVisible(true);
    }



    private static ArrayList<String> createDeck() {
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        ArrayList<String> deck = new ArrayList<>();
        for (String suit : suits) {
            for (String value : values) {
                deck.add(value + suit);
            }
        }
        Collections.shuffle(deck);
        return deck;
    }

    private static int calculateScore(ArrayList<String> cards) {
        String value = cards.get(cards.size() - 1).substring(0, cards.get(cards.size() - 1).length() - 1);
        if ("JQK".contains(value)) {
            return 11 + "JQK".indexOf(value);
        } else if ("A".equals(value)) {
            return 14; // A는 최고 점수
        } else {
            return Integer.parseInt(value);
        }
    }

    public void updateDealerPanel(JPanel panel, ArrayList<String> cards) {
        panel.removeAll();
        for (String card : cards) {
            panel.add(createCardPanel(card));
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
