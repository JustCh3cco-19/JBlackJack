package main.controller;

import main.model.Bot;
import main.model.Game;
import main.model.Human;
import main.model.PlayerProfile;
import main.util.AudioManager;

import java.io.FileInputStream;
// import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Game game;
    private Human giocatore;
    private AudioManager audioManager;

    private static final String PROFILE_FILE_PATH = "PlayerProfile.ser";

    public GameController() {
        PlayerProfile profilo = caricaPlayerProfile();
        if (profilo == null) {
            System.out.println("Devi creare un Profilo prima di giocare");
            return; // Esci se non c'è un profilo disponibile
        }
        setupGame(profilo);
    }

    // Carichiamo il profilo
    private PlayerProfile caricaPlayerProfile() {
        PlayerProfile profilo = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(PROFILE_FILE_PATH))) {
            profilo = (PlayerProfile) input.readObject();
            System.out.println("Profilo caricato correttamente");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nessun profilo trovato, per giocare devi crearne uno");
        }
        return profilo;
    }

    // Metodo per salvare il profilo del giocatore su disco
    /*
     * private void savePlayerProfile(PlayerProfile profile) {
     * try (ObjectOutputStream oos = new ObjectOutputStream(new
     * FileOutputStream(PROFILE_FILE_PATH))) {
     * oos.writeObject(profile);
     * System.out.println("Profilo salvato correttamente.");
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * }
     */

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
