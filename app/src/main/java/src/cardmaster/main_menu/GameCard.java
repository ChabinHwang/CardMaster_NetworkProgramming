package src.cardmaster.main_menu;

import src.cardmaster.Baccarat_game.Baccarat;
import src.cardmaster.blackjack_game.Blackjack;

import javax.swing.*;
import java.awt.*;

public class GameCard extends JPanel {

    public JLabel titleLabel;
    public JLabel descriptionLabel;
    public JButton joinButton;
    public JPanel buttonPanel;

    public GameCard(String gameTitle, int numberOfPlayer) {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255)); // 카드 배경색 설정
        setPreferredSize(new Dimension(200, 300));
        // 게임 타이틀이 들어오면, 이를 바탕으로
        // 게임 제목
        titleLabel = new JLabel(gameTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 게임 타이틀을 통해 해당 게임에 인원이 얼마나 남아 있는지 확인
        /**
         * gameTitle 이름을 가진 게임을 진행하는 플레이어가 몇명인지 가져와야함
         */
        descriptionLabel = new JLabel(numberOfPlayer + " 명 플레이중", SwingConstants.CENTER);
        descriptionLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 14));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
        add(descriptionLabel, BorderLayout.CENTER);

        // 참여하기 버튼
        joinButton = new JButton("참여하기");
        joinButton.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 10));
        joinButton.setPreferredSize(new Dimension(80, 50));
        joinButton.setBackground(new Color(170, 170, 170)); // 버튼 배경을 흰색으로 설정
        joinButton.setForeground(Color.BLACK); // 텍스트 색상을 검정으로 설정

        // 버튼 패널에 배치
        buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 255, 255)); // 버튼 패널도 카드 배경색과 맞춤
        buttonPanel.add(joinButton);

        // 상단에 여백 추가하여 버튼을 위로 이동
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
