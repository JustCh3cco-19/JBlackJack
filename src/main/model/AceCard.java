package main.model;

/**
 * Represents the {@code AceCard} class.
 */
public class AceCard implements Card {
    /** Stores the suit value. */
    private String suit;

    /**
     * Creates a new {@code AceCard} instance.
     * @param suit the suit
     */
    public AceCard(String suit) {
        this.suit = suit;
    }

    /**
     * Returns the value.
     * @return the value
     */
    @Override
    public int getValue() {
        return 11;
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
        return "ace";
    }
}
