import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {
    public ButtonPanel() {
        setLayout(new BorderLayout());

        // 상단 타이틀
        JLabel titleLabel = new JLabel("CardMaster", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 60));
        add(titleLabel, BorderLayout.NORTH);

        // 버튼 패널
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(2, 1, 0, 10));
        buttonContainer.setBackground(new Color(245, 245, 245));

        // 버튼 추가
        CustomButton loginButton = new CustomButton("Login");
        CustomButton signUpButton = new CustomButton("SignUp");

        buttonContainer.add(loginButton);
        buttonContainer.add(signUpButton);

        // 버튼 패널 가운데 정렬
        JPanel centeredPanel = new JPanel(new GridBagLayout());
        centeredPanel.setBackground(new Color(245, 245, 245));
        centeredPanel.add(buttonContainer);

        add(centeredPanel, BorderLayout.CENTER);

        // 배경색 설정
        setBackground(new Color(255, 255, 255));
    }
}
