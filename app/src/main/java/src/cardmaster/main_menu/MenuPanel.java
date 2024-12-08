package src.cardmaster.main_menu;

import src.cardmaster.Casinowar_game.Casinowar;
import src.cardmaster.Baccarat_game.Baccarat;
import src.cardmaster.blackjack_game.Blackjack;
import src.cardmaster.main_signup_login.handler.BtEnterBlackJackHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    String userName;
    int points;
    public GameCard blackjackCard;
    public GameCard baccaratCard;
    public GameCard casinowarCard;

    public MenuPanel(){
        setLayout(new GridLayout(1, 3, 10, 0)); // 3개의 게임 카드를 가로로 배치
        setBackground(Color.WHITE);
        blackjackCard = new GameCard("BLACKJACK", 0);
        blackjackCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(blackjackCard);

        // 두 번째 카드: Baccarat
        baccaratCard = new GameCard("BACCARAT", 0);
        baccaratCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(baccaratCard);

        // 세 번째 카드: Baccarat
        casinowarCard = new GameCard("CASINO WAR", 0);
        casinowarCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(casinowarCard);
    }

//    public void update(int blackJackPlayer, int baccaratPlayer, int casinoWarPlayer){
//        removeAll();
//        setLayout(new GridLayout(1, 3, 10, 0)); // 3개의 게임 카드를 가로로 배치
//        setBackground(Color.WHITE);
//
//        // 첫 번째 카드: Blackjack
//        blackjackCard = new GameCard("BLACKJACK", blackJackPlayer);
//        blackjackCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
//        add(blackjackCard);
//
//        // 두 번째 카드: Baccarat
//        baccaratCard = new GameCard("BACCARAT", baccaratPlayer);
//        baccaratCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
//        add(baccaratCard);
//
//        // 세 번째 카드: Baccarat
//        casinowarCard = new GameCard("CASINO WAR", casinoWarPlayer);
//        casinowarCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
//        add(casinowarCard);
//        revalidate();
//        repaint();
//    }
}
