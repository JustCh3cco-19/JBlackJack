package main.model;

import java.util.*;

public class Deck {
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
        String[] suits = { "hearts", "diamonds", "clubs", "spades" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace" };

        Arrays.stream(suits)
                .flatMap(suit -> Arrays.stream(ranks).map(rank -> CardFactory.createCard(suit, rank)))
                .forEach(cards::add);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            initializeDeck(); // Recreate deck if empty
            shuffle(); // Shuffle the newly created deck
        }
        return cards.remove(cards.size() - 1);
    }
}
