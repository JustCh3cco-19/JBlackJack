package main.model;

// Classe in cui gestiamo le carte da usare nella partita
public class Carta {
    // Definisco l'enum necessario per il punteggio assegnato alla carta
    public enum Punteggio {
        ASSO(new int[]{1, 11}), 
        DUE(new int[]{2}), 
        TRE(new int[]{3}), 
        QUATTRO(new int[]{4}), 
        CINQUE(new int[]{5}), 
        SEI(new int[]{6}), 
        SETTE(new int[]{7}), 
        OTTO(new int[]{8}), 
        NOVE(new int[]{9}), 
        DIECI(new int[]{10}), 
        JACK(new int[]{10}), 
        REGINA(new int[]{10}), 
        RE(new int[]{10});

        private final int[] valori;

        Punteggio(int[] valori) {
            this.valori = valori;
        }

        public int[] getValori() {
            return valori;
        }
    }

    private final Punteggio punteggio; // valore della carta

    // Costruttore della classe Carta
    public Carta(Punteggio punteggio) {
        this.punteggio = punteggio;
    }

    // Getter per il punteggio
    public Punteggio getPunteggio() {
        return punteggio;
    }

    // Metodo per ottenere i valori della carta
    public int[] getValore() {
        return punteggio.getValori();
    }

    @Override
    public String toString() {
        return punteggio.toString();
    }
}
