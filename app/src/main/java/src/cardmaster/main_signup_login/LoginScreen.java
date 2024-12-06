package src.cardmaster.main_signup_login;


import org.json.JSONObject;
import src.cardmaster.main_menu.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class LoginScreen extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;

    public LoginScreen() {
        setTitle("Login - CardMaster");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField idField = new JTextField(20);
        JPasswordField pwField = new JPasswordField(20);

        JButton loginButton = new JButton("로그인하기");
        JButton backButton = new JButton("돌아가기");

        // Layout components
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("PW:"));
        formPanel.add(pwField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        // Event handlers
        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());

            try {
                JSONObject jsonObject = UserDatabase.validateUser(id, pw);
                if (jsonObject.has("error")) {
                    JOptionPane.showMessageDialog(this, "Invalid ID or Password", "Login Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 성공!");
                    dispose();
                    new MenuFrame(id, jsonObject.getJSONObject("data").getInt("money")); // MenuFrame에 사용자 이름과 초기 포인트 전달
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new HomeScreen();
        });

        add(mainPanel);
        setVisible(true);
    }
}
