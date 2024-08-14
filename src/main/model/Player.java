package main.model;

public abstract class Player {

    public enum Ruolo {
        GIOCATORE,
        BOT,
        BANCO
    }

    protected Hand mano;
    protected String nome;
    protected Ruolo ruolo;

    public Player(String nome, Ruolo ruolo) {
        this.nome = nome;
        this.mano = new Hand();
        this.ruolo = ruolo;
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
    }

    // Definiamo il metodo come astratto in Player
    protected abstract int decidiValoreAsso();
    
    // Decidere se il giocatore deve pescare o meno
    protected abstract boolean devePescare();

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
        return nome + " (" + ruolo + ") con mano: " + mano.toString() + " - Punteggio: " + calcolaPunteggio();
    }
}
