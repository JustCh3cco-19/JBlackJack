package main.model;

/**
 * Represents the {@code Card} interface.
 */
public interface Card {

    /**
     * Returns the value.
     * @return the value
     */
    int getValue();

    /**
     * Returns the suit.
     * @return the suit
     */
    String getSuit();

    /**
     * Returns the rank.
     * @return the rank
     */
    String getRank();

    /**
     * Returns the image file name.
     * @return the image file name
     */
    default String getImageFileName() {
        return getRank() + "_of_" + getSuit() + ".png";
    }
}
