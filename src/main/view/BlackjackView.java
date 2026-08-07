package main.view;

import main.model.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Represents the {@code BlackjackView} class.
 */
public class BlackjackView extends JFrame implements GameListener {
    private static final long serialVersionUID = 1L;
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
     * Creates a new {@code BlackjackView} instance.
     */
    public BlackjackView() {
        setTitle("JBlackJack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(900, 650));
        setSize(1200, 800);
        setLocationRelativeTo(null);
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
        gamePanel.setLayout(new BorderLayout(10, 30));

        dealerPanel = new JPanel();
        dealerPanel.setOpaque(false);
        dealerPanel.setPreferredSize(new Dimension(DEALER_PANEL_WIDTH, DEALER_PANEL_HEIGHT));
        gamePanel.add(dealerPanel, BorderLayout.NORTH);

        playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, CARD_SPACING, 0));
        playerPanel.setOpaque(false);
        playerPanel.setPreferredSize(new Dimension(900, PLAYER_PANEL_HEIGHT));
        JScrollPane playersScrollPane = new JScrollPane(playerPanel);
        playersScrollPane.setOpaque(false);
        playersScrollPane.getViewport().setOpaque(false);
        playersScrollPane.setBorder(null);
        gamePanel.add(playersScrollPane, BorderLayout.CENTER);

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

    }

    /**
     * Performs the {@code gameChanged} operation.
     * @param model the model
     * @param event the event
     */
    @Override
    public void gameChanged(BlackjackModel model, GameEvent event) {
            updateDealerHand(model.getDealer(), event != GameEvent.GAME_OVER);
            updatePlayerHands(model.getPlayers(), model.getCurrentPlayerIndex());

            if (event == GameEvent.GAME_OVER) {
                String winnerMessage = model.getWinnerMessage();
                JOptionPane.showMessageDialog(this, winnerMessage, "Partita Terminata",
                        JOptionPane.INFORMATION_MESSAGE);
                restartButton.setEnabled(true);
            }

            updateButtons(model.getCurrentPlayerIndex() == 0 && !model.isBotOrDealerTurn());
    }

    /**
     * Updates the dealer hand.
     * @param dealer the dealer
     * @param hideHoleCard the hide hole card
     */
    private void updateDealerHand(Player dealer, boolean hideHoleCard) {
        dealerPanel.removeAll();
        dealerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel dealerInfoPanel = createPlayerInfoPanel(dealer, true, hideHoleCard);
        dealerPanel.add(dealerInfoPanel);
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    /**
     * Updates the player hands.
     * @param players the players
     * @param currentPlayerIndex the current player index
     */
    private void updatePlayerHands(List<Player> players, int currentPlayerIndex) {
        playerPanel.removeAll();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            JPanel playerInfoPanel = createPlayerInfoPanel(player, false, false);

            playerInfoPanel.setPreferredSize(new Dimension(CARD_WIDTH * 5 + CARD_SPACING * 4,
                    PLAYER_PANEL_HEIGHT));

            playerPanel.add(playerInfoPanel);
        }

        playerPanel.revalidate();
        playerPanel.repaint();
    }

    /**
     * Creates the player info panel.
     * @param player the player
     * @param isDealer the is dealer
     * @param hideHoleCard the hide hole card
     * @return the operation result
     */
    private JPanel createPlayerInfoPanel(Player player, boolean isDealer, boolean hideHoleCard) {
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

        for (int i = 0; i < player.getHand().size(); i++) {
            Card card = player.getHand().get(i);
            JLabel cardLabel = hideHoleCard && i == 1 ? new JLabel("Carta coperta") : createCardLabel(card);
            cardLabel.setForeground(Color.WHITE);
            cardsPanel.add(cardLabel);
        }

        panel.add(cardsPanel);

        panel.add(Box.createVerticalStrut(10));
        JLabel valueLabel = new JLabel(hideHoleCard ? "Valore: ?" : "Valore: " + player.getHandValue());
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(valueLabel);

        return panel;
    }

    /**
     * Creates the card label.
     * @param card the card
     * @return the operation result
     */
    private JLabel createCardLabel(Card card) {
        java.net.URL imageUrl = getClass().getResource("/images/cards/" + card.getImageFileName());
        if (imageUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            return new JLabel(scaledIcon);
        } else {
            return new JLabel("Immagine non trovata");
        }
    }

    /**
     * Updates the buttons.
     * @param enable the enable
     */
    private void updateButtons(boolean enable) {
        hitButton.setEnabled(enable);
        standButton.setEnabled(enable);
    }

    /**
     * Returns the hit button.
     * @return the hit button
     */
    public JButton getHitButton() {
        return hitButton;
    }

    /**
     * Returns the stand button.
     * @return the stand button
     */
    public JButton getStandButton() {
        return standButton;
    }

    /**
     * Returns the button used to restart the round.
     *
     * @return the restart button
     */
    public JButton getRestartButton() {
        return restartButton;
    }
}
