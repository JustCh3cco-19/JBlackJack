package main.model;

public class Banco extends Player {

    public Banco() {
        super("Banco", Ruolo.BANCO);
    }

    @Override
    protected int decidiValoreAsso() {
        // Il banco decide se l'asso vale 11 o 1 in base al punteggio corrente
        return calcolaPunteggio() + 11 <= 21 ? 11 : 1;
    }

    @Override
    public boolean devePescare() {
        // Logica per il banco: il banco continua a pescare se il punteggio è inferiore a 17
        return calcolaPunteggio() < 17;
    }

    @Override
    public String toString() {
        return "Banco " + super.toString();
    }
}