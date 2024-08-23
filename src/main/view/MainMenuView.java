package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    private JButton btnNewGame;
    private JButton btnLoadGame;
    private JButton btnProfile;
    private JButton btnSettings;
    private JButton btnExit;

    public MainMenuView() {
        setTitle("Blackjack - Menu Principale");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Creazione dei pulsanti
        btnNewGame = new JButton("Nuova Partita");
        btnLoadGame = new JButton("Carica Partita");
        btnProfile = new JButton("Profilo Giocatore");
        btnSettings = new JButton("Impostazioni");
        btnExit = new JButton("Esci");

        // Aggiunta dei pulsanti al frame
        add(btnNewGame);
        add(btnLoadGame);
        add(btnProfile);
        add(btnSettings);
        add(btnExit);

        setLocationRelativeTo(null); // Centra la finestra
        setVisible(true);
    }

    // Metodi per aggiungere listener ai pulsanti
    public void addNewGameListener(ActionListener listener) {
        btnNewGame.addActionListener(listener);
    }

    public void addLoadGameListener(ActionListener listener) {
        btnLoadGame.addActionListener(listener);
    }

    public void addProfileListener(ActionListener listener) {
        btnProfile.addActionListener(listener);
    }

    public void addSettingsListener(ActionListener listener) {
        btnSettings.addActionListener(listener);
    }

    public void addExitListener(ActionListener listener) {
        btnExit.addActionListener(listener);
    }
}
