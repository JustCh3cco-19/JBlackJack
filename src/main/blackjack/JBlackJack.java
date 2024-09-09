package main.blackjack;

import javax.swing.SwingUtilities;
import java.awt.event.*;
import main.view.ProfileCreationView;
import main.view.MainMenuView;
import main.model.UserProfile;

/**
 * La classe JBlackJack rappresenta il main del gioco.
 * Inizializza la finestra per la creazione del profilo e procede al menu
 * principale una volta creato un profilo utente.
 * 
 * <p>
 * La GUI viene inizializzata sul thread di gestione degli eventi (Event
 * Dispatch Thread, EDT)
 * utilizzando {@link SwingUtilities#invokeLater(Runnable)} per garantire una
 * corretta gestione dei thread stessi.
 * Viene mostrata prima la finestra di creazione del profilo e, una volta
 * chiusa,
 * l'applicazione passa al menu principale se viene creato un
 * {@link UserProfile} corretto.
 * Dal menu principale si può accedere al gioco, visualizzare le statistiche
 * del giocatore o tornare alla creazione del profilo.
 * </p>
 * 
 * @see ProfileCreationView
 * @see MainMenuView
 * @see UserProfile
 */
public class JBlackJack {
    /**
     * Questo metodo avvia l'applicazione.
     * 
     * <p>
     * Questo metodo esegue l'applicazione sull'Event Dispatch Thread, iniziando
     * con la {@link ProfileCreationView}. Una volta creato il profilo utente,
     * l'applicazione passa alla {@link MainMenuView}.
     * </p>
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfileCreationView profileCreationView = new ProfileCreationView(null);
            profileCreationView.setVisible(true);

            profileCreationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    UserProfile userProfile = profileCreationView.getUserProfile();
                    if (userProfile != null) {
                        MainMenuView menuView = new MainMenuView(userProfile);
                        menuView.setVisible(true);
                    }
                }
            });
        });
    }
}