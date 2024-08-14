package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Carta> carte;

    public Deck() {
        carte = new ArrayList<>();
        initializeDeck(); // Initialize the deck with cards
        mescola(); // Shuffle the deck after initializing
    }

    // Metodo per inizializzare il mazzo con le carte
    private void initializeDeck() {
        // Assuming Carta.Punteggio represents different values and each value should appear multiple times
        for (Carta.Punteggio punteggio : Carta.Punteggio.values()) {
            // For a standard deck, each value would be repeated 4 times (one for each suit)
            for (int i = 0; i < 4; i++) {
                carte.add(new Carta(punteggio));
            }
        }
    }

    // Metodo per mescolare le carte
    public void mescola() {
        Collections.shuffle(carte);
    }

    public Carta pescaCarta() {
        if (carte.isEmpty()) {
            throw new IllegalStateException("Il mazzo è vuoto");
        }
        return carte.remove(0);
    }

    // restituisce il numero di carte nel mazzo
    public int carteRimaste() {
        return carte.size();
    }
}
