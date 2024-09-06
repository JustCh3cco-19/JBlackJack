package main.model;

/**
 * La classe AceCard rappresenta la carta Asso e implementa il pattern Strategy.
 * Implementa l'interfaccia {@link Card}, definendo il comportamento
 * specifico per la carta Asso,
 * in questo caso per comodità assumerà sempre il valore 11.
 * 
 * <p>
 * Questa classe è applica il pattern Strategy, dove diverse implementazioni
 * di {@link Card} possono fornire comportamenti diversi
 * per diversi tipi di carte.
 * </p>
 * 
 * <p>
 * Inoltre, AceCard fa parte del pattern Composite, dove singole carte (Leaf)
 * e collezioni di carte (Composite) implementano la stessa interfaccia
 * {@link Card}.
 * </p>
 * 
 */
public class AceCard implements Card {
    private String suit;

    /**
     * Costruttore AceCard che inizializza una carta Asso con il seme specificato.
     * 
     * @param suit Il seme della carta Asso.
     */
    public AceCard(String suit) {
        this.suit = suit;
    }

    /**
     * Getter che restituisce il valore dell'Asso.
     * 
     * @return Il valore dell'Asso, fissato a 11 in questa implementazione.
     */
    @Override
    public int getValue() {
        return 11;
    }

    /**
     * Getter che restituisce il seme dell'asso.
     * 
     * @return Il seme della carta Asso.
     */
    @Override
    public String getSuit() {
        return suit;
    }

    /**
     * Getter che restituisce il rango dell'Asso.
     * 
     * @return Il rango della carta, che è sempre "ace" per questa classe.
     */
    @Override
    public String getRank() {
        return "ace";
    }
}
