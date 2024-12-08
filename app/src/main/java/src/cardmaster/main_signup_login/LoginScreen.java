package src.cardmaster.main_signup_login;


import org.json.JSONObject;
import src.cardmaster.main_menu.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class LoginScreen extends JPanel {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    public JPanel mainPanel;
    public JLabel titleLabel;
    public JTextField idField;
    public JPasswordField pwField;
    public JButton loginButton;
    public JButton backButton;
    public JPanel formPanel;
    public JPanel buttonPanel;

    public LoginScreen() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        idField = new JTextField(20);
        pwField = new JPasswordField(20);

        loginButton = new JButton("로그인하기");
        backButton = new JButton("돌아가기");

        // Layout components
        formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("PW:"));
        formPanel.add(pwField);

        buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }
}
