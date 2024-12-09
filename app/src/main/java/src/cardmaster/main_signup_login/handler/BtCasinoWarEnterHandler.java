package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtCasinoWarEnterHandler implements ActionListener {
    ClientUI cu;
    public BtCasinoWarEnterHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        int amount = 0;
        try{
            amount = Integer.parseInt(cu.casinowar.betField.getText());
        }catch(NumberFormatException a){
            JOptionPane.showMessageDialog(cu, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (amount <= 0) {
            JOptionPane.showMessageDialog(cu, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
        }
        cu.net.sendWarOrSurrender(amount, "false");
    }
}
