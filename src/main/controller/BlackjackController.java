package main.controller;

import main.model.BlackjackModel;
import main.view.BlackjackView;
import main.util.AudioManager;

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
        model.hit();
    }

    private void stand() {
        audioManager.play("/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/audio/chip_place.wav");
        model.stand();
    }

    private void startGame() {
        model.startGame();
    }

    public void restartGame() {
        model.resetRound();
    }
}
