package main.model;

import java.util.List;

/**
 * La classe PlayerStrategy definisce la strategia di gioco per il giocatore.
 * 
 * <p>
 * Pattern adottati:
 * - Strategy: permette di modellare una strategia di gioco,
 * creando un'interfaccia generica per essere poi estesa nei singoli casi.
 * </p>
 */
public interface PlayerStrategy {

    /**
     * L'interfaccia determina se il giocatore vuole chiedere
     * un'altra carta basandosi sulla sua mano attuale.
     * 
     * <p>
     * Questo metodo implementa la logica decisionale della strategia di gioco.
     * Diverse implementazioni di questa interfaccia possono fornire diverse
     * strategie
     * per decidere quando chiedere un'altra carta.
     * </p>
     *
     * @param hand Le carte attualmente nella mano del giocatore
     * @return true se il giocatore vuole un'altra carta, false altrimenti
     */
    boolean wantsToHit(List<Card> hand);
}