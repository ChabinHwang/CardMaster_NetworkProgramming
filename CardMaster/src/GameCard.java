import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameCard extends JPanel {
    public GameCard() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255)); // 카드 배경색 설정
        setPreferredSize(new Dimension(200, 300));

        // 게임 제목
        JLabel titleLabel = new JLabel("BLACKJACK", SwingConstants.CENTER);
        titleLabel.setFont(new Font("휴먼둥근헤드라인", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(100, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 게임 설명
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
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 참여하기 버튼 클릭 시 동작 (다음 화면으로 전환할 코드 작성 가능)
                JOptionPane.showMessageDialog(GameCard.this, "게임에 참여합니다!");
            }
        });

        // 버튼 패널에 배치
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255,255,255)); // 버튼 패널도 카드 배경색과 맞춤
        buttonPanel.add(joinButton);

        // 상단에 여백 추가하여 버튼을 위로 이동
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 100, 0));
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
