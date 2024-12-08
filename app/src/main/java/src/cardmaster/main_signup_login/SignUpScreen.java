package src.cardmaster.main_signup_login;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class SignUpScreen extends JPanel {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 350;
    public JPanel mainPanel;
    public JLabel titleLabel;
    public JTextField idField;
    public JPasswordField pwField;
    public JPasswordField pwConfirmField;
    public JButton signUpButton;
    public JButton backButton;
    public JPanel formPanel;
    public JPanel buttonPanel;

    public SignUpScreen() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("SignUp");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        idField = new JTextField(20);
        pwField = new JPasswordField(20);
        pwConfirmField = new JPasswordField(20);

        signUpButton = new JButton("회원가입하기");
        backButton = new JButton("돌아가기");

        // Layout components
        formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("PW:"));
        formPanel.add(pwField);
        formPanel.add(new JLabel("PW확인:"));
        formPanel.add(pwConfirmField);

        buttonPanel = new JPanel();
        buttonPanel.add(signUpButton);
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