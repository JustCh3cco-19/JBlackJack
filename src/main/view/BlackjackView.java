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
    private JPanel profilePanel; // Pannello per le statistiche del giocatore
    private JButton hitButton;
    private JButton standButton;
    private JButton restartButton; // Pulsante per ricominciare la partita
    private JLabel profileLabel;
    private JLabel statusLabel;

    private List<Card> previousDealerHand; // Per tracciare lo stato precedente delle mani
    private List<List<Card>> previousPlayerHands;

    public BlackjackView() {
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Modifica della dimensione della finestra
        setLayout(new BorderLayout());

        // Colore verde
        Color greenColor = new Color(0, 128, 0);

        // Pannello di gioco con sfondo verde
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(greenColor);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gamePanel.setLayout(null); // Usa layout null per posizionamento assoluto

        // Pannello per il giocatore
        playerPanel = new JPanel();
        playerPanel.setOpaque(false); // Rendi il pannello trasparente
        playerPanel.setBounds(200, 400, 400, 100); // Posiziona il pannello dei giocatori
        playerPanel.setLayout(new FlowLayout());

        // Pannello per il dealer
        dealerPanel = new JPanel();
        dealerPanel.setOpaque(false); // Rendi il pannello trasparente
        dealerPanel.setBounds(200, 50, 400, 100); // Posiziona il pannello del dealer
        dealerPanel.setLayout(new FlowLayout());

        // Pannello per le statistiche del profilo
        profilePanel = new JPanel();
        profilePanel.setBackground(greenColor); // Imposta lo sfondo verde per il pannello del profilo
        profilePanel.setLayout(new BorderLayout());

        profileLabel = new JLabel();
        profileLabel.setForeground(Color.WHITE); // Imposta il testo bianco per contrastare il verde
        profilePanel.add(profileLabel, BorderLayout.CENTER);

        statusLabel = new JLabel("Benvenuto nel gioco!");
        statusLabel.setForeground(Color.WHITE); // Imposta il testo bianco per contrastare il verde
        statusLabel.setBackground(greenColor); // Imposta lo sfondo verde per lo stato
        statusLabel.setOpaque(true); // Rendi visibile il colore di sfondo

        hitButton = new JButton("Rilancia");
        standButton = new JButton("Stai");

        restartButton = new JButton("Ricomincia");
        restartButton.setEnabled(false); // Disabilitato fino a fine partita

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton); // Aggiungi il pulsante "Ricomincia"

        add(gamePanel, BorderLayout.CENTER);
        gamePanel.add(dealerPanel);
        gamePanel.add(playerPanel);
        add(buttonPanel, BorderLayout.SOUTH);
        add(profilePanel, BorderLayout.EAST); // Aggiungi il pannello delle statistiche
        add(statusLabel, BorderLayout.NORTH); // Aggiungi l'etichetta dello stato in alto

        // Listener per il pulsante "Ricomincia"
        restartButton.addActionListener(e -> {
            // Chiama un metodo del controller per ricominciare la partita
            BlackjackController.getInstance().restartGame();
            restartButton.setEnabled(false); // Disabilita il pulsante dopo il riavvio
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BlackjackModel) {
            BlackjackModel model = (BlackjackModel) o;
            updatePlayerHands(model.getPlayers(), model.getDealer(), model.getCurrentPlayerIndex());

            if (model.isBotOrDealerTurn()) {
                statusLabel.setText("In attesa che il bot o banco decida...");
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

        // Dealer's cards
        JPanel dealerInfoPanel = new JPanel();
        dealerInfoPanel.setLayout(new BoxLayout(dealerInfoPanel, BoxLayout.Y_AXIS));
        dealerInfoPanel.setOpaque(false);
        JLabel dealerNameLabel = new JLabel(dealer.getName());
        dealerNameLabel.setForeground(Color.WHITE);
        dealerInfoPanel.add(dealerNameLabel);

        if (currentPlayerIndex >= players.size()) {
            // Show all dealer's cards if the game is over
            for (Card card : dealer.getHand()) {
                JLabel cardLabel = new JLabel(card.getRank() + " di " + card.getSuit());
                cardLabel.setForeground(Color.WHITE);
                dealerInfoPanel.add(cardLabel);
            }
        } else {
            // Show only the first card of the dealer
            if (!dealer.getHand().isEmpty()) {
                JLabel visibleCardLabel = new JLabel(
                        dealer.getHand().get(0).getRank() + " di " + dealer.getHand().get(0).getSuit());
                visibleCardLabel.setForeground(Color.WHITE);
                dealerInfoPanel.add(visibleCardLabel);

                JLabel hiddenCardLabel = new JLabel("Carta coperta");
                hiddenCardLabel.setForeground(Color.WHITE);
                dealerInfoPanel.add(hiddenCardLabel);
            }
        }

        this.dealerPanel.add(dealerInfoPanel);

        // Players' cards
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JPanel playerInfoPanel = new JPanel();
            playerInfoPanel.setLayout(new BoxLayout(playerInfoPanel, BoxLayout.Y_AXIS));
            playerInfoPanel.setOpaque(false);
            JLabel playerNameLabel = new JLabel(player.getName());
            playerNameLabel.setForeground(Color.WHITE);
            playerInfoPanel.add(playerNameLabel);

            // Always show all cards for all players
            for (Card card : player.getHand()) {
                JLabel cardLabel = new JLabel(card.getRank() + " di " + card.getSuit());
                cardLabel.setForeground(Color.WHITE);
                playerInfoPanel.add(cardLabel);
            }

            // Show hand value for all players
            JLabel playerHandValueLabel = new JLabel("Valore mano: " + player.getHandValue());
            playerHandValueLabel.setForeground(Color.WHITE);
            playerInfoPanel.add(playerHandValueLabel);

            this.playerPanel.add(playerInfoPanel);
        }

        this.playerPanel.revalidate();
        this.playerPanel.repaint();
        this.dealerPanel.revalidate();
        this.dealerPanel.repaint();
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