package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtSignUpCancelHandler implements ActionListener {
    ClientUI cu;
    public BtSignUpCancelHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.setTitle("Signup");
        cu.setLocationRelativeTo(null);
        cu.setSize(800, 600);
        cu.setContentPane(cu.homeScreen);
    }
}
