package cardmaster.main_menu;

import cardmaster.blackjack_game.Blackjack;
import cardmaster.baccarat_game.Baccarat;
import cardmaster.Casinowar_game.Casinowar;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel() {
        setLayout(new GridLayout(1, 3, 10, 0)); // 3개의 게임 카드를 가로로 배치
        setBackground(Color.WHITE);

        // 첫 번째 카드: Blackjack
        GameCard blackjackCard = new GameCard("BLACKJACK", () -> Blackjack.main(new String[]{}));
        blackjackCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(blackjackCard);

        // 두 번째 카드: Baccarat
        GameCard baccaratCard = new GameCard("BACCARAT", () -> Baccarat.main(new String[]{}));
        baccaratCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(baccaratCard);

        // 세 번째 카드: Baccarat
        GameCard casinowarCard = new GameCard("CASINO WAR", () -> Casinowar.main(new String[]{}));
        casinowarCard.setBorder(BorderFactory.createLineBorder(new Color(92, 64, 51), 2));
        add(casinowarCard);
    }
}
