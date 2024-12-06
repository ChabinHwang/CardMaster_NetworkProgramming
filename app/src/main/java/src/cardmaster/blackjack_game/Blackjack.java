package src.cardmaster.blackjack_game;

import src.cardmaster.chat.ChatApp;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.json.JSONObject;
import src.cardmaster.chat.ChatApp;
import src.cardmaster.main_menu.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

import static src.cardmaster.JSONMessageTransfer.MakeJSON;
import static src.cardmaster.main_signup_login.Main.br;
import static src.cardmaster.main_signup_login.Main.pw;
public class Blackjack {
    static int initialBalance = 100; // 기본 잔액
    static String id="chabin";// 유저 id
    private static Timer countdownTimer; // Timer를 클래스 필드로 선언
    private static int[] roundTime = {30}; // 초기 시간을 30초로 설정

    // 전역 변수로 버튼 선언
    private static JButton standButton;

    public static void main(String[] args) {

        initialBalance = Integer.parseInt(args[0]);
        id=args[1];
        JSONObject jsonObject = MakeJSON("startGame","roomNmae",String.valueOf(0),"id",id);

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
        JFrame frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 메인 패널 (딜러와 플레이어 카드 및 간격 포함)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        // 딜러 패널 (상단)
        JPanel dealerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dealerPanel.setBackground(Color.GREEN);
        mainPanel.add(dealerPanel);

        // 딜러와 플레이어 사이의 간격 패널
        JPanel spacerPanel = new JPanel(new BorderLayout());
        spacerPanel.setPreferredSize(new Dimension(800, 100)); // 높이 100
        spacerPanel.setBackground(Color.GREEN); // 연한 배경색

        // BLACKJACK 텍스트 라벨 추가
        JLabel blackjackLabel = new JLabel("BLACKJACK", SwingConstants.CENTER);
        blackjackLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 36));
        blackjackLabel.setForeground(Color.GRAY);
        spacerPanel.add(blackjackLabel, BorderLayout.CENTER);

        mainPanel.add(spacerPanel);

        // 플레이어 패널 (하단)
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerPanel.setBackground(Color.GREEN);
        mainPanel.add(playerPanel);

        // 타이머 표시 라벨
        JLabel timerLabel = new JLabel("30", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.RED);
        timerLabel.setPreferredSize(new Dimension(100, 50));

        // 버튼 패널 (하단)
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField betField = new JTextField(5);
        JButton betButton = new JButton("배팅하기");
        standButton = new JButton("스탠드"); // 버튼을 전역으로 설정
        JButton hitButton = new JButton("추가 카드 받기");
        JButton chatButton = new JButton("채팅하기");
        actionPanel.add(new JLabel("배팅 금액: "));
        actionPanel.add(betField);
        actionPanel.add(betButton);
        actionPanel.add(hitButton);
        actionPanel.add(standButton); // 버튼 추가
        actionPanel.add(chatButton);
        buttonPanel.add(actionPanel, BorderLayout.CENTER);

        // 잔액 표시 (오른쪽)
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel balanceLabel = new JLabel("잔액: " + balance[0]);
        balancePanel.add(balanceLabel);
        buttonPanel.add(balancePanel, BorderLayout.EAST);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.add(timerLabel, BorderLayout.NORTH); // 타이머 레이블 추가

        // 배팅 버튼 액션
        betButton.addActionListener(e -> {
            if (!gameActive[0]) return;

            try {
                int bet = Integer.parseInt(betField.getText());
                if (bet > balance[0]) {
                    JOptionPane.showMessageDialog(frame, "잔액이 부족합니다.", "오류", JOptionPane.ERROR_MESSAGE);
                } else if (bet <= 0) {
                    JOptionPane.showMessageDialog(frame, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                } else {
                    betAmount[0] = bet;
                    balance[0] -= bet;
                    balanceLabel.setText("잔액: " + balance[0]);

                    // 카드 나눠주기
                    dealerCards.add(deck.remove(0)); // 딜러 카드 (공개)
                    dealerCards.add(deck.remove(0)); // 딜러 카드 (비공개)
                    playerCards.add(deck.remove(0)); // 플레이어 카드
                    playerCards.add(deck.remove(0)); // 플레이어 카드

                    updateDealerPanel(dealerPanel, dealerCards, false); // 딜러 카드 중 첫 번째만 공개
                    updatePlayerPanel(playerPanel, playerCards);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "숫자를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // "추가 카드 받기" 버튼 액션
        hitButton.addActionListener(e -> {
            if (!gameActive[0]) return;

            // 덱에서 카드 한 장 가져오기
            if (!deck.isEmpty()) {
                String newCard = deck.remove(0);
                playerCards.add(newCard); // 플레이어 카드 리스트에 추가
                updatePlayerPanel(playerPanel, playerCards); // UI 갱신
            } else {
                JOptionPane.showMessageDialog(frame, "덱에 카드가 없습니다.", "알림", JOptionPane.WARNING_MESSAGE);
            }

            // 플레이어 점수 계산
            int playerScore = calculateScore(playerCards);
            if (playerScore > 21) {
                JOptionPane.showMessageDialog(frame, "버스트! 딜러 승리!", "패배", JOptionPane.INFORMATION_MESSAGE);
                gameActive[0] = false;
            }
        });

        // "스탠드" 버튼 액션
        standButton.addActionListener(e -> {
            if (!gameActive[0]) return;

            // 딜러의 숨겨진 카드 공개
            updateDealerPanel(dealerPanel, dealerCards, true);

            // 딜러 추가 카드 받기
            while (calculateScore(dealerCards) < 17) {
                dealerCards.add(deck.remove(0));
                updateDealerPanel(dealerPanel, dealerCards, true);
            }

            // 점수 계산
            int playerScore = calculateScore(playerCards);
            int dealerScore = calculateScore(dealerCards);

            String result;
            if (playerScore > 21) {
                result = "버스트! 딜러 승리!";
            } else if (dealerScore > 21 || playerScore > dealerScore) {
                balance[0] += betAmount[0] * 2;
                result = "플레이어 승리! 배팅 금액의 두 배를 획득했습니다!";
            } else if (playerScore == dealerScore) {
                balance[0] += betAmount[0];
                result = "무승부! 배팅 금액이 반환되었습니다.";
            } else {
                result = "딜러 승리!";
            }

            // 결과 출력
            balanceLabel.setText("잔액: " + balance[0]);
            JOptionPane.showMessageDialog(frame, result, "결과", JOptionPane.INFORMATION_MESSAGE);

            // 게임 상태 종료
            gameActive[0] = false;
        });

        // "채팅하기" 버튼 이벤트
        String finalId = id;
        chatButton.addActionListener(e -> {
            // ChatApp 실행
            SwingUtilities.invokeLater(() -> {
                ChatApp.main(new String[]{finalId}); // ChatApp 실행
            });
        });
        // 타이머 서버 응답 읽기
        roundTime[0] = 0;
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while (true) {
            try {
                if (!((line = br.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            responseBuilder.append(line);
            if (responseBuilder.toString().trim().endsWith("}")) {
                JSONObject timerJSON = new JSONObject(responseBuilder.toString());
                if(timerJSON.get("response").equals("timer_update")){
                    JSONObject dataJSON = timerJSON.getJSONObject("data");
                    roundTime[0] = dataJSON.getInt("remaining_time");
                    break; // 시간을 초기화하고 입장.
                }
            }
        }
        timerLabel.setText(String.valueOf(roundTime[0]));
        startCountdownTimer(timerLabel); // 타이머 시작

        frame.setVisible(true);//게임 프레임 보여지게 하기

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
                new MenuFrame(id, initialBalance);
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

    private static int calculateScore(ArrayList<String> cards) {
        int score = 0;
        int aceCount = 0;

        for (String card : cards) {
            String value = card.substring(0, card.length() - 1);
            if ("JQK".contains(value)) {
                score += 10;
            } else if ("A".equals(value)) {
                aceCount++;
                score += 11;
            } else {
                score += Integer.parseInt(value);
            }
        }

        while (score > 21 && aceCount > 0) {
            score -= 10;
            aceCount--;
        }

        return score;
    }

    private static void updateDealerPanel(JPanel panel, ArrayList<String> cards, boolean revealAll) {
        panel.removeAll();
        for (int i = 0; i < cards.size(); i++) {
            String text = (i == 1 && !revealAll) ? "?" : cards.get(i);
            panel.add(createCardPanel(text));
        }
        panel.revalidate();
        panel.repaint();
    }

    private static void updatePlayerPanel(JPanel panel, ArrayList<String> cards) {
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
