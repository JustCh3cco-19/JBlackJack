package main.model;

import java.util.ArrayList;
import java.util.List;
public class Hand {
    private final List<Carta> carte;
    private final List<Integer> valoriSpecifici;

    public Hand()
    {
        this.carte = new ArrayList<>();
        this.valoriSpecifici = new ArrayList<>();
    }

    // Metodo per aggiungere una carta normale alla mano
    public void addCarta(Carta carta) {
        carte.add(carta);
        valoriSpecifici.add(carta.getValore()[0]);
    }

    public void addCartaConValoreAsso(Carta carta, int valore) {
        carte.add(carta);
        valoriSpecifici.add(valore);
    }

    // Metodo per calcolare il punteggio totale della mano
    public int calcolaPunteggio() {
        int totale = 0;

        for (int valore : valoriSpecifici) {
            totale += valore;
        }
        return totale;
    }

    // Restituisci le carte nella Mano
    public List<Carta> getCarte() {
        return carte;
    }
    
    @Override
    public String toString() {
        return carte.toString();
    }
}