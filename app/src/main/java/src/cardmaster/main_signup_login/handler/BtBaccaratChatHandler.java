package src.cardmaster.main_signup_login.handler;

import src.cardmaster.chat.ChatPanel;
import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtBaccaratChatHandler implements ActionListener {
    ClientUI cu;
    public BtBaccaratChatHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String text = ((ChatPanel)cu.baccarat.chatPanel).inputField.getText();
        ((ChatPanel)cu.baccarat.chatPanel).inputField.setText("");
        cu.net.sendMessage(text+"\n");
    }
}
