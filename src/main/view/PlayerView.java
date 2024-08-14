package main.view;

import main.model.Player;
import main.model.Stats;

import java.util.Observer;
import java.util.Observable;

public class PlayerView implements Observer {

    private Player player;

    public PlayerView(Player player) {
        this.player = player;
        player.addObserver(this); // Registrazione dell'observer
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable == player) {
            // Aggiorna la vista in base ai cambiamenti nel Player
            System.out.println("Player status updated: " + player);
        }
    }

    // Metodo per aggiornare la mano del giocatore nella vista
    public void aggiornaMano(Player player) {
        // Aggiorna la vista con le informazioni sulla mano del giocatore
        System.out.println("Mano aggiornata per il giocatore " + player.getNome() + ": " + player.getMano());
        // Se stai usando una GUI, aggiorna gli elementi grafici corrispondenti
    }

    // Metodo per aggiornare le statistiche nella vista
    public void aggiornaStatistiche(Stats stats) {
        // Aggiorna la vista con le statistiche del giocatore
        System.out.println("Statistiche aggiornate: " + stats.toString());
        // Se stai usando una GUI, aggiorna gli elementi grafici corrispondenti
    }
}
