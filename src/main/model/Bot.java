package main.model;

import java.util.Random;

public class Bot extends Player {

    private Random random = new Random();

    public Bot() {
        // Passiamo il PlayerProfile e il Ruolo al costruttore di Player
        super(new PlayerProfile(NomiBot.getNomeRandom(), Player.Ruolo.BOT), null);
    }

    @Override
    public int decidiValoreAsso() {
        // Decide casualmente se l'asso vale 1 o 11
        return random.nextBoolean() ? 11 : 1;
    }

    @Override
    public boolean devePescare() {
        // Il bot continua a pescare se il punteggio è inferiore a 17
        return calcolaPunteggio() < 17;
    }

    @Override
    public String toString() {
        return "Bot " + super.toString();
    }
}
