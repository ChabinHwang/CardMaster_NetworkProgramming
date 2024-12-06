package src.cardmaster.main_menu;

import src.cardmaster.Casinowar_game.Casinowar;
import src.cardmaster.Baccarat_game.Baccarat;
import src.cardmaster.blackjack_game.Blackjack;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    String userName;
    int points;
    public void runMenuPanel() {
        setLayout(new GridLayout(1, 3, 10, 0)); // 3개의 게임 카드를 가로로 배치
        setBackground(Color.WHITE);

        // 첫 번째 카드: Blackjack
        GameCard blackjackCard = new GameCard("BLACKJACK", () -> Blackjack.main(new String[]{String.valueOf(points), userName}));
        blackjackCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(blackjackCard);

        // 두 번째 카드: Baccarat
        GameCard baccaratCard = new GameCard("BACCARAT", () -> Baccarat.main(new String[]{String.valueOf(points), userName}));
        baccaratCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(baccaratCard);

        // 세 번째 카드: Baccarat
        GameCard casinowarCard = new GameCard("CASINO WAR", () -> Casinowar.main(new String[]{String.valueOf(points), userName}));
        casinowarCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(casinowarCard);
    }

    public MenuPanel(String userName, int points) {
        this.userName = userName;
        this.points = points;
        runMenuPanel();
    }
}
