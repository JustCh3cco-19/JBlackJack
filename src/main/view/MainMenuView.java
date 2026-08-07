package main.view;

import main.model.UserProfile;
import main.model.BlackjackModel;
import main.controller.BlackjackController;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the {@code MainMenuView} class.
 */
public class MainMenuView extends JFrame {
    private static final long serialVersionUID = 1L;
    private UserProfile userProfile;

    /**
     * Creates a new {@code MainMenuView} instance.
     * @param userProfile the user profile
     */
    public MainMenuView(UserProfile userProfile) {
        this.userProfile = userProfile;

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
     * Starts the game.
     */
    private void startGame() {
        BlackjackModel model = new BlackjackModel(userProfile);
        BlackjackView view = new BlackjackView();
        new BlackjackController(model, view, this);
        this.dispose();
    }

    /**
     * Performs the {@code showStatistics} operation.
     */
    private void showStatistics() {
        StatisticsView statsView = new StatisticsView(userProfile);
        statsView.setVisible(true);
    }

    /**
     * Creates the profile.
     */
    private void createProfile() {
        ProfileCreationView profileCreationView = new ProfileCreationView(userProfile);
        profileCreationView.setVisible(true);
        this.dispose();
    }
}
