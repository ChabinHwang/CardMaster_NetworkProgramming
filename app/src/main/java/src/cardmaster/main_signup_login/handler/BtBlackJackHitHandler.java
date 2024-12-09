package src.cardmaster.main_signup_login.handler;

import src.cardmaster.chat.ChatPanel;
import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtBlackJackHitHandler implements ActionListener {
    ClientUI cu;
    public BtBlackJackHitHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        cu.net.sendHitOrStand("hit");
    }
}
