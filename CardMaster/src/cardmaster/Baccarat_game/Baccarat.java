package cardmaster.baccarat_game;

import cardmaster.chat.ChatApp;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Baccarat {

    public static void main(String[] args) {
        // 초기 잔액
        int[] balance = {100}; // 플레이어 잔액
        int[] betAmount = {0}; // 배팅 금액
        String[] selectedBet = {""}; // 선택한 베팅 ("Player", "Banker", "Tie")

        // 카드 덱 생성
        ArrayList<String> deck = createDeck();

        // JFrame 생성
        JFrame frame = new JFrame("Baccarat");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 메인 패널
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.GREEN); // 초록색 배경 유지
        frame.add(mainPanel, BorderLayout.CENTER);

        // 딜러 패널 (상단)
        JPanel dealerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dealerPanel.setBackground(Color.GREEN);
        mainPanel.add(dealerPanel);

        // 딜러와 플레이어 사이의 간격 패널
        JPanel spacerPanel = new JPanel(new BorderLayout());
        spacerPanel.setPreferredSize(new Dimension(800, 100)); // 높이 100
        spacerPanel.setBackground(Color.GREEN); // 초록색 배경

        // BACCARAT 텍스트 라벨 추가
        JLabel baccaratLabel = new JLabel("BACCARAT", SwingConstants.CENTER);
        baccaratLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 36));
        baccaratLabel.setForeground(Color.GRAY);
        spacerPanel.add(baccaratLabel, BorderLayout.CENTER);

        mainPanel.add(spacerPanel);

        // 플레이어 패널 (하단)
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        playerPanel.setBackground(Color.GREEN);
        mainPanel.add(playerPanel);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE); // 버튼 영역 하얀색 배경

        // 배팅 패널 (왼쪽 정렬)
        JPanel betPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        betPanel.setBackground(Color.WHITE); // 배팅 영역 하얀색 배경

        JTextField betField = new JTextField(5);
        JButton betButton = new JButton("배팅하기");
        JButton playButton = new JButton("게임 시작");
        JButton chatButton = new JButton("채팅하기");

        // 베팅 옵션 버튼
        JRadioButton playerBet = new JRadioButton("Player");
        JRadioButton bankerBet = new JRadioButton("Banker");
        JRadioButton tieBet = new JRadioButton("Tie");
        ButtonGroup betGroup = new ButtonGroup();
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
        betPanel.add(chatButton); // 채팅하기 버튼 추가
        betPanel.add(playButton); // 게임 시작 버튼 추가

        // 버튼 패널에 배팅 패널 추가
        buttonPanel.add(betPanel, BorderLayout.CENTER);

        // 잔액 표시 (오른쪽 정렬)
        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        balancePanel.setBackground(Color.WHITE); // 잔액 영역 하얀색 배경
        JLabel balanceLabel = new JLabel("잔액: " + balance[0]);
        balanceLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16));
        balanceLabel.setForeground(Color.BLACK);
        balancePanel.add(balanceLabel);

        // 버튼 패널에 잔액 표시 추가
        buttonPanel.add(balancePanel, BorderLayout.EAST);

        // 버튼 패널을 프레임 하단에 추가
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // 배팅 버튼 동작
        betButton.addActionListener(e -> {
            try {
                int bet = Integer.parseInt(betField.getText());
                if (bet > balance[0]) {
                    JOptionPane.showMessageDialog(frame, "잔액이 부족합니다.", "오류", JOptionPane.ERROR_MESSAGE);
                } else if (bet <= 0) {
                    JOptionPane.showMessageDialog(frame, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                } else if (!playerBet.isSelected() && !bankerBet.isSelected() && !tieBet.isSelected()) {
                    JOptionPane.showMessageDialog(frame, "베팅할 대상을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                } else {
                    betAmount[0] = bet;
                    balance[0] -= bet;
                    balanceLabel.setText("잔액: " + balance[0]);

                    if (playerBet.isSelected()) selectedBet[0] = "Player";
                    if (bankerBet.isSelected()) selectedBet[0] = "Banker";
                    if (tieBet.isSelected()) selectedBet[0] = "Tie";

                    JOptionPane.showMessageDialog(frame, selectedBet[0] + "에 " + bet + "포인트를 배팅했습니다.", "베팅 완료", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "숫자를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 게임 시작 버튼 동작
        playButton.addActionListener(e -> {
            if (betAmount[0] == 0) {
                JOptionPane.showMessageDialog(frame, "먼저 베팅을 완료하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 카드 분배
            ArrayList<String> playerCards = new ArrayList<>();
            ArrayList<String> bankerCards = new ArrayList<>();
            playerCards.add(deck.remove(0));
            playerCards.add(deck.remove(0));
            bankerCards.add(deck.remove(0));
            bankerCards.add(deck.remove(0));

            // 카드 표시
            updateCardPanel(playerPanel, playerCards, "Player");
            updateCardPanel(dealerPanel, bankerCards, "Banker");

            // 5초 후 결과 표시
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        int playerScore = calculateScore(playerCards);
                        int bankerScore = calculateScore(bankerCards);
                        String result;

                        if (playerScore == bankerScore) {
                            result = "Tie";
                        } else if (playerScore > bankerScore) {
                            result = "Player";
                        } else {
                            result = "Banker";
                        }

                        // 배팅 결과 확인
                        if (result.equals(selectedBet[0])) {
                            int winnings = selectedBet[0].equals("Tie") ? betAmount[0] * 8 : betAmount[0] * 2;
                            balance[0] += winnings;
                            JOptionPane.showMessageDialog(frame, "축하합니다! " + winnings + "포인트를 획득했습니다.", "승리", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "아쉽습니다! " + result + "가 승리했습니다.", "패배", JOptionPane.INFORMATION_MESSAGE);
                        }

                        // 잔액 업데이트
                        balanceLabel.setText("잔액: " + balance[0]);
                        betAmount[0] = 0; // 베팅 금액 초기화
                    });
                }
            }, 3000); // 3초 지연
        });

        // 채팅 버튼 동작
        chatButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> ChatApp.main(new String[]{}));
        });

        frame.setVisible(true);
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

        for (String card : cards) {
            String value = card.substring(0, card.length() - 1);
            if ("JQK".contains(value)) {
                score += 0;
            } else if ("A".equals(value)) {
                score += 1;
            } else {
                score += Integer.parseInt(value);
            }
        }

        return score % 10;
    }

    private static void updateCardPanel(JPanel panel, ArrayList<String> cards, String label) {
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
