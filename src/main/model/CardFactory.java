package main.model;

public class CardFactory {
    public static Card createCard(String suit, String rank) {
        if (rank.equals("ace")) {
            return new AceCard(suit);
        } else if (rank.equals("jack") || rank.equals("queen") || rank.equals("king")) {
            return new FaceCard(suit, rank);
        } else {
            try {
                int value = Integer.parseInt(rank);
                return new NumericCard(suit, rank, value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid rank: " + rank, e);
            }
        }
    }
}
