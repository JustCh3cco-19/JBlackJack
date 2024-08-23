package main.view;

import main.model.Carta;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CardView {

    private JFrame frame;
    private JPanel cardPanel;

    public CardView() {
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        cardPanel = new JPanel();
        frame.add(cardPanel);
        frame.setVisible(true);
    }

    public void mostraCarta(Carta carta) {
        // Crea un'etichetta per la carta
        JLabel cardLabel = new JLabel(new ImageIcon("path/to/cards/" + carta.toString() + ".png"));
        cardPanel.add(cardLabel);
        frame.revalidate();
        frame.repaint();
    }

    public void mostraMano(List<Carta> mano) {
        cardPanel.removeAll();
        for (Carta carta : mano) {
            mostraCarta(carta);
        }
    }

    public void mostraPunteggioMano(int punteggio) {
        JOptionPane.showMessageDialog(frame, "Punteggio della mano: " + punteggio);
    }
}
