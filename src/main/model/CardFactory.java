package main.model;

public class CardFactory {
    public static Card createCard(String suit, String rank) {
        if (rank.equals("Asso")) {
            return new AceCard(suit);
        } else if (rank.equals("Jack") || rank.equals("Regina") || rank.equals("Re")) {
            return new FaceCard(suit, rank);
        } else {
            return new NumericCard(suit, rank, Integer.parseInt(rank));
        }
    }
}
