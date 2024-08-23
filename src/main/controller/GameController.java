package main.controller;

import main.model.*;
import main.util.AudioManager;
import main.view.PlayerView;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Game game;
    private Human giocatore;
    private List<Bot> bots;
    private AudioManager audioManager;
    private PlayerManager playerManager;
    private PlayerView playerView;

    public GameController() {
        playerManager = new PlayerManager();
        PlayerProfile profilo = playerManager.caricaProfilo();
        if (profilo == null) {
            System.out.println("Devi creare un Profilo prima di giocare");
            return; // Esci se non c'è un profilo disponibile
        }
        setupGame(profilo);
    }

    // Metodo per configurare e iniziare il gioco
    private void setupGame(PlayerProfile profilo) {
        // Inizializzazione dell'AudioManager
        audioManager = AudioManager.getInstance();

        // Creazione del giocatore umano
        giocatore = new Human(profilo);

        // Creazione della PlayerView per il giocatore umano
        playerView = new PlayerView(giocatore, profilo); // Passa sia giocatore che profilo

        // Creazione dei bot
        bots = new ArrayList<>();
        bots.add(new Bot());
        bots.add(new Bot()); // Aggiungi altri bot se necessario

        // Creazione del gioco con il giocatore umano e i bot
        game = new Game(giocatore, bots);

        // Avvio del gioco
        game.startGioco();

        // Riproduzione di un suono di avvio gioco
        audioManager.play("start_game.wav");

        // Chiudi il Scanner del giocatore umano una volta terminato il gioco
        giocatore.closeScanner();
    }

    // Metodo per avviare il gioco e gestire il flusso
    private void startGame() {
        // Distribuzione delle carte iniziali
        game.setupInitialHands();
        playerView.aggiornaVista();

        // Gestione turni del giocatore umano
        managePlayerTurn(giocatore);

        // Gestione turni dei bot
        for (Bot bot : bots) {
            managePlayerTurn(bot);
        }

        // Gestione del turno del banco
        managePlayerTurn(game.getBanco());

        // Determina il vincitore alla fine del gioco
        game.determinaVincitore();
    }

    // Metodo per gestire i turni dei giocatori
    private void managePlayerTurn(Player player) {
        while (player.devePescare()) {
            player.addCarta(game.getDeck().pescaCarta());
            playerView.aggiornaVista(); // Aggiorna la vista ad ogni pescata
            // Se il giocatore è il banco o un bot, possiamo fare una pausa per simulare il
            // pensiero
            if (player instanceof Bot || player instanceof Banco) {
                try {
                    Thread.sleep(1000); // Pausa di 1 secondo tra le mosse
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        // Avvia il gioco
        new GameController();
    }
}
