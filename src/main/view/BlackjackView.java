package main.view;

import main.controller.BlackjackController;
import main.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * La classe BlackjackView rappresenta la View del gioco.
 * 
 * <p>
 * Questa classe è responsabile della visualizzazione dell'interfaccia
 * utente del gioco e per l'aggiornamento della View in base ai
 * cambiamenti nel Model.
 * </p>
 * 
 * <p>
 * Pattern adottati:
 * - MVC (Model-View-Controller): come parte della View per gestire
 * l'interfaccia grafica della partita.
 * - Observer: per ricevere aggiornamenti dal Model.
 * </p>
 */
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

    private static final int DEALER_PANEL_WIDTH = 400;
    private static final int DEALER_PANEL_HEIGHT = 300;
    private static final int PLAYER_PANEL_HEIGHT = 300;
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 116;
    private static final int CARD_SPACING = 20;

    /**
     * Costruttore che modella una nuova istanza di BlackjackView.
     * Inizializza l'interfaccia utente del gioco, inclusi i JPanel
     * i giocatori, il profilo e i pulsanti di controllo.
     */
    public BlackjackView() {
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920, 1080);
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
        dealerPanel.setBounds(760, 50, DEALER_PANEL_WIDTH, DEALER_PANEL_HEIGHT);
        gamePanel.add(dealerPanel);

        playerPanel = new JPanel(null);
        playerPanel.setOpaque(false);
        playerPanel.setBounds(50, 400, 1820, PLAYER_PANEL_HEIGHT);
        gamePanel.add(playerPanel);

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

        hitButton = new JButton("Pesca Carta");
        standButton = new JButton("Stai");
        restartButton = new JButton("Ricomincia Partita");
        restartButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        buttonPanel.add(restartButton);

        add(gamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(profilePanel, BorderLayout.EAST);
        add(statusLabel, BorderLayout.NORTH);

        restartButton.addActionListener(e -> {
            BlackjackController.getInstance().restartGame();
            restartButton.setEnabled(false);
        });
    }

    /**
     * Metodo che aggiorna la View in base ai cambiamenti nel Model.
     *
     * @param o   L'oggetto Observable che ha segnalato il cambiamento.
     * @param arg Un argomento opzionale passato dall'Observable.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof BlackjackModel) {
            BlackjackModel model = (BlackjackModel) o;
            updateDealerHand(model.getDealer());
            updatePlayerHands(model.getPlayers(), model.getCurrentPlayerIndex());

            if (arg != null && arg.equals("GAME_OVER")) {
                String winnerMessage = model.getWinnerMessage();
                JOptionPane.showMessageDialog(this, winnerMessage, "Partita Terminata",
                        JOptionPane.INFORMATION_MESSAGE);
                restartButton.setEnabled(true);
            }

            updateButtons(model.getCurrentPlayerIndex() == 0 && !model.isBotOrDealerTurn());
        }
    }

    /**
     * Metodo che aggiorna la visualizzazione della mano del Banco.
     *
     * @param dealer Il giocatore che rappresenta il Banco.
     */
    private void updateDealerHand(Player dealer) {
        dealerPanel.removeAll();
        JPanel dealerInfoPanel = createPlayerInfoPanel(dealer, true);
        dealerPanel.add(dealerInfoPanel);
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    /**
     * Metodo che aggiorna la visualizzazione delle mani di tutti i giocatori.
     *
     * @param players            La lista dei giocatori.
     * @param currentPlayerIndex L'indice del giocatore attuale.
     */
    private void updatePlayerHands(List<Player> players, int currentPlayerIndex) {
        playerPanel.removeAll();

        int totalWidth = players.size() * (CARD_WIDTH * 5 + CARD_SPACING * 4);
        int startX = (playerPanel.getWidth() - totalWidth) / 2;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JPanel playerInfoPanel = createPlayerInfoPanel(player, false);

            int x = startX + i * (CARD_WIDTH * 5 + CARD_SPACING * 4);
            playerInfoPanel.setBounds(x, 0, CARD_WIDTH * 5 + CARD_SPACING * 4, PLAYER_PANEL_HEIGHT);

            playerPanel.add(playerInfoPanel);
        }

        playerPanel.revalidate();
        playerPanel.repaint();
    }

    /**
     * Metodo che crea un JPanel per i giocatori e per il Banco.
     * 
     * @param player   Il giocatore o il banco.
     * @param isDealer true se il JPanel è per il banco, false altrimenti.
     * @return Un JPanel contenente le informazioni del giocatore.
     */
    private JPanel createPlayerInfoPanel(Player player, boolean isDealer) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel(isDealer ? "Banco" : player.getName());
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(10));

        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, CARD_SPACING, 0));
        cardsPanel.setOpaque(false);

        for (Card card : player.getHand()) {
            JLabel cardLabel = createCardLabel(card);
            cardsPanel.add(cardLabel);
        }

        panel.add(cardsPanel);

        panel.add(Box.createVerticalStrut(10));
        JLabel valueLabel = new JLabel("Valore: " + player.getHandValue());
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(valueLabel);

        return panel;
    }

    /**
     * Metodo che crea un JLabel per rappresentare una carta.
     * 
     * <p>
     * Questo metodo cerca l'immagine corrispondente alla carta nel percorso
     * specificato, la ridimensiona per poter farla entrare nel JPanel del
     * giocatore.
     * </p>
     * 
     * @param card La carta da rappresentare.
     * @return Un JLabel contenente l'immagine ridimensionata della carta.
     *         Se l'immagine non viene trovata, ritorna un JLabel con una frase di
     *         errore.
     */
    private JLabel createCardLabel(Card card) {
        String imagePath = "src/main/resources/images/cards/" + card.getImageFileName();
        java.io.File imageFile = new java.io.File(imagePath);
        if (imageFile.exists()) {
            ImageIcon originalIcon = new ImageIcon(imagePath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            return new JLabel(scaledIcon);
        } else {
            return new JLabel("Immagine non trovata");
        }
    }

    /**
     * Metodo che aggiorna lo stato dei pulsanti di gioco.
     *
     * @param enable true per abilitare i pulsanti, false per disabilitarli.
     */
    private void updateButtons(boolean enable) {
        hitButton.setEnabled(enable);
        standButton.setEnabled(enable);
    }

    /**
     * Getter che restituisce il pulsante "Pesca Carta".
     *
     * @return Il JButton per l'azione "Pesca Carta".
     */
    public JButton getHitButton() {
        return hitButton;
    }

    /**
     * Getter che restituisce il pulsante "Stai".
     *
     * @return Il JButton per l'azione "Stai".
     */
    public JButton getStandButton() {
        return standButton;
    }
}