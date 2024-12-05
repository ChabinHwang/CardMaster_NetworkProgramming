package Server;

import java.util.List;
import java.util.Map;

public interface dealerI {
    public void play(List<client> players, Map<client, Boolean> activePlayers, int numberOfActivePlayer);
    public void playRounds(client player, String action);
    public client checkPlayerTurn();
    public void handleBet(client player, int amount, String bet);
}
