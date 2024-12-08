package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class gameList {
    private List<game> list;//0,1,2 이렇게 3개
    private messageGenerator mg;

    public gameList(messageGenerator mg) {
        list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add(new game(mg, i));
        }
    }

    public synchronized game getGameInstance(int game) {
        return list.get(game);
    }

    public synchronized int numberOfGames() {
        return list.size();
    }
}
