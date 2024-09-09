package main.view;

import main.model.UserProfile;
import main.model.BlackjackModel;
import main.controller.BlackjackController;

import javax.swing.*;
import java.awt.*;

/**
 * La classe MainMenuView rappresenta la View del menu principale del gioco.
 * 
 * <p>
 * Questa classe gestisce l'interfaccia utente per il menu principale,
 * permettendo all'utente di iniziare una partita, visualizzare le
 * statistiche o tornare alla creazione di un profilo.
 * </p>
 * 
 * <p>
 * Pattern adottati:
 * - MVC (Model-View-Controller) come parte della View per visualizzare il menu
 * principale.
 */
public class MainMenuView extends JFrame {
    private UserProfile userProfile;

    /**
     * Costruttore che modella una nuova istanza di MainMenuView.
     * 
     * <p>
     * Inizializza l'interfaccia utente del menu principale e
     * carica il profilo utente.
     * </p>
     * 
     * @param userProfile Il profilo utente associato a questa sessione di gioco
     */
    public MainMenuView(UserProfile userProfile) {
        this.userProfile = userProfile;

        // Carica i dati del profilo quando si apre il menu principale
        this.userProfile.loadProfile();

        setTitle("Menu Principale");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1));

        JButton playButton = new JButton("Gioca");
        JButton statsButton = new JButton("Statistiche");
        JButton createProfileButton = new JButton("Crea Profilo");

        add(playButton);
        add(statsButton);
        add(createProfileButton);

        playButton.addActionListener(e -> startGame());
        statsButton.addActionListener(e -> showStatistics());
        createProfileButton.addActionListener(e -> createProfile());

        setVisible(true);
    }

    /**
     * Metodo che avvia una nuova partita.
     * 
     * <p>
     * Crea un nuovo modello, una nuova vista e un nuovo controller per il gioco.
     * </p>
     */
    private void startGame() {
        BlackjackModel model = new BlackjackModel(userProfile);
        BlackjackView view = new BlackjackView();
        BlackjackController controller = new BlackjackController(model, view, this);
        view.setVisible(true);
        this.dispose();
    }

    /**
     * Metodo che mostra la finestra delle statistiche del giocatore.
     * Crea e visualizza una nuova istanza di StatisticsView.
     */
    private void showStatistics() {
        StatisticsView statsView = new StatisticsView(userProfile);
        statsView.setVisible(true);
    }

    /**
     * Metodo che avvia il processo di creazione di un nuovo profilo utente.
     * Crea e visualizza una nuova istanza di ProfileCreationView,
     * quindi chiude la finestra del menu principale.
     */
    private void createProfile() {
        ProfileCreationView profileCreationView = new ProfileCreationView(userProfile);
        profileCreationView.setVisible(true);
        this.dispose();
    }
}