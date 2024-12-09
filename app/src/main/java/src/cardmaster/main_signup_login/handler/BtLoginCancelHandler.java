package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtLoginCancelHandler implements ActionListener {
    ClientUI cu;
    public BtLoginCancelHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.setTitle("main");
        cu.setLocationRelativeTo(null);
        cu.setSize(800, 600);
        cu.setContentPane(cu.homeScreen);
    }
}
