package src.cardmaster.main_signup_login.handler;

import src.cardmaster.chat.ChatPanel;
import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtBlackJackChatHandler implements ActionListener {
    ClientUI cu;
    public BtBlackJackChatHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String text = ((ChatPanel)cu.blackjack.chatPanel).inputField.getText();
        cu.net.sendMessage(text+"\n");
    }
}
