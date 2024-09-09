package main.model;

/**
 * La classe AceCard rappresenta la carta Asso.
 * Implementa l'interfaccia {@link Card} e fornisce il comportamento
 * specifico per la carta Ace, che ha un valore fisso di 11 in questa
 * implementazione.
 * 
 * <p>
 * Pattern utilizzati:
 * - Composite: le singole carte (Leaf) che le collezioni di carte (Composite)
 * implementano la stessa interfaccia {@link Card}.
 * </p>
 * 
 */
public class AceCard implements Card {
    private String suit;

    /**
     * Costruttore che inizializza una carta Asso con il seme specificato.
     * 
     * @param suit Il seme della carta Asso.
     */
    public AceCard(String suit) {
        this.suit = suit;
    }

    /**
     * Getter che restituisce il valore della carta Asso.
     * 
     * @return Il valore della carta Asso, che in questa implementazione è sempre
     *         11.
     */
    @Override
    public int getValue() {
        return 11;
    }

    /**
     * Getter che restituisce il seme della carta Asso.
     * 
     * @return Il seme della carta Asso.
     */
    @Override
    public String getSuit() {
        return suit;
    }

    /**
     * Getter che restituisce il rango della carta Asso.
     * 
     * @return Il rango della carta, che è sempre "ace" in questa classe.
     */
    @Override
    public String getRank() {
        return "ace";
    }
}
