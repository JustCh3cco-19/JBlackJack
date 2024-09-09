package main.model;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Player rappresenta un giocatore.
 * 
 * <p>
 * Questa classe gestisce la mano del giocatore, la sua strategia di
 * gioco, e fornisce metodi per interagire in partita durante il gioco.
 * </p>
 */
public class Player {
    /** La mano attuale del giocatore */
    private List<Card> hand;
    /** Il nome del giocatore */
    private String name;
    /** La strategia di gioco del giocatore */
    private PlayerStrategy strategy;
    /** Indica se il giocatore è umano o meno */
    private boolean isHuman;

    /**
     * Costruttore che modella un nuovo giocatore.
     *
     * @param name     Il nome del giocatore.
     * @param strategy La strategia di gioco.
     * @param isHuman  true se il giocatore è umano, false altrimenti.
     */
    public Player(String name, PlayerStrategy strategy, boolean isHuman) {
        this.name = name;
        this.strategy = strategy;
        this.isHuman = isHuman;
        this.hand = new ArrayList<>();
    }

    /**
     * Metodo che aggiunge una carta alla mano del giocatore.
     *
     * @param card La carta da aggiungere.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Metodo che determina se il giocatore vuole chiedere un'altra carta.
     *
     * @return true se il giocatore vuole un'altra carta, false altrimenti.
     */
    public boolean wantsToHit() {
        return strategy.wantsToHit(hand);
    }

    /**
     * Metodo che rimuove tutte le carte dalla mano del giocatore.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Metodo che indica se il giocatore è umano o meno.
     *
     * @return true se il giocatore è umano, false altrimenti.
     */
    public boolean isHuman() {
        return isHuman;
    }

    /**
     * Getter che restituisce il valore totale della mano del giocatore.
     * 
     * <p>
     * Una nota importante è che ,in questa implementazione, l'Asso vale
     * sempre 11 punti indipendentemente dal valore totale della mano.
     * </p>
     *
     * @return il valore totale della mano
     */
    public int getHandValue() {
        return hand.stream()
                .mapToInt(card -> card.getRank().equals("ace") ? 11 : card.getValue())
                .sum();
    }

    /**
     * Getter che restituisce la mano attuale del giocatore.
     *
     * @return La lista di carte nella mano del giocatore.
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Getter che restituisce il nome del giocatore.
     *
     * @return Il nome del giocatore.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter che restituisce il tipo di strategia di gioco
     * adottata dal singolo giocatore.
     *
     * @return La strategia di gioco del giocatore.
     */
    public PlayerStrategy getStrategy() {
        return strategy;
    }

}