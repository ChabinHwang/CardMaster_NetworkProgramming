package src.cardmaster.main_menu;

import javax.swing.*;
import java.awt.*;

public class MenuFrame extends JPanel {
    public WelcomePanel welcomePanel;
    public MenuPanel menuPanel;
    public MenuFrame() {
        setSize(800, 500);

        // 상단 환영 패널 추가
        welcomePanel = new WelcomePanel();
        add(welcomePanel, BorderLayout.NORTH);

        // 메뉴 패널 추가
        menuPanel = new MenuPanel();
        add(menuPanel, BorderLayout.CENTER);

        setVisible(true);
    }

}
