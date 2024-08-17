package main.model;

import java.io.Serializable;
import java.util.Observable;

public abstract class Player extends Observable implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Ruolo {
        GIOCATORE,
        BOT,
        BANCO
    }

    protected Hand mano;
    protected PlayerProfile profilo;
    protected Ruolo ruolo;
    protected Stats stats;

    // Costruttore che accetta un PlayerProfile e un Ruolo
    public Player(PlayerProfile profilo, Ruolo ruolo) {
        this.profilo = profilo;
        this.mano = new Hand();
        this.ruolo = ruolo;
        this.stats = new Stats();
    }

    // Costruttore alternativo che accetta solo un Ruolo
    public Player(Ruolo ruolo) {
        this(null, ruolo); // Chiama il costruttore principale con profilo nullo
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

    public Stats getStats() {
        return stats;
    }

    public abstract int decidiValoreAsso();

    public abstract boolean devePescare();

    public int calcolaPunteggio() {
        return mano.calcolaPunteggio();
    }

    public Hand getMano() {
        return mano;
    }

    public String getNome() {
        return profilo.getNome();
    }

    public void aggiornaStatisticheDopoPartita(boolean vinta) {
        if (vinta) {
            stats.incrementaPartiteVinte();
        } else {
            stats.incrementaPartitePerse();
        }
    }

    @Override
    public String toString() {
        return getNome() + " (" + ruolo + ") con mano: " + mano.toString() + " - Punteggio: " + calcolaPunteggio();
    }
}
