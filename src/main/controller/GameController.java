package main.controller;

import main.model.Bot;
import main.model.Game;
import main.model.Human;
import main.model.PlayerManager;
import main.model.PlayerProfile;
import main.util.AudioManager;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Game game;
    private Human giocatore;
    private AudioManager audioManager;
    private PlayerManager playerManager;

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

        // Creazione dei bot
        List<Bot> bots = new ArrayList<>();
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

    public static void main(String[] args) {
        // Avvia il gioco
        new GameController();
    }
}
