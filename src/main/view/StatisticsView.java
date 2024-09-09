package main.view;

import main.model.UserProfile;
import javax.swing.*;
import java.awt.*;

/**
 * La classe StatisticsView rappresenta la View per visualizzare
 * le statistiche del profilo utente del giocatore.
 * 
 * <p>
 * Pattern adottati:
 * - MVC (Model-View-Controller): come parte della View per gestire il pannello
 * delle statistiche del giocatore;
 * - Composite: per organizzare i componenti dell'interfaccia.
 * </p>
 */
public class StatisticsView extends JFrame {
    private UserProfile userProfile;

    /**
     * Costruttore che modella la classe StatisticsView.
     * Inizializza la finestra e visualizza le statistiche del giocatore.
     *
     * @param userProfile Il profilo utente contenente le statistiche da
     *                    visualizzare.
     */
    public StatisticsView(UserProfile userProfile) {
        this.userProfile = userProfile;
        initializeFrame();
        setupComponents();
        setVisible(true);
    }

    /**
     * Metodo che inizializza le proprietà base della finestra.
     */
    private void initializeFrame() {
        setTitle("Statistiche Giocatore");
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    /**
     * Metodo che configura e aggiunge i componenti alla finestra.
     */
    private void setupComponents() {
        JPanel panel = createStatisticsPanel();
        JButton closeButton = createCloseButton();

        add(panel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    /**
     * Metodo che crea il JPanel contenente le statistiche del giocatore.
     *
     * @return JPanel contenente le etichette con le statistiche.
     */
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(new JLabel("Nickname: " + userProfile.getNickname()));
        panel.add(new JLabel("Livello: " + userProfile.getLevel()));
        panel.add(new JLabel("Esperienza: " + userProfile.getExperience()));
        panel.add(new JLabel("Partite Giocate: " + userProfile.getGamesPlayed()));
        panel.add(new JLabel("Partite Vinte: " + userProfile.getGamesWon()));
        panel.add(new JLabel("Partite Perse: " + userProfile.getGamesLost()));
        return panel;
    }

    /**
     * Metodo che crea il pulsante di chiusura della finestra.
     *
     * @return JButton configurato per chiudere la finestra.
     */
    private JButton createCloseButton() {
        JButton closeButton = new JButton("Chiudi");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }
}