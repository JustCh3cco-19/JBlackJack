package main.model;

public class PlayerProfile {

    private String nome;
    private Player.Ruolo ruolo;

    // Costruttore aggiornato per accettare un nome e un ruolo
    public PlayerProfile(String nome, Player.Ruolo ruolo) {
        this.nome = nome;
        this.ruolo = ruolo;
    }

    // Getter e altri metodi se necessario
    public String getNome() {
        return nome;
    }

    public Player.Ruolo getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return nome + " (" + ruolo + ")";
    }
}
