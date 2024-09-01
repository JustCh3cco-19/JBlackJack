package main.blackjack;

import javax.swing.SwingUtilities;
import main.controller.BlackjackController;
import main.model.BlackjackModel;
import main.view.BlackjackView;

public class JBlackJack {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackModel model = new BlackjackModel("FrancescoZompanti",
                    "/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/images/charles_leclerc.png");
            BlackjackView view = new BlackjackView();
            BlackjackController controller = new BlackjackController(model, view);
            view.setVisible(true);
        });
    }
}