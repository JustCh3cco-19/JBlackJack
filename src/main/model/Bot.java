package main.model;

import java.util.Random;

public class Bot extends Player {

    private Random random = new Random();

    public Bot() {
        super(NomiBot.getNomeRandom());
    }

    @Override
    public void addCarta(Carta carta) {
        if (carta.getPunteggio() == Carta.Punteggio.ASSO) {
            int valoreAsso = decidiValoreAsso();
            getMano().addCartaConValoreAsso(carta, valoreAsso);
        } else {
            super.addCarta(carta);
        }
    }
    
    // Decidiamo se l'asso vale 1 o 11 basato sul punteggio attuale
    private int decidiValoreAsso() {
        return random.nextBoolean() ? 11 : 1;
        // Se aggiungere 11 non fa superare 21, aggiungi 11 altrimenti aggiungi 1
    }

    public boolean devePescare() {
        return calcolaPunteggio() < 17;
    }

    @Override
    public String toString() {
        return "Bot " + getNome() + " con mano: " + getMano().toString() + " - Punteggio: " + calcolaPunteggio();
    }
}
