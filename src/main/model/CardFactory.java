package main.model;

/**
 * Represents the {@code CardFactory} class.
 */
public class CardFactory {
    /**
     * Creates the card.
     * @param suit the suit
     * @param rank the rank
     * @return the operation result
     */
    public static Card createCard(String suit, String rank) {
        if (suit == null || !java.util.Set.of("hearts", "diamonds", "clubs", "spades").contains(suit)) {
            throw new IllegalArgumentException("Seme non valido: " + suit);
        }
        if (rank == null) {
            throw new IllegalArgumentException("Il rango non può essere null");
        }
        if (rank.equals("ace")) {
            return new AceCard(suit);
        } else if (rank.equals("jack") || rank.equals("queen") || rank.equals("king")) {
            return new FaceCard(suit, rank);
        } else {
            try {
                int value = Integer.parseInt(rank);
                if (value < 2 || value > 10) {
                    throw new IllegalArgumentException("Rango non valido: " + rank);
                }
                return new NumericCard(suit, rank, value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Rango non valido: " + rank, e);
            }
        }
    }
}
