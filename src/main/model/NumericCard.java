package main.model;

public class NumericCard implements Card {
    private String suit;
    private String rank;
    private int value;

    public NumericCard(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
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
