package main.model;

import java.util.List;

/**
 * La classe HumanPlayerStrategy implementa la strategia di gioco per il
 * giocatore umano.
 * 
 * <p>
 * Pattern adottati:
 * - Strategy: permette di cambiare la strategia di gioco del giocatore umano
 * senza modificare PlayerStrategy.
 * </p>
 */
public class HumanPlayerStrategy implements PlayerStrategy {

    /**
     * Metodo che determina se il giocatore umano vuole chiedere un'altra carta.
     * 
     * <p>
     * Questa implementazione attuale restituisce sempre false, indicando che
     * il giocatore umano non vuole mai chiedere un'altra carta.
     * È un segnaposto che serve per interagire con l'input del giocatore umano in
     * base alle scelte che vuole compiere nel gioco.
     * </p>
     *
     * @param hand La lista di carte nella mano del giocatore.
     * @return false, indicando che il giocatore non vuole un'altra carta.
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        return false;
    }
}