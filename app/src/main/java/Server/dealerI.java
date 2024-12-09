package Server;

import java.util.List;
import java.util.Map;

public interface dealerI {
    public void play(List<client> players, Map<String, Boolean> activePlayers, int numberOfActivePlayer);
    public void playRounds(String action);
    public client checkPlayerTurn();
    public void changePlayerTurn(client client);
    public void handleBet(int amount, String bet);
}
