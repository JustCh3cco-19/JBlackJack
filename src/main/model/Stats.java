package main.model;

import java.io.Serializable;

public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;

    private int partiteGiocate;
    private int partiteVinte;
    private int partitePerse;
    private int livello;

    public Stats() {
        this.partiteGiocate = 0;
        this.partiteVinte = 0;
        this.partitePerse = 0;
        this.livello = 1;
    }

    // Metodi per aggiornare le statistiche
    public void incrementaPartiteGiocate() {
        this.partiteGiocate++;
    }

    public void incrementaPartiteVinte() {
        this.partiteVinte++;
        incrementaLivello();
    }

    public void incrementaPartitePerse() {
        this.partitePerse++;
    }

    private void incrementaLivello() {
        this.livello++;
    }

    @Override
    public String toString() {
        return "Partite Giocate: " + partiteGiocate +
                "\nPartite Vinte: " + partiteVinte +
                "\nPartite Perse: " + partitePerse +
                "\nLivello: " + livello;
    }
}
