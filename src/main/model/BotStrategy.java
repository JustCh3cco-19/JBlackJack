package main.model;

import java.util.List;

/**
 * La classe BotStrategy implementa la strategia di gioco per i bot.
 * 
 * Pattern adottati:
 * - Strategy: permette di cambiare la strategia di gioco dei bot senza
 * modificare PlayerStrategy
 */
public class BotStrategy implements PlayerStrategy {

    /**
     * Determina se il bot vuole pescare un'altra carta.
     * 
     * Questa implementazione specifica del metodo wantsToHit definisce
     * la strategia concreta del bot: continua a pescare finché il valore
     * della mano è inferiore a 17.
     *
     * @param hand La lista di carte attualmente nella mano del bot.
     * @return true se il bot vuole pescare un'altra carta, false altrimenti.
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        int handValue = hand.stream().mapToInt(Card::getValue).sum();
        return handValue < 17;
    }
}