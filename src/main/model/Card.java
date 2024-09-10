package main.model;

/**
 * Questa interfaccia rappresenta una carta da gioco.
 * 
 * <p>
 * Le classi che implementano questa interfaccia devono fornire le
 * implementazioni concrete per i metodi {@link #getValue()},
 * {@link #getSuit()}, e {@link #getRank()}.
 * </p>
 * 
 * <p>
 * Pattern adottati:
 * - Template Method: tramite il metodo {@link #getImageFileName()},
 * implementato come
 * default, ed altri metodi dell'interfaccia per
 * costruire il nome del file immagine associato alla carta.
 * - Strategy: l'interfaccia {@link Card} rappresenta una strategia per la
 * gestione delle carte, con implementazioni concrete che definiscono il
 * comportamento specifico per il valore, il seme e il rango delle carte.
 * </p>
 */
public interface Card {

    /**
     * Getter che restituisce il valore numerico della carta.
     *
     * @return Il valore numerico della carta.
     */
    int getValue();

    /**
     * Getter restituisce il seme della carta.
     *
     * @return Il seme della carta (es. "hearts", "clubs", "spades", "diamonds").
     */
    String getSuit();

    /**
     * Getter che restituisce il rango della carta (es. ace, king, queen, ecc.).
     *
     * @return Il rango della carta (es. "ace", "king", "queen", ecc.).
     */
    String getRank();

    /**
     * Metodo di default che restituisce il nome del file immagine associato alla
     * carta.
     * <p>
     * Questo metodo è implementato con una logica predefinita che costruisce il
     * nome del file immagine utilizzando il rango e il seme della carta. (es. per
     * un Asso di cuori, il nome del file sarà "ace_of_hearts.png").
     * </p>
     *
     * @return Il nome del file immagine della carta.
     */
    default String getImageFileName() {
        return getRank() + "_of_" + getSuit() + ".png";
    }
}
