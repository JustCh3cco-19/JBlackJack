package main.model;

/**
 * La classe NumericCard rappresenta una carta numerica.
 * Implementa l'interfaccia {@link Card} e fornisce il comportamento
 * specifico per la carta NumericCard.
 * 
 * <p>
 * Pattern utilizzati:
 * - Composite: le singole carte (Leaf) che le collezioni di carte (Composite)
 * implementano la stessa interfaccia {@link Card}.
 * </p>
 * 
 */
public class NumericCard implements Card {
    /** Il seme della carta */
    private String suit;

    /** Il rango della carta (es. "2", "3", ..., "10") */
    private String rank;

    /** Il valore numerico della carta */
    private int value;

    /**
     * Costruttore che modella una nuova carta numerica.
     *
     * @param suit  Il seme della carta.
     * @param rank  Il rango della carta (deve essere una stringa rappresentante un
     *              numero da 2 a 10).
     * @param value Il valore numerico della carta.
     */
    public NumericCard(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Getter che restituisce il valore numerico della carta.
     *
     * @return Il valore numerico della carta.
     */
    @Override
    public int getValue() {
        return value;
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
     * @return Il rango della carta.
     */
    @Override
    public String getRank() {
        return rank;
    }
}