package Server;

public class Card {
    private final String suit;
    private final int rank;

    public Card(String suit, int rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getValue() {
        return rank;
    }

    @Override
    public String toString() {
        return suit + rank;
    }
}