package main.model;

import java.util.*;
import java.util.stream.*;

class Deck {
    private static Deck instance;
    private List<Card> cards;

    private Deck() {
        initializeDeck();
    }

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] suits = { "Cuori", "Quadri", "Fiori", "Spade" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Regina", "Re", "Asso" };

        Arrays.stream(suits)
                .flatMap(suit -> Arrays.stream(ranks).map(rank -> CardFactory.createCard(suit, rank)))
                .forEach(cards::add);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            initializeDeck(); // Ricrea il mazzo
            shuffle(); // Rimescola il mazzo
        }
        return cards.remove(cards.size() - 1);
    }
}
