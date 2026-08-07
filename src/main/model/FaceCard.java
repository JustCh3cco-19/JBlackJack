package main.model;

/**
 * Represents the {@code FaceCard} class.
 */
public class FaceCard implements Card {
    /** Stores the suit value. */
    private String suit;

    /** Stores the rank value. */
    private String rank;

    /**
     * Creates a new {@code FaceCard} instance.
     * @param suit the suit
     * @param rank the rank
     */
    public FaceCard(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the value.
     * @return the value
     */
    @Override
    public int getValue() {
        return 10;
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