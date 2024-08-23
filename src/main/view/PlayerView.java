package main.view;

import main.model.Player;
import main.model.PlayerProfile;
import main.model.Stats;

import java.util.Observer;
import java.util.Observable;

public class PlayerView implements Observer {

    private Player player;
    private PlayerProfile profile;

    public PlayerView(Player player, PlayerProfile profile) {
        this.player = player;
        this.profile = profile;
        player.addObserver(this); // Registrazione dell'observer
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable == player) {
            aggiornaVista(); // Aggiorna la vista quando il giocatore cambia
        }
    }

    public void mostraProfilo() {
        System.out.println("Profilo Giocatore:");
        System.out.println("Nickname: " + profile.getNickname());
        System.out.println("Avatar: " + profile.getAvatar());
    }

    public void aggiornaProfilo(String nickname, String avatar) {
        profile.setNickname(nickname);
        profile.setAvatar(avatar);
        System.out.println("Profilo aggiornato!");
        mostraProfilo();
    }

    public void aggiornaVista() {
        // Aggiorna la mano del giocatore nella vista
        aggiornaMano();

        // Aggiorna le statistiche del giocatore nella vista
        Stats stats = player.getStats();
        if (stats != null) {
            aggiornaStatistiche(stats);
        }
    }

    public void aggiornaMano() {
        // Aggiorna la vista con le informazioni sulla mano del giocatore
        System.out.println("Mano aggiornata per il giocatore " + player.getNome() + ": " + player.getMano());
        // Se stai usando una GUI, aggiorna gli elementi grafici corrispondenti
    }

    public void aggiornaStatistiche(Stats stats) {
        // Aggiorna la vista con le statistiche del giocatore
        System.out.println("Statistiche aggiornate: " + stats.toString());
        // Se stai usando una GUI, aggiorna gli elementi grafici corrispondenti
    }

    // Metodo per aggiornare la vista (in caso di integrazione con una GUI)
    public void aggiornaVistaGUI() {
        // implementare l'aggiornamento della GUI se stai utilizzando
        // un'interfaccia grafica
        // Ad esempio, aggiornando le label o immagini con il nuovo nickname, avatar,
        // mano e statistiche
    }
}
