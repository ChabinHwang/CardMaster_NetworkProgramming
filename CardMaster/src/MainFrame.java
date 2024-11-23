import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("CardMaster");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 패널 추가
        ButtonPanel buttonPanel = new ButtonPanel();
        add(buttonPanel);

        setVisible(true);
    }
    

    public static void main(String[] args) {
        new MainFrame();
    }
}

