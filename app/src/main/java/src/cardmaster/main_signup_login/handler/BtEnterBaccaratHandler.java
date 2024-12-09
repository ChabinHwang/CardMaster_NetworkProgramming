package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtEnterBaccaratHandler implements ActionListener {
    ClientUI cu;
    public BtEnterBaccaratHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.net.sendEnterRequest(2);
    }
}
