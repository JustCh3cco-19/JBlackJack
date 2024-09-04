package main.model;

public class AceCard implements Card {
    private String suit;

    public AceCard(String suit) {
        this.suit = suit;
    }

    @Override
    public int getValue() {
        return 11;
    }

    @Override
    public String getSuit() {
        return suit;
    }

    @Override
    public String getRank() {
        return "ace";
    }
}
