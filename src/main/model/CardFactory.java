package main.model;

/**
 * Classe CardFactory per la creazione di oggetti {@link Card}.
 * 
 * <p>
 * Pattern adottati:
 * - Factory Method: definisce un'interfaccia per creare un oggetto,
 * ma lascia alle sottoclassi decidere quale classe istanziare.
 * </p>
 */
public class CardFactory {
    /**
     * Metodo che crea e restituisce un oggetto {@link Card} basato sul seme e sul
     * rango forniti.
     * 
     * <p>
     * Pattern adottati:
     * - Factory Method: determina il tipo di carta da creare (Asso, Figura o
     * Numerica) basandosi sul rango fornito.
     * </p>
     *
     * @param suit il seme della carta
     * @param rank il rango della carta
     * @return un'istanza di {@link Card} appropriata
     * @throws IllegalArgumentException se il rango fornito non è valido
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
                throw new IllegalArgumentException("Invalid rank: " + rank, e);
            }
        }
    }
}
