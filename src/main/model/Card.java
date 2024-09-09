package main.model;

/**
 * Questa interfaccia rappresenta una carta da gioco.
 * 
 * <p>
 * Pattern adottati:
 * - Template Method: il metodo {@link #getImageFileName()} è implementato come
 * default ed utilizza altri metodi dell'interfaccia per
 * costruire il nome del file immagine associato alla carta.
 * </p>
 */
public interface Card {

    /**
     * Restituisce il valore numerico della carta.
     *
     * @return Il valore numerico della carta.
     */
    int getValue();

    /**
     * Restituisce il seme della carta.
     *
     * @return Il seme della carta (es. "hearts", "clubs", "spades", "diamonds").
     */
    String getSuit();

    /**
     * Restituisce il rango della carta (es. Asso, Re, Regina, ecc.).
     *
     * @return Il rango della carta (es. "ace", "king", "queen", ecc.).
     */
    String getRank();

    /**
     * Metodo di default che restituisce il nome del file immagine associato alla
     * carta.
     * <p>
     * Questo metodo è implementato con una logica predefinita che costruisce il
     * nome del file immagine utilizzando il rango e il seme della carta. Ad
     * esempio, per un Asso di cuori, il nome del file sarà "ace_of_hearts.png".
     * </p>
     *
     * @return Il nome del file immagine della carta.
     */
    default String getImageFileName() {
        return getRank() + "_of_" + getSuit() + ".png";
    }
}
