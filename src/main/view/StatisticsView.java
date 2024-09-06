package main.view;

import main.model.UserProfile;

import javax.swing.*;
import java.awt.*;

public class StatisticsView extends JFrame {
    private UserProfile userProfile;

    public StatisticsView(UserProfile userProfile) {
        this.userProfile = userProfile;
        setTitle("Statistiche Giocatore");
        setSize(300, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(new JLabel("Nickname: " + userProfile.getNickname()));
        panel.add(new JLabel("Livello: " + userProfile.getLevel()));
        panel.add(new JLabel("Esperienza: " + userProfile.getExperience()));
        panel.add(new JLabel("Partite Giocate: " + userProfile.getGamesPlayed()));
        panel.add(new JLabel("Partite Vinte: " + userProfile.getGamesWon()));
        panel.add(new JLabel("Partite Perse: " + userProfile.getGamesLost()));

        JButton closeButton = new JButton("Chiudi");
        closeButton.addActionListener(e -> dispose());

        add(panel, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}