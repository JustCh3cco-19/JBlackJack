package main.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the {@code Player} class.
 */
public class Player {
    /** Stores the hand value. */
    private List<Card> hand;
    /** Stores the name value. */
    private String name;
    /** Stores the strategy value. */
    private PlayerStrategy strategy;
    /** Stores the is human value. */
    private boolean isHuman;

    /**
     * Creates a new {@code Player} instance.
     * @param name the name
     * @param strategy the strategy
     * @param isHuman the is human
     */
    public Player(String name, PlayerStrategy strategy, boolean isHuman) {
        this.name = name;
        this.strategy = strategy;
        this.isHuman = isHuman;
        this.hand = new ArrayList<>();
    }

    /**
     * Adds the card.
     * @param card the card
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Determines whether hit.
     * @return the operation result
     */
    public boolean wantsToHit() {
        return strategy.wantsToHit(hand);
    }

    /**
     * Clears the hand.
     */
    public void clearHand() {
        hand.clear();
    }

    /**
     * Returns whether human.
     * @return whether human
     */
    public boolean isHuman() {
        return isHuman;
    }

    /**
     * Returns the hand value.
     * @return the hand value
     */
    public int getHandValue() {
        int value = hand.stream().mapToInt(Card::getValue).sum();
        long aces = hand.stream().filter(card -> "ace".equals(card.getRank())).count();
        while (value > 21 && aces-- > 0) {
            value -= 10;
        }
        return value;
    }

    /**
     * Returns the hand.
     * @return the hand
     */
    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    /**
     * Returns the name.
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the strategy.
     * @return the strategy
     */
    public PlayerStrategy getStrategy() {
        return strategy;
    }

}
