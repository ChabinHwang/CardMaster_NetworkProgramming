package src.cardmaster.main_signup_login;

// HomeScreen.java
import src.cardmaster.main_signup_login.handler.BtSignUpHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeScreen extends JPanel {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    public JPanel mainPanel;
    public JLabel titleLabel;
    public JPanel buttonPanel;
    public JButton loginButton;
    public JButton signupButton;

    public HomeScreen() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title
        titleLabel = new JLabel("CardMaster");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        loginButton = new JButton("Login");
        signupButton = new JButton("SignUp");

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(200, 40);
        loginButton.setPreferredSize(buttonSize);
        signupButton.setPreferredSize(buttonSize);

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(signupButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Add components to main panel
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(50));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

}