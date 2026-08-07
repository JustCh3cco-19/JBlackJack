package main.model;

import java.util.*;

/**
 * Represents the {@code Deck} class.
 */

public class Deck {
    /** Stores the cards value. */
    private List<Card> cards;

    /**
     * Creates a new {@code Deck} instance.
     */
    public Deck() {
        initializeDeck();
    }

    /**
     * Initializes the deck.
     */
    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] suits = { "hearts", "diamonds", "clubs", "spades" };
        String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace" };

        Arrays.stream(suits)
                .flatMap(suit -> Arrays.stream(ranks).map(rank -> CardFactory.createCard(suit, rank)))
                .forEach(cards::add);
    }

    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Performs the {@code reset} operation.
     */
    public void reset() {
        initializeDeck();
        shuffle();
    }

    /**
     * Draws a card from the deck.
     * @return the operation result
     */
    public Card drawCard() {
        if (cards.isEmpty()) {
            initializeDeck();
            shuffle();
        }
        return cards.remove(cards.size() - 1);
    }
}
