import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {
    public CustomButton(String text) {
        super(text);
        setFont(new Font("Comic Sans MS", Font.BOLD, 18));
        setBackground(new Color(170, 170, 170)); // 버튼 색상 설정
        setForeground(Color.WHITE); // 버튼 텍스트 색상 설정
        setFocusPainted(false);
        setBorderPainted(false);
        setPreferredSize(new Dimension(200, 50)); // 버튼 크기 설정
    }
}
