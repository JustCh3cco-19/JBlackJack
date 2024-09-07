package main.model;

/**
 * La classe NumericCard rappresenta una carta numerica in un mazzo di carte.
 * 
 * <p>
 * Questa classe implementa l'interfaccia {@link Card} e definisce il
 * comportamento delle carte numeriche, che hanno
 * un valore specifico basato sul loro rango.
 * </p>
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
     * @param suit  il seme della carta
     * @param rank  il rango della carta (deve essere una stringa rappresentante un
     *              numero da 2 a 10)
     * @param value il valore numerico della carta
     */
    public NumericCard(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    /**
     * Getter che restituisce il valore numerico della carta.
     *
     * @return il valore numerico della carta
     */
    @Override
    public int getValue() {
        return value;
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
     * @return il rango della carta
     */
    @Override
    public String getRank() {
        return rank;
    }
}