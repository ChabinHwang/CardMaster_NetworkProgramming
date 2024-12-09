package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtLoginEnterHandler implements ActionListener {
    ClientUI cu;
    public BtLoginEnterHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String id = cu.loginScreen.idField.getText().trim();
        String pw = String.valueOf(cu.loginScreen.pwField.getText());
        cu.net.sendLoginRequest(id, pw);
    }
}
