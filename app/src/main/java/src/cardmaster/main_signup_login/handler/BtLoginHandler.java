package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtLoginHandler implements ActionListener {
    ClientUI cu;
    public BtLoginHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.setTitle("login");
        cu.setLocationRelativeTo(null);
        cu.setSize(500, 400);
        cu.setContentPane(cu.loginScreen);
    }
}
