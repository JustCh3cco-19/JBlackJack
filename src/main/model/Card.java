package main.model;

public interface Card {
    int getValue();

    String getSuit();

    String getRank();

    default String getImageFileName() {
        return getRank().toLowerCase() + "_of_" + getSuit().toLowerCase() + ".png";
    }
}
