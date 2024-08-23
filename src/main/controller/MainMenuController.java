package main.controller;

import main.view.MainMenuView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController {
    private MainMenuView menuView;

    public MainMenuController(MainMenuView menuView) {
        this.menuView = menuView;

        // Aggiunge i listener ai pulsanti
        menuView.addNewGameListener(new NewGameListener());
        menuView.addLoadGameListener(new LoadGameListener());
        menuView.addProfileListener(new ProfileListener());
        menuView.addSettingsListener(new SettingsListener());
        menuView.addExitListener(new ExitListener());
    }

    class NewGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logica per iniziare una nuova partita
            System.out.println("Nuova partita iniziata!");
            // Potresti creare un nuovo GameController e iniziare il gioco
        }
    }

    class LoadGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logica per caricare una partita salvata
            System.out.println("Carica partita selezionata!");
            // Potresti implementare una schermata di selezione del salvataggio
        }
    }

    class ProfileListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logica per visualizzare o modificare il profilo
            System.out.println("Profilo selezionato!");
            // Potresti aprire una schermata di gestione del profilo
        }
    }

    class SettingsListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logica per accedere alle impostazioni
            System.out.println("Impostazioni selezionate!");
            // Potresti aprire una schermata di impostazioni
        }
    }

    class ExitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logica per uscire dal gioco
            System.out.println("Esci dal gioco selezionato!");
            System.exit(0);
        }
    }
}
