package main.controller;

import main.model.BlackjackModel;
import main.view.BlackjackView;
import main.view.MainMenuView;
import main.util.AudioManager;

import javax.swing.JOptionPane;

/**
 * La classe BlackjackController implementa il pattern MVC
 * (Model-View-Controller) gestendo la logica di controllo del gioco.
 * Fa interagire il modello del gioco ({@link BlackjackModel}), la
 * vista ({@link BlackjackView}),
 * e la riproduzione di file audio ({@link AudioManager}).
 * 
 * <p>
 * Questa classe implementa anche il pattern Singleton per garantire
 * un'unica istanza del controller.
 * Utilizza il pattern Observer attraverso l'interazione
 * con il Model e la View.
 * </p>
 * 
 * @see BlackjackModel
 * @see BlackjackView
 * @see AudioManager
 * @see MainMenuView
 */
public class BlackjackController {
    private BlackjackModel model;
    private BlackjackView view;
    private AudioManager audioManager;
    private static BlackjackController instance;

    /**
     * Costruttore della classe BlackjackController.
     * 
     * <p>
     * Inizializza il Model e la View, aggiunge gli Action Listener per i pulsanti
     * "Pesca Carta" e "Stai", e avvia il gioco.
     * Inoltre, gestisce l'audio utilizzando l'{@link AudioManager}.
     * </p>
     * 
     * @param model    il modello del gioco Blackjack
     * @param view     la vista associata al gioco Blackjack
     * @param mainMenu il menu principale dell'applicazione
     */
    public BlackjackController(BlackjackModel model, BlackjackView view, MainMenuView mainMenu) {
        this.model = model;
        this.view = view;
        this.audioManager = AudioManager.getInstance();
        instance = this;

        model.addObserver(view);

        // Aggiunge gli Action Listener per i pulsanti "Pesca Carta" e "Stai"
        view.getHitButton().addActionListener(e -> hit());
        view.getStandButton().addActionListener(e -> stand());

        startGame();
    }

    /**
     * Metodo per avviare una nuova partita.
     * 
     * <p>
     * Chiama il metodo per iniziare il gioco tramite il Model, riproduce
     * il file audio di sottofondo per la partita e rende visibile la View.
     * </p>
     */
    private void startGame() {
        model.startGame();
        audioManager.play("src/main/resources/audio/game.wav");
        view.setVisible(true);
    }

    /**
     * Metodo che gestisce l'azione "Pesca Carta" del giocatore.
     * 
     * <p>
     * Riproduce il suono di girare una carta e verifica se il giocatore ha
     * sballato.
     * Se il giocatore sballa, termina il suo turno chiamando il metodo
     * {@link #stand()}.
     * </p>
     */
    private void hit() {
        audioManager.play("src/main/resources/audio/card_flip.wav");
        boolean busted = model.hit();
        if (busted) {
            JOptionPane.showMessageDialog(view, "Hai sballato! Il tuo turno è finito.", "Sballato",
                    JOptionPane.INFORMATION_MESSAGE);
            stand();
        }
    }

    /**
     * Metodo che gestisce l'azione "Stai" del giocatore.
     * 
     * <p>
     * Riproduce il suono della puntata di chip e termina il turno.
     * </p>
     */
    private void stand() {
        audioManager.play("src/main/resources/audio/chip_place.wav");
        model.stand();
        playNextTurn();
    }

    /**
     * Metodo che gestisce il turno successivo.
     * 
     * <p>
     * Se il gioco è finito, chiama il metodo {@link BlackjackModel#endRound()}.
     * Se non è il turno del giocatore umano, avvia il turno del bot.
     * </p>
     */
    private void playNextTurn() {
        if (model.isGameOver()) {
            model.endRound();
        } else if (!model.isHumanTurn()) {
            playBotTurn();
        }
    }

    /**
     * Metodo che esegue il turno del bot in un thread separato.
     * Ho adottato questa soluzione poiché eseguendo tutto in un unico
     * thread la partita terminava non appena il giocatore umano finisse
     * il suo turno.
     * Con questa soluzione riesco a far sì che la partita si svolga
     * nell'ordine corretto,
     * ossia che il primo turno sta al giocatore umano,
     * poi ai bot ed infine al Banco.
     * 
     * <p>
     * Il bot attende 2 secondi tra le azioni per simulare un possibile ragionamento
     * sul pescare la carta o stare. Quando il turno del bot è terminato,
     * il controllo torna al giocatore umano.
     * </p>
     */
    private void playBotTurn() {
        new Thread(() -> {
            while (!model.isGameOver() && !model.isHumanTurn()) {
                try {
                    Thread.sleep(2000);
                    if (model.botWantsToHit()) {
                        model.hit();
                    } else {
                        model.stand();
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

    /**
     * Metodo che riavvia la partita, resettando il round corrente.
     * 
     * <p>
     * Questo metodo chiama il reset del round sul Model, permettendo di
     * iniziare una nuova mano.
     * </p>
     */
    public void restartGame() {
        model.resetRound();
    }

    /**
     * Getter che restituisce l'istanza Singleton di {@link BlackjackController}.
     * 
     * @return l'istanza attuale del Controller.
     */
    public static BlackjackController getInstance() {
        return instance;
    }
}