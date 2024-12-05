package src.cardmaster.main_signup_login;

// HomeScreen.java
import javax.swing.*;
import java.awt.*;

public class HomeScreen extends JFrame {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    public HomeScreen() {
        setTitle("CardMaster");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Title
        JLabel titleLabel = new JLabel("CardMaster");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("SignUp");

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

        // Event handlers
        loginButton.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });

        signupButton.addActionListener(e -> {
            dispose();
            new SignUpScreen();
        });

        add(mainPanel);
        setVisible(true);
    }
}