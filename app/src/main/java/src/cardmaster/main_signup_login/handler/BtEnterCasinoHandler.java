package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtEnterCasinoHandler implements ActionListener {
    ClientUI cu;
    public BtEnterCasinoHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.net.sendEnterRequest(1);
    }
}
