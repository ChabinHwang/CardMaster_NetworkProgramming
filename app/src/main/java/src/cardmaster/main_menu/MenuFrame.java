package src.cardmaster.main_menu;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JFrame {
    public MenuFrame(String userName, int points) {
        setTitle("Game Menu");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 상단 환영 패널 추가
        WelcomePanel welcomePanel = new WelcomePanel(userName, points);
        add(welcomePanel, BorderLayout.NORTH);

        // 메뉴 패널 추가
        MenuPanel MenuPanel = new MenuPanel();
        add(MenuPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuFrame("Chabin", 120220);
    }
}
