package main.model;

import java.util.Scanner;

public class Human extends Player {

    private Scanner scanner;

    public Human() {
        super("Umano", Ruolo.GIOCATORE);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int decidiValoreAsso() {
        int valore = 0;
        while (valore != 1 && valore != 11) {
            System.out.println("Hai pescato un asso, vuoi farlo valere 1 o 11?");
            valore = scanner.nextInt();
        }
        return valore;
    }

    @Override
    public boolean devePescare() {
        // Mostra il punteggio attuale e chiede al giocatore se vuole continuare a
        // pescare
        System.out.println("Il tuo punteggio attuale è: " + calcolaPunteggio());
        System.out.println("Vuoi pescare un'altra carta? (s/n)");

        String risposta = scanner.next().toLowerCase();

        // Se il giocatore risponde 's', deve pescare
        return risposta.equals("s");
    }

    @Override
    public String toString() {
        return "Umano " + super.toString();
    }

    // Chiude il scanner quando il gioco finisce per evitare resource leaks
    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}