package main.controller;

import main.model.Player;
import main.model.Deck;
import main.util.AudioManager;

public class PlayerController {
	private Player player;
	private Deck deck;

	public PlayerController(Player player, Deck deck) {
		this.player = player;
		this.deck = deck;
	}

	// Gestione della mano del giocatore
	public void gestisciMano() {
		AudioManager audioManager = AudioManager.getInstance(); // Ottieni l'istanza singleton
		while (player.devePescare()) {
			player.addCarta(deck.pescaCarta());
			audioManager.play("card_draw"); // Riproduci l'effetto sonoro
		}
	}

	// Metodo per aggiornare le statistiche dopo una partita
	public void aggiornaStatisticheDopoPartita(boolean vinta) {
		player.aggiornaStatisticheDopoPartita(vinta);
		// Non è stato specificato l'uso dell'AudioManager qui, ma se necessario, puoi
		// aggiungerlo
	}

	// Altri metodi come gestisciClickSuCarta ecc.
}