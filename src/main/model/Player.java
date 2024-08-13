package main.model;

import java.util.Scanner;
public class Player {
    private final Hand mano;
    private final String nome;

    public Player(String nome) {
        this.nome = nome;
        this.mano = new Hand();
    }

    public void addCarta(Carta carta) {
        if (carta.getPunteggio() == Carta.Punteggio.ASSO) {
            int valoreAsso = chiediValoreAsso();
            mano.addCartaConValoreAsso(carta, valoreAsso);
        } else {
            mano.addCarta(carta);
        }
    }

    // Metodo provvisorio da sostituire con interfaccia grafica
    private int chiediValoreAsso() {
        Scanner scanner = new Scanner(System.in);
        int valore = 0;
        while (valore != 1 && valore != 11) {
            System.out.println("Hai pescato un asso, vuoi farlo valere 1 o 11?");
            valore = scanner.nextInt();
        }
        return valore;
    }

    public int calcolaPunteggio() {
        return mano.calcolaPunteggio();
    }

    public Hand getMano() {
        return mano;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome + " con mano: " + mano.toString() + " - Punteggio: " + calcolaPunteggio();
    }
}