package main.model;

/**
 * Represents the {@code NumericCard} class.
 */
public class NumericCard implements Card {
    /** Stores the suit value. */
    private String suit;

    /** Stores the rank value. */
    private String rank;

    /** Stores the value value. */
    private int value;

    /**
     * Creates a new {@code NumericCard} instance.
     * @param suit the suit
     * @param rank the rank
     * @param value the value
     */
    public NumericCard(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Returns the value.
     * @return the value
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * Returns the suit.
     * @return the suit
     */
    @Override
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the rank.
     * @return the rank
     */
    @Override
    public String getRank() {
        return rank;
    }
}