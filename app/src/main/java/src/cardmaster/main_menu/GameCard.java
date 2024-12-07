package src.cardmaster.main_menu;

import src.cardmaster.Baccarat_game.Baccarat;
import src.cardmaster.blackjack_game.Blackjack;

import javax.swing.*;
import java.awt.*;

public class GameCard extends JPanel {

    public GameCard(String gameTitle, Runnable gameAction) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255)); // 카드 배경색 설정
        setPreferredSize(new Dimension(200, 300));
        // 게임 타이틀이 들어오면, 이를 바탕으로
        // 게임 제목
        JLabel titleLabel = new JLabel(gameTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 게임 타이틀을 통해 해당 게임에 인원이 얼마나 남아 있는지 확인
        /**
         * gameTitle 이름을 가진 게임을 진행하는 플레이어가 몇명인지 가져와야함
         */
        JLabel descriptionLabel = new JLabel("4 명 플레이중", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 14));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(descriptionLabel, BorderLayout.CENTER);

        // 참여하기 버튼
        JButton joinButton = new JButton("참여하기");
        joinButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 10));
        joinButton.setPreferredSize(new Dimension(80, 50));
        joinButton.setBackground(new Color(170, 170, 170)); // 버튼 배경을 흰색으로 설정
        joinButton.setForeground(Color.BLACK); // 텍스트 색상을 검정으로 설정

        // 참여하기 버튼 이벤트
        joinButton.addActionListener(e -> SwingUtilities.invokeLater(gameAction));

        // 버튼 패널에 배치
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255)); // 버튼 패널도 카드 배경색과 맞춤
        buttonPanel.add(joinButton);

        // 상단에 여백 추가하여 버튼을 위로 이동
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Game Menu");
        frame.setLayout(new FlowLayout());

        // 블랙잭 카드
        GameCard blackjackCard = new GameCard("BLACKJACK", () -> Blackjack.main(new String[]{}));

        // 바카라 카드
        GameCard baccaratCard = new GameCard("BACCARAT", () -> Baccarat.main(new String[]{}));

        // 카지노 워 카드
        GameCard casinowarCard = new GameCard("CASINO WAR", () -> Baccarat.main(new String[]{}));
        // 카드 추가
        frame.add(blackjackCard);
        frame.add(baccaratCard);
        frame.add(casinowarCard);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
