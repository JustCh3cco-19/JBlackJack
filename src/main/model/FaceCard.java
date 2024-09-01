package main.model;

public class FaceCard implements Card {
    private String suit;
    private String rank;

    public FaceCard(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public int getValue() {
        return 10;
    }

    @Override
    public String getSuit() {
        return suit;
    }

    @Override
    public String getRank() {
        return rank;
    }
}
