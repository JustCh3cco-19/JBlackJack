package main.model;

import java.util.Scanner;

public class Human extends Player {
    private transient Scanner scanner;

    public Human(PlayerProfile profilo) {
        super(profilo, Ruolo.GIOCATORE);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public int decidiValoreAsso() {
        int valore = 0;
        while (valore != 1 && valore != 11) {
            System.out.println("Hai pescato un asso, vuoi farlo valere 1 o 11?");
            if (scanner.hasNextInt()) {
                valore = scanner.nextInt();
            } else {
                System.out.println("Inserimento non valido, riprova.");
                scanner.next(); // Consuma l'input non valido
            }
        }
        return valore;
    }

    @Override
    public boolean devePescare() {
        System.out.println("Il tuo punteggio attuale è: " + calcolaPunteggio());
        System.out.println("Vuoi pescare un'altra carta? (s/n)");

        String risposta = scanner.next().trim().toLowerCase();

        while (!risposta.equals("s") && !risposta.equals("n")) {
            System.out.println("Risposta non valida. Vuoi pescare un'altra carta? (s/n)");
            risposta = scanner.next().trim().toLowerCase();
        }

        return risposta.equals("s");
    }

    @Override
    public String toString() {
        return "Umano " + super.toString();
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
            scanner = null;
        }
    }
}
