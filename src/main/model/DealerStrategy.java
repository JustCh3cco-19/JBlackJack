package main.model;

import java.util.List;

/**
 * La classe DealerStrategy implementa la strategia che adotterà il Banco.
 * 
 * Pattern adottati:
 * - Strategy: definisce la strategia adottata dal banco.
 * 
 */
public class DealerStrategy implements PlayerStrategy {
    /**
     * Questo metodo determina se il mazziere vuole chiedere un'altra carta
     * basandosi sul valore della sua mano.
     * Questa implementazione segue la regola standard del Blackjack per il
     * mazziere:
     * chiedere un'altra carta se il valore totale della mano è inferiore a 17.
     *
     * @param hand La lista di carte nella mano del mazziere
     * @return true se il mazziere vuole un'altra carta, false altrimenti
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        int handValue = hand.stream().mapToInt(Card::getValue).sum();
        return handValue < 17;
    }
}