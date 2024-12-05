package src.cardmaster.main_signup_login;

import javax.swing.*;
import java.awt.*;

public class SignUpScreen extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 350;

    public SignUpScreen() {
        setTitle("Sign Up - CardMaster");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("SignUp");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField idField = new JTextField(20);
        JPasswordField pwField = new JPasswordField(20);
        JPasswordField pwConfirmField = new JPasswordField(20);

        JButton signupButton = new JButton("회원가입하기");
        JButton backButton = new JButton("돌아가기");

        // Layout components
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("PW:"));
        formPanel.add(pwField);
        formPanel.add(new JLabel("PW확인:"));
        formPanel.add(pwConfirmField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(signupButton);
        buttonPanel.add(backButton);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);

        // Event handlers
        signupButton.addActionListener(e -> {
            String id = idField.getText();
            String pw = new String(pwField.getPassword());
            String pwConfirm = new String(pwConfirmField.getPassword());

            if (id.trim().isEmpty() || pw.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!pw.equals(pwConfirm)) {
                JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (UserDatabase.addUser(id, pw)) {
                JOptionPane.showMessageDialog(this, "회원가입 성공!");
                dispose();
                new LoginScreen();
            } else {
                JOptionPane.showMessageDialog(this, "이미 존재하는 ID입니다.", "Error", JOptionPane.ERROR_MESSAGE);
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