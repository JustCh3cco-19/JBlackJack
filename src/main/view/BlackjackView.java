package main.view;

import main.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;
import java.util.List;

public class BlackjackView extends JFrame implements Observer {
    private JPanel gamePanel;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JButton hitButton;
    private JButton standButton;
    private JLabel profileLabel;

    public BlackjackView() {
        setTitle("Blackjack Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        gamePanel = new JPanel(new BorderLayout());
        playerPanel = new JPanel(new FlowLayout());
        dealerPanel = new JPanel(new FlowLayout());
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        profileLabel = new JLabel();

        gamePanel.add(dealerPanel, BorderLayout.NORTH);
        gamePanel.add(playerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(profileLabel, BorderLayout.EAST);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BlackjackModel) {
            BlackjackModel model = (BlackjackModel) o;
            updatePlayerHands(model.getPlayers());
            updateProfileInfo(model.getUserProfile());
            updateButtons(model.getCurrentPlayerIndex() == 0);
        }
    }

    private void updatePlayerHands(List<Player> players) {
        playerPanel.removeAll();
        dealerPanel.removeAll();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.add(new JLabel(player.getName()));

            for (Card card : player.getHand()) {
                panel.add(new JLabel(card.getRank() + " of " + card.getSuit()));
            }

            panel.add(new JLabel("Hand Value: " + player.getHandValue()));

            if (i == 0) {
                playerPanel.add(panel);
            } else {
                dealerPanel.add(panel);
            }
        }

        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    private void updateProfileInfo(UserProfile profile) {
        profileLabel.setText("<html>Player: " + profile.getNickname() +
                "<br>Level: " + profile.getLevel() +
                "<br>Experience: " + profile.getExperience() +
                "<br>Games Played: " + profile.getGamesPlayed() +
                "<br>Games Won: " + profile.getGamesWon() +
                "<br>Games Lost: " + profile.getGamesLost() + "</html>");
    }

    private void updateButtons(boolean playerTurn) {
        hitButton.setEnabled(playerTurn);
        standButton.setEnabled(playerTurn);
    }

    public JButton getHitButton() {
        return hitButton;
    }

    public JButton getStandButton() {
        return standButton;
    }
}
