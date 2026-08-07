package main.view;

import main.model.UserProfile;
import javax.swing.*;
import java.awt.*;

/**
 * Represents the {@code StatisticsView} class.
 */
public class StatisticsView extends JFrame {
    private static final long serialVersionUID = 1L;
    private UserProfile userProfile;

    /**
     * Creates a new {@code StatisticsView} instance.
     * @param userProfile the user profile
     */
    public StatisticsView(UserProfile userProfile) {
        this.userProfile = userProfile;
        initializeFrame();
        setupComponents();
        setVisible(true);
    }

    /**
     * Initializes the frame.
     */
    private void initializeFrame() {
        setTitle("Statistiche Giocatore");
        setSize(300, 300);
        setLocationRelativeTo(null);
    }

    /**
     * Configures the components.
     */
    private void setupComponents() {
        JPanel panel = createStatisticsPanel();
        JButton closeButton = createCloseButton();

        add(panel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    /**
     * Creates the statistics panel.
     * @return the operation result
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
     * Creates the close button.
     * @return the operation result
     */
    private JButton createCloseButton() {
        JButton closeButton = new JButton("Chiudi");
        closeButton.addActionListener(e -> dispose());
        return closeButton;
    }
}
