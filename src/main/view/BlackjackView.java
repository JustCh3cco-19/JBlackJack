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
    private JLabel statusLabel;

    public BlackjackView() {
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Pannello di gioco con sfondo verde
        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 128, 0)); // Imposta il colore verde
                g.fillRect(0, 0, getWidth(), getHeight()); // Riempi il pannello con il colore verde
            }
        };
        gamePanel.setLayout(null); // Usa layout null per posizionamento assoluto

        playerPanel = new JPanel();
        playerPanel.setOpaque(false); // Rendi il pannello trasparente
        playerPanel.setBounds(200, 400, 400, 100); // Posiziona il pannello dei giocatori
        playerPanel.setLayout(new FlowLayout());

        dealerPanel = new JPanel();
        dealerPanel.setOpaque(false); // Rendi il pannello trasparente
        dealerPanel.setBounds(200, 50, 400, 100); // Posiziona il pannello del dealer
        dealerPanel.setLayout(new FlowLayout());

        hitButton = new JButton("Rilancia");
        standButton = new JButton("Stai");

        profileLabel = new JLabel();
        statusLabel = new JLabel("Benvenuto nel gioco!");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);

        add(gamePanel, BorderLayout.CENTER);
        gamePanel.add(dealerPanel);
        gamePanel.add(playerPanel);
        add(buttonPanel, BorderLayout.SOUTH);
        add(profileLabel, BorderLayout.EAST);
        add(statusLabel, BorderLayout.NORTH); // Aggiungi l'etichetta dello stato in alto
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BlackjackModel) {
            BlackjackModel model = (BlackjackModel) o;
            updatePlayerHands(model.getPlayers(), model.getDealer());

            if (model.isBotOrDealerTurn()) {
                // Mostra che è il turno di un bot o del dealer e che stanno giocando
                statusLabel.setText("In attesa che il bot/dealer termini il turno...");
            } else {
                // Mostra le informazioni del profilo del giocatore
                updateProfileInfo(model.getUserProfile());
                statusLabel.setText(""); // Rimuovi il messaggio di stato
            }

            // Abilita o disabilita i bottoni in base al turno
            updateButtons(model.getCurrentPlayerIndex() == 0 && !model.isBotOrDealerTurn());
        }
    }

    private void updatePlayerHands(List<Player> players, Player dealer) {
        playerPanel.removeAll();
        dealerPanel.removeAll();

        // Aggiungi il dealer senza avatar, solo con nome e carte
        JPanel dealerPanel = new JPanel();
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));
        dealerPanel.add(new JLabel(dealer.getName()));
        for (Card card : dealer.getHand()) {
            dealerPanel.add(new JLabel(card.getRank() + " di " + card.getSuit()));
        }
        dealerPanel.add(new JLabel("Valore mano: " + dealer.getHandValue()));
        this.dealerPanel.add(dealerPanel); // Aggiungi il pannello del dealer al pannello principale del dealer

        // Aggiungi i giocatori umani e bot senza avatar
        for (Player player : players) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
            playerPanel.add(new JLabel(player.getName()));

            for (Card card : player.getHand()) {
                playerPanel.add(new JLabel(card.getRank() + " di " + card.getSuit()));
            }

            playerPanel.add(new JLabel("Valore mano: " + player.getHandValue()));
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
