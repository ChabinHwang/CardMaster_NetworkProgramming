package src.cardmaster.main_signup_login.handler;

import com.mysql.cj.xdevapi.Client;
import src.cardmaster.main_signup_login.ClientUI;
import src.cardmaster.main_signup_login.HomeScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtSignUpHandler implements ActionListener {
    ClientUI cu;
    public BtSignUpHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.setTitle("lobby");
        cu.setLocationRelativeTo(null);
        cu.setSize(500, 400);
        cu.setContentPane(cu.signUp);
    }
}
