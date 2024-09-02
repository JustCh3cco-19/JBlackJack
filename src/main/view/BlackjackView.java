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

            // Se la partita è terminata, mostra il vincitore
            if (model.isGameOver()) {
                String winnerMessage = model.getWinnerMessage();
                JOptionPane.showMessageDialog(this, winnerMessage, "Partita Terminata",
                        JOptionPane.INFORMATION_MESSAGE);
                restartButton.setEnabled(true); // Abilita il pulsante "Ricomincia"
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

        // Aggiungi il dealer con una carta scoperta e una coperta all'inizio
        JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
        dealerPanel.setOpaque(false); // Rendi il pannello trasparente
        JLabel dealerNameLabel = new JLabel(dealer.getName());
        dealerNameLabel.setForeground(Color.WHITE); // Testo bianco per il nome del dealer
        dealerPanel.add(dealerNameLabel);

        if (previousDealerHand == null) {
            // Prima mano, mostra una carta scoperta e una coperta
            if (!dealer.getHand().isEmpty()) {
                JLabel visibleCardLabel = new JLabel(
                        dealer.getHand().get(0).getRank() + " di " + dealer.getHand().get(0).getSuit());
                visibleCardLabel.setForeground(Color.WHITE); // Testo bianco per la carta scoperta
                dealerPanel.add(visibleCardLabel);

                JLabel hiddenCardLabel = new JLabel("Carta coperta");
                hiddenCardLabel.setForeground(Color.WHITE); // Testo bianco per la carta coperta
                dealerPanel.add(hiddenCardLabel);
            }
        } else {
            // Mostra tutte le carte del dealer dopo che i bot hanno giocato
            for (Card card : dealer.getHand()) {
                JLabel cardLabel = new JLabel(card.getRank() + " di " + card.getSuit());
                cardLabel.setForeground(Color.WHITE); // Testo bianco per le carte del dealer
                dealerPanel.add(cardLabel);
            }
        }

        this.dealerPanel.add(dealerPanel); // Aggiungi il pannello del dealer al pannello principale del dealer

        // Aggiungi i giocatori umani e bot senza avatar
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            playerPanel.setOpaque(false); // Rendi il pannello trasparente
            JLabel playerNameLabel = new JLabel(player.getName());
            playerNameLabel.setForeground(Color.WHITE); // Testo bianco per il nome del giocatore
            playerPanel.add(playerNameLabel);

            if (i == currentPlayerIndex) {
                // Mostra tutte le carte e la somma delle carte per il giocatore umano
                for (Card card : player.getHand()) {
                    JLabel cardLabel = new JLabel(card.getRank() + " di " + card.getSuit());
                    cardLabel.setForeground(Color.WHITE); // Testo bianco per le carte del giocatore
                    playerPanel.add(cardLabel);
                }

                // Mostra la somma delle carte del giocatore umano
                JLabel playerHandValueLabel = new JLabel("Valore mano: " + player.getHandValue());
                playerHandValueLabel.setForeground(Color.WHITE); // Testo bianco per il valore della mano
                playerPanel.add(playerHandValueLabel);
            } else {
                // Mostra solo le nuove carte pescate dai bot
                List<Card> previousHand = previousPlayerHands != null && i < previousPlayerHands.size()
                        ? previousPlayerHands.get(i)
                        : null;

                if (previousHand != null && player.getHand().size() > previousHand.size()) {
                    for (int j = previousHand.size(); j < player.getHand().size(); j++) {
                        Card newCard = player.getHand().get(j);
                        JLabel cardLabel = new JLabel(
                                "Nuova carta pescata: " + newCard.getRank() + " di " + newCard.getSuit());
                        cardLabel.setForeground(Color.WHITE); // Testo bianco per le carte pescate
                        playerPanel.add(cardLabel);
                    }
                } else {
                    // Mostra solo il numero di carte in mano del bot
                    playerPanel.add(new JLabel("Carte in mano: " + player.getHand().size()));
                }
            }

            this.playerPanel.add(playerPanel); // Aggiungi il pannello del giocatore al pannello principale dei
                                               // giocatori
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