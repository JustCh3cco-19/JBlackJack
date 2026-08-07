package main.controller;

import main.model.BlackjackModel;
import main.view.BlackjackView;
import main.view.MainMenuView;
import main.util.AudioManager;

import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Represents the {@code BlackjackController} class.
 */
public class BlackjackController {
    private BlackjackModel model;
    private BlackjackView view;
    private AudioManager audioManager;
    private Timer botTimer;

    /**
     * Creates a new {@code BlackjackController} instance.
     * @param model the model
     * @param view the view
     * @param mainMenu the main menu
     */
    public BlackjackController(BlackjackModel model, BlackjackView view, MainMenuView mainMenu) {
        this.model = model;
        this.view = view;
        this.audioManager = AudioManager.getInstance();
        model.addGameListener(view);

        view.getHitButton().addActionListener(e -> hit());
        view.getStandButton().addActionListener(e -> stand());
        view.getRestartButton().addActionListener(e -> restartGame());

        startGame();
    }

    /**
     * Starts the game.
     */
    private void startGame() {
        model.startGame();
        audioManager.play("/audio/game.wav");
        view.setVisible(true);
    }

    /**
     * Handles the hit action.
     */
    private void hit() {
        audioManager.play("/audio/card_flip.wav");
        boolean busted = model.hit();
        if (busted) {
            JOptionPane.showMessageDialog(view, "Hai sballato! Il tuo turno è finito.", "Sballato",
                    JOptionPane.INFORMATION_MESSAGE);
            stand();
        }
    }

    /**
     * Handles the stand action.
     */
    private void stand() {
        audioManager.play("/audio/chip_place.wav");
        model.stand();
        playNextTurn();
    }

    /**
     * Plays the next turn.
     */
    private void playNextTurn() {
        if (model.isGameOver()) {
            model.endRound();
        } else if (!model.isHumanTurn()) {
            playBotTurn();
        }
    }

    /**
     * Plays the bot turn.
     */
    private void playBotTurn() {
        if (botTimer != null && botTimer.isRunning()) return;
        botTimer = new Timer(700, e -> {
            if (model.isGameOver()) {
                botTimer.stop();
                model.endRound();
            } else if (model.botWantsToHit()) {
                if (model.hit()) model.stand();
            } else {
                model.stand();
            }
        });
        botTimer.start();
    }

    /**
     * Restarts the game.
     */
    public void restartGame() {
        if (botTimer != null) botTimer.stop();
        model.resetRound();
    }
}
