package src.cardmaster.main_signup_login.handler;

import src.cardmaster.chat.ChatPanel;
import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtCasinoChatHandler implements ActionListener {
    ClientUI cu;
    public BtCasinoChatHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String text = ((ChatPanel)cu.casinowar.chatPanel).inputField.getText();
        ((ChatPanel)cu.casinowar.chatPanel).inputField.setText("");
        cu.net.sendMessage(text+"\n");
    }
}
