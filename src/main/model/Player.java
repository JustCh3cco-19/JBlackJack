package main.model;

import java.util.Observable;

public abstract class Player extends Observable {

    public enum Ruolo {
        GIOCATORE,
        BOT,
        BANCO
    }

    protected Hand mano;
    protected String nome;
    protected Ruolo ruolo;
    protected Stats stats;

    public Player(String nome, Ruolo ruolo) {
        this.nome = nome;
        this.mano = new Hand();
        this.ruolo = ruolo;
        this.stats = new Stats();
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void addCarta(Carta carta) {
        if (carta.getPunteggio() == Carta.Punteggio.ASSO) {
            int valoreAsso = decidiValoreAsso();
            mano.addCartaConValoreAsso(carta, valoreAsso);
        } else {
            mano.addCarta(carta);
        }
        setChanged();
        notifyObservers();
    }

    // Metodo per accedere alle statistiche del giocatore
    public Stats getStats() {
        return stats;
    }

    // Definiamo il metodo come astratto in Player
    public abstract int decidiValoreAsso();

    // Decidere se il giocatore deve pescare o meno
    public abstract boolean devePescare();

    public int calcolaPunteggio() {
        return mano.calcolaPunteggio();
    }

    public Hand getMano() {
        return mano;
    }

    public String getNome() {
        return nome;
    }

    // Aggiorna le statistiche dopo una partita
    public void aggiornaStatisticheDopoPartita(boolean vinta) {
        if (vinta) {
            stats.incrementaPartiteVinte();
        } else {
            stats.incrementaPartitePerse();
        }
    }

    @Override
    public String toString() {
        return nome + " (" + ruolo + ") con mano: " + mano.toString() + " - Punteggio: " + calcolaPunteggio();
    }
}
