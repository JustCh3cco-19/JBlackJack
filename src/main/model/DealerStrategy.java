package main.model;

import java.util.List;

/**
 * La classe DealerStrategy implementa la strategia per il Banco.
 * 
 * <p>
 * Pattern adottati:
 * - Strategy: permette di cambiare la strategia di gioco del banco senza
 * modificare PlayerStrategy.
 * </p>
 */
public class DealerStrategy implements PlayerStrategy {
    /**
     * Metodo che determina se il banco vuole chiedere un'altra carta
     * basandosi sul valore della sua mano.
     * 
     * <p>
     * Questa implementazione segue la regola standard del Blackjack per il
     * mazziere:
     * pescare se il valore totale della mano è inferiore a 17.
     * </p>
     * 
     * @param hand Le carte nella mano del mazziere.
     * @return true se il mazziere vuole un'altra carta, false altrimenti.
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        int handValue = hand.stream().mapToInt(Card::getValue).sum();
        return handValue < 17;
    }
}