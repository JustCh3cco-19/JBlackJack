package main.view;

import main.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class BlackjackView extends JFrame implements Observer {
    private JPanel gamePanel;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JButton hitButton;
    private JButton standButton;
    private JLabel profileLabel;

    public BlackjackView() {
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        gamePanel = new JPanel(new BorderLayout());
        playerPanel = new JPanel(new FlowLayout());
        dealerPanel = new JPanel(new FlowLayout());
        hitButton = new JButton("Rilancia");
        standButton = new JButton("Stai");
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

        // Assumiamo che il giocatore sia il primo (indice 0) e il banco il secondo
        // (indice 1)
        if (players.size() >= 2) {
            Player player = players.get(0);
            Player dealer = players.get(1);

            // Aggiorna la mano del giocatore
            JPanel playerHandPanel = new JPanel();
            playerHandPanel.setLayout(new BoxLayout(playerHandPanel, BoxLayout.Y_AXIS));
            playerHandPanel.add(new JLabel(player.getName()));

            for (Card card : player.getHand()) {
                playerHandPanel.add(new JLabel(card.getRank() + " di " + card.getSuit()));
            }

            playerHandPanel.add(new JLabel("Valore mano: " + player.getHandValue()));
            playerPanel.add(playerHandPanel);

            // Aggiorna la mano del banco
            JPanel dealerHandPanel = new JPanel();
            dealerHandPanel.setLayout(new BoxLayout(dealerHandPanel, BoxLayout.Y_AXIS));
            dealerHandPanel.add(new JLabel(dealer.getName()));

            for (Card card : dealer.getHand()) {
                dealerHandPanel.add(new JLabel(card.getRank() + " di " + card.getSuit()));
            }

            dealerHandPanel.add(new JLabel("Valore mano: " + dealer.getHandValue()));
            dealerPanel.add(dealerHandPanel);
        }

        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    private void updateProfileInfo(UserProfile profile) {
        profileLabel.setText("<html>Giocatore: " + profile.getNickname() +
                "<br>Livello: " + profile.getLevel() +
                "<br>Punti Esperienza: " + profile.getExperience() +
                "<br>Partite Giocate: " + profile.getGamesPlayed() +
                "<br>Partite Vinte: " + profile.getGamesWon() +
                "<br>Partite Perse: " + profile.getGamesLost() + "</html>");
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
