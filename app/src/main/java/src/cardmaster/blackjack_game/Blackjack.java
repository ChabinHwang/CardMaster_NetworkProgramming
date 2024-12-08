package src.cardmaster.blackjack_game;

import src.cardmaster.chat.ChatPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


public class Blackjack extends JPanel {
    private int initialBalance = 100; // 기본 잔액
    private String id="chabin";// 유저 id
    private static Timer countdownTimer; // Timer를 클래스 필드로 선언
    private static int[] roundTime = {30}; // 초기 시간을 30초로 설정

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
    public JPanel balancePanel;
    public JLabel balanceLabel;
    public JPanel chatPanel;

    public Blackjack(){
        // 초기 잔액
        int[] balance = {100}; // 플레이어 잔액
        int[] betAmount = {0}; // 배팅 금액
        boolean[] gameActive = {true}; // 게임 상태

        // 카드 덱 생성
        ArrayList<String> deck = createDeck();

        // 딜러와 플레이어 카드
        ArrayList<String> dealerCards = new ArrayList<>();
        ArrayList<String> playerCards = new ArrayList<>();

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
        actionPanel.add(new JLabel("배팅 금액: "));
        actionPanel.add(betField);
        actionPanel.add(betButton);
        actionPanel.add(hitButton);
        actionPanel.add(standButton); // 버튼 추가
        actionPanel.add(startButton);
        buttonPanel.add(actionPanel, BorderLayout.CENTER);

        // 잔액 표시 (오른쪽)
        balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balanceLabel = new JLabel("잔액: " + balance[0]);
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

    public void init(int money, String id) {

        // 배팅 버튼 액션
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
//                    dealerCards.add(deck.remove(0)); // 딜러 카드 (공개)
//                    dealerCards.add(deck.remove(0)); // 딜러 카드 (비공개)
//                    playerCards.add(deck.remove(0)); // 플레이어 카드
//                    playerCards.add(deck.remove(0)); // 플레이어 카드
//
//                    updateDealerPanel(dealerPanel, dealerCards, false); // 딜러 카드 중 첫 번째만 공개
//                    updatePlayerPanel(playerPanel, playerCards);
//                }
//            } catch (NumberFormatException ex) {
//                JOptionPane.showMessageDialog(frame, "숫자를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
//            }
//        });
//
//        // "추가 카드 받기" 버튼 액션
//        hitButton.addActionListener(e -> {
//            if (!gameActive[0]) return;
//
//            // 덱에서 카드 한 장 가져오기
//            if (!deck.isEmpty()) {
//                String newCard = deck.remove(0);
//                playerCards.add(newCard); // 플레이어 카드 리스트에 추가
//                updatePlayerPanel(playerPanel, playerCards); // UI 갱신
//            } else {
//                JOptionPane.showMessageDialog(frame, "덱에 카드가 없습니다.", "알림", JOptionPane.WARNING_MESSAGE);
//            }
//
//            // 플레이어 점수 계산
//            int playerScore = calculateScore(playerCards);
//            if (playerScore > 21) {
//                JOptionPane.showMessageDialog(frame, "버스트! 딜러 승리!", "패배", JOptionPane.INFORMATION_MESSAGE);
//                gameActive[0] = false;
//            }
//        });
//
//        // "스탠드" 버튼 액션
//        standButton.addActionListener(e -> {
//            if (!gameActive[0]) return;
//
//            // 딜러의 숨겨진 카드 공개
//            updateDealerPanel(dealerPanel, dealerCards, true);
//
//            // 딜러 추가 카드 받기
//            while (calculateScore(dealerCards) < 17) {
//                dealerCards.add(deck.remove(0));
//                updateDealerPanel(dealerPanel, dealerCards, true);
//            }
//
//            // 점수 계산
//            int playerScore = calculateScore(playerCards);
//            int dealerScore = calculateScore(dealerCards);
//
//            String result;
//            if (playerScore > 21) {
//                result = "버스트! 딜러 승리!";
//            } else if (dealerScore > 21 || playerScore > dealerScore) {
//                balance[0] += betAmount[0] * 2;
//                result = "플레이어 승리! 배팅 금액의 두 배를 획득했습니다!";
//            } else if (playerScore == dealerScore) {
//                balance[0] += betAmount[0];
//                result = "무승부! 배팅 금액이 반환되었습니다.";
//            } else {
//                result = "딜러 승리!";
//            }
//
//            // 결과 출력
//            balanceLabel.setText("잔액: " + balance[0]);
//            JOptionPane.showMessageDialog(frame, result, "결과", JOptionPane.INFORMATION_MESSAGE);
//
//            // 게임 상태 종료
//            gameActive[0] = false;
//        });
//
//        // "채팅하기" 버튼 이벤트
//        String finalId = id;
////        chatButton.addActionListener(e -> {
////            // ChatApp 실행
////            SwingUtilities.invokeLater(() -> {
////                ChatApp.main(new String[]{finalId}); // ChatApp 실행
////            });
////        });
//        // 타이머 서버 응답 읽기
//        roundTime[0] = 0;
//        StringBuilder responseBuilder = new StringBuilder();
//        String line;
//        timerLabel.setText(String.valueOf(roundTime[0]));
//        startCountdownTimer(timerLabel); // 타이머 시작
//
//        frame.setVisible(true);//게임 프레임 보여지게 하기

    }
    //타이머 카운트 다운 표시->종료시 메뉴화면으로 돌아가기
    private static void startCountdownTimer(JLabel timerLabel) {
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop(); // 이전 타이머가 실행 중이라면 멈추기
        }

        countdownTimer = new javax.swing.Timer(1000, e1 -> {
            if (roundTime[0] > 0) {
                roundTime[0]--;
                timerLabel.setText(String.valueOf(roundTime[0]));
            } else {
                // 타이머 종료 시 메시지 표시
                if (roundTime[0] == 0) {
                    int option = JOptionPane.showOptionDialog(null, "타이머가 종료되었습니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"확인"}, null);
                    if (option == 0) {
                        System.exit(0); // 게임 종료 (JFrame 닫기)
                    }
                }
                countdownTimer.stop(); // 타이머 멈추기
                //시간 아웃 요청이 오면, 금액변화 저장. MenuFrame 만들기
            }
        });
        countdownTimer.start();
    }

    private static ArrayList<String> createDeck() {
        String[] suits = {"♠", "♥", "♦", "♣"};
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        ArrayList<String> deck = new ArrayList<>();
        for (String suit : suits) {
            for (String value : values) {
                deck.add(value + suit);
            }
        }
        Collections.shuffle(deck);
        return deck;
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
