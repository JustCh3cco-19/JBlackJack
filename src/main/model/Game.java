package main.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Human giocatore;
    private List<Bot> bots;
    private Deck deck;
    private Player banco;

    public Game(Human giocatore, List<Bot> bots) {
        this.giocatore = giocatore;
        this.bots = bots != null ? bots : new ArrayList<>();
        this.deck = new Deck();
        this.banco = new Banco();
    }

    // Metodo per ottenere il mazzo
    public Deck getDeck() {
        return deck;
    }

    // Metodo per iniziare il gioco
    public void startGioco() {
        // Distribuisci le carte ai giocatori
        setupInitialHands();

        // Gestione turni del giocatore umano
        managePlayerTurn(giocatore);

        // Gestisci turno del banco
        managePlayerTurn(banco);

        // Gestisci il turno del bot
        for (Bot bot : bots) {
            managePlayerTurn(bot);
        }

        // Stabilisci il vincitore della mano
        determinaVincitore();
    }

    // Metodo per configurare le mani iniziali dei giocatori
    private void setupInitialHands() {
        // Distribuisci due carte ad ogni giocatore umano, ai bot e al banco
        giocatore.addCarta(deck.pescaCarta());
        giocatore.addCarta(deck.pescaCarta());

        for (Bot bot : bots) {
            bot.addCarta(deck.pescaCarta());
            bot.addCarta(deck.pescaCarta());
        }

        banco.addCarta(deck.pescaCarta());
        banco.addCarta(deck.pescaCarta());
    }

    private void managePlayerTurn(Player player) {
        while (player.devePescare()) {
            player.addCarta(deck.pescaCarta());
            System.out.println(player.toString());
        }
    }

    private void determinaVincitore() {
        int punteggioBanco = banco.calcolaPunteggio();
        System.out.println("Punteggio del banco: " + punteggioBanco);

        int punteggioGiocatore = giocatore.calcolaPunteggio();
        System.out.println("Punteggio di " + giocatore.getNome() + ": " + punteggioGiocatore);

        // Verifica se il giocatore umano ha sballato
        if (punteggioGiocatore > 21) {
            System.out.println(giocatore.getNome() + " ha sballato!");
        } else if (punteggioGiocatore > 21 || punteggioGiocatore > punteggioBanco) {
            System.out.println(giocatore.getNome() + " ha vinto!");
        } else if (punteggioGiocatore == punteggioBanco) {
            System.out.println(giocatore.getNome() + " ed il banco hanno pareggiato");
        } else {
            System.out.println(giocatore.getNome() + " ha perso!");
        }

        // Gestisci i risultati per i bot
        for (Bot bot : bots) {
            int punteggioBot = bot.calcolaPunteggio();
            System.out.println(bot.getNome() + " punteggio: " + punteggioBot);

            // Verifica se il bot ha sballato
            if (punteggioBot > 21) {
                System.out.println(bot.getNome() + " ha sballato!");
            } else if (punteggioBot > 21 || punteggioBot > punteggioBanco) {
                System.out.println(bot.getNome() + " ha vinto!");
            } else if (punteggioBot == punteggioBanco) {
                System.out.println(bot.getNome() + " ed il banco hanno pareggiato");
            } else {
                System.out.println(bot.getNome() + " ha perso!");
            }
        }
    }
}