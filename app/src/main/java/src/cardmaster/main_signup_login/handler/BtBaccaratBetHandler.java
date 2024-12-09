package src.cardmaster.main_signup_login.handler;

import src.cardmaster.main_signup_login.ClientUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BtBaccaratBetHandler implements ActionListener {
    ClientUI cu;
    public BtBaccaratBetHandler(ClientUI cu){
        this.cu = cu;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        int amount = 0;
        try{
            amount = Integer.parseInt(cu.baccarat.betField.getText());
        }catch(NumberFormatException a){
            JOptionPane.showMessageDialog(cu, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (amount <= 0) {
            JOptionPane.showMessageDialog(cu, "유효한 금액을 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!cu.baccarat.playerBet.isSelected() && !cu.baccarat.bankerBet.isSelected() && !cu.baccarat.tieBet.isSelected()) {
            JOptionPane.showMessageDialog(cu, "베팅할 대상을 선택하세요.", "오류", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            String bet = "";
            if (cu.baccarat.playerBet.isSelected()) bet = "Player";
            if (cu.baccarat.bankerBet.isSelected()) bet = "Banker";
            if (cu.baccarat.tieBet.isSelected()) bet = "Tie";
            cu.net.sendBetRequest(amount, bet, 2);
        }
    }
}
