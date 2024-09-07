package main.model;

/**
 * Classe CardFactory per la creazione di oggetti {@link Card}.
 * 
 * Pattern adottati:
 * - Factory Method: definisce un'interfaccia per creare un oggetto,
 * ma lascia alle sottoclassi decidere quale classe istanziare.
 */
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
