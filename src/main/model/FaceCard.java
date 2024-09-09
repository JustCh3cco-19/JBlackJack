package main.model;

/**
 * La classe FaceCard rappresenta una carta figura (Jack, Queen, King).
 * Implementa l'interfaccia {@link Card} e fornisce il comportamento
 * specifico per la carta FaceCard, che ha un valore fisso di 10.
 * 
 * <p>
 * Pattern utilizzati:
 * - Composite: le singole carte (Leaf) che le collezioni di carte (Composite)
 * implementano la stessa interfaccia {@link Card}.
 * </p>
 * 
 */
public class FaceCard implements Card {
    /** Il seme della carta */
    private String suit;

    /** Il rango della carta (Jack, Queen, King) */
    private String rank;

    /**
     * Costruttore che modella una nuova carta figura.
     *
     * @param suit Il seme della carta
     * @param rank Il rango della carta (deve essere "jack", "queen", o "king")
     */
    public FaceCard(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Getter che restituisce il valore della carta figura.
     *
     * Nelle regole standard, tutte le carte figura valgono 10 punti.
     *
     *
     * @return Il valore della carta, che è sempre 10 per le carte figura.
     */
    @Override
    public int getValue() {
        return 10;
    }

    /**
     * Getter che restituisce il seme della carta.
     *
     * @return Il seme della carta.
     */
    @Override
    public String getSuit() {
        return suit;
    }

    /**
     * Getter che restituisce il rango della carta.
     *
     * @return Il rango della carta ("jack", "queen", o "king").
     */
    @Override
    public String getRank() {
        return rank;
    }
}