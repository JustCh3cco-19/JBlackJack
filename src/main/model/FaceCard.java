package main.model;

/**
 * La classe FaceCard rappresenta una carta figura
 * (Jack, Regina, Re) in un mazzo di carte.
 * 
 * <p>
 * Questa classe implementa l'interfaccia {@link Card} e definisce il
 * comportamento
 * specifico per le carte figura, che hanno sempre un valore di 10 nel gioco.
 * </p>
 * 
 */
public class FaceCard implements Card {
    /** Il seme della carta */
    private String suit;

    /** Il rango della carta (Jack, Regina, Re) */
    private String rank;

    /**
     * Costruttore che modella una nuova carta figura.
     *
     * @param suit il seme della carta
     * @param rank il rango della carta (deve essere "jack", "queen", o "king")
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
     * @return il valore della carta, che è sempre 10 per le carte figura
     */
    @Override
    public int getValue() {
        return 10;
    }

    /**
     * Getter che restituisce il seme della carta.
     *
     * @return il seme della carta
     */
    @Override
    public String getSuit() {
        return suit;
    }

    /**
     * Getter che restituisce il rango della carta.
     *
     * @return il rango della carta ("jack", "queen", o "king")
     */
    @Override
    public String getRank() {
        return rank;
    }
}