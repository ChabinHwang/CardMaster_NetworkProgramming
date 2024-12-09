package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;
import src.cardmaster.main_signup_login.HomeScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtSignUpFInishHandler implements ActionListener {
    ClientUI cu;
    public BtSignUpFInishHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        String id = cu.signUp.idField.getText();
        String pw = new String(cu.signUp.pwField.getPassword());
        String pwConfirm = new String(cu.signUp.pwConfirmField.getPassword());
        cu.net.sendCreateRequest(id, pw);
    }
}
