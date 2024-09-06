package main.view;

import main.model.UserProfile;
import main.model.BlackjackModel;
import main.controller.BlackjackController;

import javax.swing.*;
import java.awt.*;

public class MainMenuView extends JFrame {
    private UserProfile userProfile;

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

    private void startGame() {
        BlackjackModel model = new BlackjackModel(userProfile);
        BlackjackView view = new BlackjackView();
        BlackjackController controller = new BlackjackController(model, view, this);
        view.setVisible(true);
        this.dispose(); // Chiudi il menu principale quando inizia il gioco
    }

    private void showStatistics() {
        StatisticsView statsView = new StatisticsView(userProfile);
        statsView.setVisible(true);
    }

    private void createProfile() {
        ProfileCreationView profileCreationView = new ProfileCreationView(userProfile); // Pass UserProfile
        profileCreationView.setVisible(true);
        this.dispose(); // Close the main menu when going to profile creation
    }

}