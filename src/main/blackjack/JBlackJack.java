package main.blackjack;

import javax.swing.SwingUtilities;
import main.view.ProfileCreationView;

/**
 * Represents the {@code JBlackJack} class.
 */
public class JBlackJack {
    /**
     * Starts the application on the Swing Event Dispatch Thread.
     * @param args the args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProfileCreationView profileCreationView = new ProfileCreationView(null);
            profileCreationView.setVisible(true);
        });
    }
}
