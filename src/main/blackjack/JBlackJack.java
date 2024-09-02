package main.blackjack;

import javax.swing.SwingUtilities;
import javax.swing.event.*;
import java.awt.event.*;

import main.controller.BlackjackController;
import main.model.BlackjackModel;
import main.view.BlackjackView;
import main.view.ProfileCreationView;
import main.model.UserProfile;

public class JBlackJack {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfileCreationView profileCreationView = new ProfileCreationView(null);
            profileCreationView.setVisible(true);

            // Aggiungi un WindowListener per sapere quando la finestra viene chiusa
            profileCreationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    // Ottieni il profilo utente creato
                    UserProfile userProfile = profileCreationView.getUserProfile();

                    if (userProfile != null) {
                        BlackjackModel model = new BlackjackModel(
                                userProfile.getNickname(),
                                userProfile.getAvatarPath());
                        BlackjackView view = new BlackjackView();
                        BlackjackController controller = new BlackjackController(model, view);
                        view.setVisible(true);
                    }
                }
            });
        });
    }
}