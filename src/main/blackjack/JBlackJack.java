package main.blackjack;

// Main.java
import javax.swing.SwingUtilities;
import main.controller.BlackjackController;
import main.model.BlackjackModel;
import main.view.BlackjackView;

public class JBlackJack {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackModel model = new BlackjackModel("Player1", "path/to/avatar.png");
            BlackjackView view = new BlackjackView();
            BlackjackController controller = new BlackjackController(model, view);
            view.setVisible(true);
        });
    }
}