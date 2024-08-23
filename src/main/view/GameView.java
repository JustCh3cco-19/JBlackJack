package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import main.model.Carta;

public class GameView extends JFrame {
    private JLabel lblPunteggioGiocatore;
    private JLabel lblPunteggioBanco;
    private JButton btnPescare;
    private JButton btnStop;
    private JPanel panelCarteGiocatore;
    private JPanel panelCarteBanco;

    public GameView() {
        setTitle("Blackjack");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Pannello per il punteggio del giocatore
        JPanel pannelloGiocatore = new JPanel();
        lblPunteggioGiocatore = new JLabel("Punteggio giocatore: 0");
        pannelloGiocatore.add(lblPunteggioGiocatore);

        // Pannello per il punteggio del banco
        JPanel pannelloBanco = new JPanel();
        lblPunteggioBanco = new JLabel("Punteggio banco: 0");
        pannelloBanco.add(lblPunteggioBanco);

        // Pannello per i bottoni di azione
        JPanel pannelloAzioni = new JPanel();
        btnPescare = new JButton("Pescare");
        btnStop = new JButton("Stop");

        pannelloAzioni.add(btnPescare);
        pannelloAzioni.add(btnStop);

        // Pannello per le carte del giocatore
        panelCarteGiocatore = new JPanel();
        panelCarteGiocatore.setLayout(new FlowLayout());
        panelCarteGiocatore.setBorder(BorderFactory.createTitledBorder("Carte Giocatore"));

        // Pannello per le carte del banco
        panelCarteBanco = new JPanel();
        panelCarteBanco.setLayout(new FlowLayout());
        panelCarteBanco.setBorder(BorderFactory.createTitledBorder("Carte Banco"));

        // Aggiungi i pannelli alla finestra principale
        add(pannelloGiocatore, BorderLayout.NORTH);
        add(panelCarteGiocatore, BorderLayout.CENTER);
        add(panelCarteBanco, BorderLayout.SOUTH);
        add(pannelloAzioni, BorderLayout.EAST);

        setVisible(true);
    }

    public void setPunteggioGiocatore(int punteggio) {
        lblPunteggioGiocatore.setText("Punteggio giocatore: " + punteggio);
    }

    public void setPunteggioBanco(int punteggio) {
        lblPunteggioBanco.setText("Punteggio banco: " + punteggio);
    }

    public void mostraCarteGiocatore(List<Carta> carte) {
        panelCarteGiocatore.removeAll();
        for (Carta carta : carte) {
            panelCarteGiocatore.add(new JLabel(new ImageIcon("path/to/cards/" + carta.toString() + ".png")));
        }
        panelCarteGiocatore.revalidate();
        panelCarteGiocatore.repaint();
    }

    public void mostraCarteBanco(List<Carta> carte) {
        panelCarteBanco.removeAll();
        for (Carta carta : carte) {
            panelCarteBanco.add(new JLabel(new ImageIcon("path/to/cards/" + carta.toString() + ".png")));
        }
        panelCarteBanco.revalidate();
        panelCarteBanco.repaint();
    }

    public void addPescareListener(ActionListener listener) {
        btnPescare.addActionListener(listener);
    }

    public void addStopListener(ActionListener listener) {
        btnStop.addActionListener(listener);
    }
}
