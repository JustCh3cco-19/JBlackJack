package main.model;

import java.util.Random;

public class Bot extends Player {

    private Random random = new Random();

    public Bot() {
        // Crea un PlayerProfile con un nome casuale dall'enum NomiBot e il ruolo BOT
        super(new PlayerProfile(NomiBot.getNomeRandom(), null), Ruolo.BOT);
    }

    @Override
    public int decidiValoreAsso() {
        return random.nextBoolean() ? 11 : 1;
    }

    @Override
    public boolean devePescare() {
        return calcolaPunteggio() < 17;
    }

    @Override
    public String toString() {
        return "Bot " + super.toString();
    }
}
