package main.model;

public class Stats {
    private int partiteGiocate;
    private int partiteVinte;
    private int partitePerse;

    // Costruttore
    public Stats() {
        this.partiteGiocate = 0;
        this.partiteVinte = 0;
        this.partitePerse = 0;
    }

    // Metodi getter
    public int getPartiteGiocate() {
        return partiteGiocate;
    }

    public int getPartiteVinte() {
        return partiteVinte;
    }

    public int getPartitePerse() {
        return partitePerse;
    }

    // Metodo per incrementare le partite giocate
    public void incrementaPartiteGiocate() {
        partiteGiocate++;
    }

    // Metodo per incrementare le partite vinte
    public void incrementaPartiteVinte() {
        partiteVinte++;
        incrementaPartiteGiocate();
    }

    // Metodo per incrementare le partite perse
    public void incrementaPartitePerse() {
        partitePerse++;
        incrementaPartiteGiocate();
    }

    // Metodo per calcolare la percentuale di vittorie
    public double calcolaPercentualeVittorie() {
        return partiteGiocate > 0 ? (double) partiteVinte / partiteGiocate * 100 : 0.0;
    }

    // Metodo per resettare le statistiche
    public void resetStats() {
        this.partiteGiocate = 0;
        this.partiteVinte = 0;
        this.partitePerse = 0;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "partiteGiocate=" + partiteGiocate +
                ", partiteVinte=" + partiteVinte +
                ", partitePerse=" + partitePerse +
                ", percentualeVittorie=" + calcolaPercentualeVittorie() +
                "%}";
    }
}
