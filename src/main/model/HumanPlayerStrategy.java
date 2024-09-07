package main.model;

import java.util.List;

/**
 * La classe HumanPlayerStrategy rappresenta la strategia di gioco per un
 * giocatore umano.
 * 
 * <p>
 * Questa classe implementa l'interfaccia {@link PlayerStrategy} e definisce
 * il comportamento specifico per un giocatore umano in un gioco di carte.
 * </p>
 * 
 * <p>
 * Pattern adottati:
 * - Strategy: serve a gestire la strategia del giocatore umano in base
 * alle carte che ha nella sua mano.
 * </p>
 */
public class HumanPlayerStrategy implements PlayerStrategy {

    /**
     * Determina se il giocatore umano vuole chiedere un'altra carta.
     * 
     * <p>
     * Questa implementazione attuale restituisce sempre false, indicando che
     * il giocatore umano non vuole mai chiedere un'altra carta.
     * È un segnaposto che serve per interagire con l'input del giocatore umano in
     * base alle scelte che vuole compiere nel gioco.
     * </p>
     *
     * @param hand La lista di carte nella mano del giocatore
     * @return false, indicando che il giocatore non vuole un'altra carta
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        return false;
    }
}