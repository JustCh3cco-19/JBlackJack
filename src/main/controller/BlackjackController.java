package main.controller;

import main.model.BlackjackModel;
import main.view.BlackjackView;
import main.util.AudioManager;

public class BlackjackController {
    private BlackjackModel model;
    private BlackjackView view;
    private AudioManager audioManager;

    public BlackjackController(BlackjackModel model, BlackjackView view) {
        this.model = model;
        this.view = view;
        this.audioManager = AudioManager.getInstance();

        model.addObserver(view);

        view.getHitButton().addActionListener(e -> hit());
        view.getStandButton().addActionListener(e -> stand());

        model.startGame();
    }

    private void hit() {
        audioManager.play("resources/audio/card_flip.wav");
        model.hit();
    }

    private void stand() {
        audioManager.play("resources/audio/chip_place.wav");
    }
}