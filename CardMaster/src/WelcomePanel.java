import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    public WelcomePanel(String userName, int points) {
        setLayout(new BorderLayout());
        setBackground(new Color(0,0,0)); // 상단 배경색 설정

        // 왼쪽 환영 메시지
        JLabel welcomeLabel = new JLabel("<html><b>" + userName + "</b> 님, 환영합니다.</html>");
        welcomeLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 18)); // 귀여운 글씨체 적용
        welcomeLabel.setForeground(Color.WHITE);
        
        // 환영 메시지 오른쪽으로 이동 (좌측에 10픽셀 여백 추가)
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(welcomeLabel, BorderLayout.WEST);

        // 오른쪽 잔액 표시
        JLabel pointsLabel = new JLabel("잔액 : " + points + " points", SwingConstants.CENTER);
        pointsLabel.setFont(new Font("휴먼둥근헤드라인", Font.PLAIN, 16)); // 귀여운 글씨체 적용
        pointsLabel.setForeground(Color.WHITE);

        // 글씨 크기에 맞춘 배경 패널 설정
        JPanel pointsPanel = new JPanel();
        pointsPanel.setBackground(new Color(170, 170, 170)); // 어두운 고동색 배경
        pointsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 16)); // 패딩 추가하여 아래로 내림
        pointsPanel.add(pointsLabel);

        // pointsPanel 크기 설정
        pointsPanel.setPreferredSize(new Dimension(pointsLabel.getPreferredSize().width + 20, pointsLabel.getPreferredSize().height + 10));

        add(pointsPanel, BorderLayout.EAST);

        setPreferredSize(new Dimension(800, 50));
    }
}
