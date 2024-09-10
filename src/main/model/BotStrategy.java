package main.model;

import java.util.List;

/**
 * La classe BotStrategy implementa PlayerStrategy e definisce la strategia di
 * gioco per i bot.
 * 
 * <p>
 * Pattern adottati:
 * - Strategy: permette di cambiare la strategia di gioco dei bot senza
 * modificare PlayerStrategy.
 * </p>
 */
public class BotStrategy implements PlayerStrategy {

    /**
     * Metodo che determina se il bot vuole pescare un'altra carta.
     * 
     * <p>
     * Questa implementazione specifica del metodo wantsToHit definisce
     * la strategia concreta del bot: continua a pescare finché il valore
     * della mano è inferiore a 17.
     * </p>
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