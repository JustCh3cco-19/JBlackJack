package main.view;

import main.controller.BlackjackController;
import main.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

public class BlackjackView extends JFrame implements Observer {
    private JPanel gamePanel;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JPanel profilePanel;
    private JButton hitButton;
    private JButton standButton;
    private JButton restartButton;
    private JLabel profileLabel;
    private JLabel statusLabel;
    private static final int PLAYER_PANEL_WIDTH = 150;
    private static final int PLAYER_PANEL_HEIGHT = 250;
    private static final int PLAYER_PANEL_SPACING = 20;

    private List<Card> previousDealerHand;
    private List<List<Card>> previousPlayerHands;

    public BlackjackView() {
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700); // Aumentata la dimensione della finestra
        setLayout(new BorderLayout());

        Color greenColor = new Color(0, 128, 0);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(greenColor);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gamePanel.setLayout(null);

        dealerPanel = new JPanel();
        dealerPanel.setOpaque(false);
        dealerPanel.setBounds(400, 50, 200, 150);
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));

        playerPanel = new JPanel();
        playerPanel.setOpaque(false);
        playerPanel.setLayout(null); // Usiamo il layout null per posizionare manualmente i pannelli dei giocatori
        playerPanel.setBounds(50, 300, 900, 300);

        profilePanel = new JPanel();
        profilePanel.setBackground(greenColor);
        profilePanel.setLayout(new BorderLayout());

        profileLabel = new JLabel();
        profileLabel.setForeground(Color.WHITE);
        profilePanel.add(profileLabel, BorderLayout.CENTER);

        statusLabel = new JLabel();
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBackground(greenColor);
        statusLabel.setOpaque(true);

        hitButton = new JButton("Rilancia");
        standButton = new JButton("Stai");

        restartButton = new JButton("Ricomincia");
        restartButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton);

        add(gamePanel, BorderLayout.CENTER);
        gamePanel.add(dealerPanel);
        gamePanel.add(playerPanel);
        add(buttonPanel, BorderLayout.SOUTH);
        add(profilePanel, BorderLayout.EAST);
        add(statusLabel, BorderLayout.NORTH);

        restartButton.addActionListener(e -> {
            BlackjackController.getInstance().restartGame();
            restartButton.setEnabled(false);
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BlackjackModel) {
            BlackjackModel model = (BlackjackModel) o;
            updatePlayerHands(model.getPlayers(), model.getDealer(), model.getCurrentPlayerIndex());

            if (model.isBotOrDealerTurn()) {
                statusLabel.setText("In attesa dei bot e del banco...");
            } else {
                updateProfileInfo(model.getUserProfile());
                statusLabel.setText("");
            }

            // Check for the GAME_OVER flag
            if (arg != null && arg.equals("GAME_OVER")) {
                String winnerMessage = model.getWinnerMessage();
                JOptionPane.showMessageDialog(this, winnerMessage, "Partita Terminata",
                        JOptionPane.INFORMATION_MESSAGE);
                restartButton.setEnabled(true);
            }

            updateButtons(model.getCurrentPlayerIndex() == 0 && !model.isBotOrDealerTurn());

            previousDealerHand = model.getDealer().getHand();
            previousPlayerHands = model.getPlayers().stream()
                    .map(Player::getHand)
                    .collect(Collectors.toList());
        }
    }

    private void updatePlayerHands(List<Player> players, Player dealer, int currentPlayerIndex) {
        playerPanel.removeAll();
        dealerPanel.removeAll();

        // Aggiorna il pannello del dealer
        JPanel dealerInfoPanel = createPlayerInfoPanel(dealer, true);
        dealerPanel.add(dealerInfoPanel);

        // Aggiorna i pannelli dei giocatori
        int totalWidth = players.size() * (PLAYER_PANEL_WIDTH + PLAYER_PANEL_SPACING) - PLAYER_PANEL_SPACING;
        int startX = (playerPanel.getWidth() - totalWidth) / 2;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JPanel playerInfoPanel = createPlayerInfoPanel(player, false);

            int x = startX + i * (PLAYER_PANEL_WIDTH + PLAYER_PANEL_SPACING);
            playerInfoPanel.setBounds(x, 0, PLAYER_PANEL_WIDTH, PLAYER_PANEL_HEIGHT);

            playerPanel.add(playerInfoPanel);
        }

        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    private JPanel createPlayerInfoPanel(Player player, boolean isDealer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10));

        for (Card card : player.getHand()) {
            JLabel cardLabel = new JLabel(card.getRank() + " di " + card.getSuit());
            cardLabel.setForeground(Color.WHITE);
            cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(cardLabel);
        }

        if (!isDealer || player.getHand().size() > 1) {
            panel.add(Box.createVerticalStrut(10));
            JLabel valueLabel = new JLabel("Valore: " + player.getHandValue());
            valueLabel.setForeground(Color.WHITE);
            valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(valueLabel);
        }

        return panel;
    }

    private void updateProfileInfo(UserProfile profile) {
        profileLabel.setText("<html>Giocatore: " + profile.getNickname() +
                "<br>Livello: " + profile.getLevel() +
                "<br>Punti Esperienza: " + profile.getExperience() +
                "<br>Partite Giocate: " + profile.getGamesPlayed() +
                "<br>Partite Vinte: " + profile.getGamesWon() +
                "<br>Partite Perse: " + profile.getGamesLost() + "</html>");
    }

    private void updateButtons(boolean enable) {
        hitButton.setEnabled(enable);
        standButton.setEnabled(enable);
    }

    public JButton getHitButton() {
        return hitButton;
    }

    public JButton getStandButton() {
        return standButton;
    }
}