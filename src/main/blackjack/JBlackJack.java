package main.blackjack;

import javax.swing.SwingUtilities;
import java.awt.event.*;
import main.view.ProfileCreationView;
import main.view.MainMenuView;
import main.model.UserProfile;

public class JBlackJack {
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