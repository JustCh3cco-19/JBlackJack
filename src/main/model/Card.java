/**
 * Questa interfacci rappresenta una carta da gioco.
 * 
 * Pattern adottati: 
 * - Template Method attraverso l'implementazione di un metodo di default * {@link #getImageFileName()}.
 * 
 * <p>
 * Il pattern "Template Method" definisce lo scheletro di un algoritmo in un metodo,
 * lasciando alcuni passi alle sottoclassi. In questo caso, il metodo {@link #getImageFileName()}
 * fornisce un'implementazione di default che può essere sovrascritta se necessario.
 * </p>
 */
package main.model;

public interface Card {

    /**
     * Getter che restituisce il valore numerico della carta.
     *
     * @return il valore della carta
     */
    int getValue();

    /**
     * Getter che restituisce il seme della carta.
     *
     * @return il seme della carta
     */
    String getSuit();

    /**
     * Getter che restituisce il rango della carta (es. Asso, Re, Regina, ecc.).
     *
     * @return il rango della carta
     */
    String getRank();

    /**
     * Getter che restituisce il nome del file associato alla carta.
     *
     * @return il nome del file immagine della carta
     */
    default String getImageFileName() {
        return getRank() + "*of*" + getSuit() + ".png";
    }
}