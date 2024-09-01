package main.model;

// model/Player.java

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private String name;
    private PlayerStrategy strategy;

    public Player(String name, PlayerStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
        this.hand = new ArrayList<>();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public int getHandValue() {
        int value = hand.stream().mapToInt(Card::getValue).sum();
        long aceCount = hand.stream().filter(card -> card.getRank().equals("Ace")).count();
        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }
        return value;
    }

    public boolean wantsToHit(Card dealerUpCard) {
        return strategy.wantsToHit(hand, dealerUpCard);
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public Card getVisibleCard() {
        return hand.get(0);
    }

    public void clearHand() {
        hand.clear();
    }

    public PlayerStrategy getStrategy() {
        return strategy;
    }
}
