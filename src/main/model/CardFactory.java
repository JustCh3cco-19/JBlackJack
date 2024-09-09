package main.model;

/**
 * La classe CardFactory serve per la creazione di oggetti {@link Card}.
 * 
 * <p>
 * Pattern adottati:
 * - Factory Method: determina il tipo di carta da creare (Ace, Face o Numeric)
 * basandosi sul rango fornito.
 * </p>
 */
public class CardFactory {
    /**
     * Metodo che crea e restituisce un oggetto {@link Card} basato sul seme e sul
     * rango forniti.
     * 
     * @param suit Il seme della carta.
     * @param rank Il rango della carta
     * @return Un'istanza di {@link Card}.
     * @throws IllegalArgumentException se il rango fornito non è valido.
     */
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
                throw new IllegalArgumentException("Rango non valido: " + rank, e);
            }
        }
    }
}
