package main.controller;

import main.model.BlackjackModel;
import main.view.BlackjackView;
import main.util.AudioManager;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class BlackjackController {
    private BlackjackModel model;
    private BlackjackView view;
    private AudioManager audioManager;
    private static BlackjackController instance;

    public BlackjackController(BlackjackModel model, BlackjackView view) {
        this.model = model;
        this.view = view;
        this.audioManager = AudioManager.getInstance();
        instance = this;

        model.addObserver(view);

        view.getHitButton().addActionListener(e -> hit());
        view.getStandButton().addActionListener(e -> stand());

        startGame();
    }

    public static BlackjackController getInstance() {
        return instance;
    }

    private void hit() {
        audioManager.play("/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/audio/card_flip.wav");
        boolean busted = model.hit();
        showPlayerDecision("Giocatore", "Rilancia", model.getCurrentPlayerHandValue());
        if (busted) {
            JOptionPane.showMessageDialog(view, "Hai sballato! Il tuo turno è finito.", "Sballato",
                    JOptionPane.INFORMATION_MESSAGE);
            stand();
        }
    }

    private void stand() {
        audioManager.play("/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/audio/chip_place.wav");
        showPlayerDecision("Giocatore", "Stai", model.getCurrentPlayerHandValue());
        model.stand();
        playNextTurn();
    }

    private void playNextTurn() {
        if (model.isGameOver()) {
            model.endRound();
        } else if (!model.isHumanTurn()) {
            playBotTurn();
        }
    }

    private void playBotTurn() {
        new Thread(() -> {
            while (!model.isGameOver() && !model.isHumanTurn()) {
                try {
                    Thread.sleep(1000); // Pausa di 1 secondo per simulare il "pensiero" del bot
                    String botName = model.getCurrentPlayerName();
                    int botHandValue = model.getCurrentPlayerHandValue();
                    if (model.botWantsToHit()) {
                        model.hit();
                        showPlayerDecision(botName, "Rilancia", botHandValue);
                    } else {
                        model.stand();
                        showPlayerDecision(botName, "Stai", botHandValue);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (model.isGameOver()) {
                model.endRound();
            }
        }).start();
    }

    private void showPlayerDecision(String playerName, String decision, int handValue) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(view,
                    playerName + " " + decision + "\nValore mano: " + handValue,
                    "Decisione del giocatore",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void startGame() {
        model.startGame();
    }

    public void restartGame() {
        model.resetRound();
    }
}