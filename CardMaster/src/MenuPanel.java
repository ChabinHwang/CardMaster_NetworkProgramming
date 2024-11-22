import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        setLayout(new GridLayout(1, 3, 10, 0)); // 3개의 게임 카드를 가로로 배치
        setBackground(Color.WHITE);

        // GameCard 패널 3개 생성
        for (int i = 0; i < 3; i++) {
            GameCard gameCard = new GameCard();
            gameCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2)); // 검정색 테두리 추가
            add(gameCard);
        }
    }
}
