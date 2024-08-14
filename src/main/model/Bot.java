package main.model;

import java.util.Random;

public class Bot extends Player {

    private Random random = new Random();

    public Bot() {
        super(NomiBot.getNomeRandom(), Ruolo.BOT);
    }

    @Override
    public int decidiValoreAsso() {
        return random.nextBoolean() ? 11 : 1;
    }

    @Override
    public boolean devePescare() {
        // Logica per i bot: il bot continua a pescare se il punteggio è inferiore a 17
        return calcolaPunteggio() < 17;
    }

    @Override
    public String toString() {
        return "Bot " + super.toString();
    }
}