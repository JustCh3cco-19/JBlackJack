package main.model;

// model/PlayerStrategy.java
import java.util.List;

interface PlayerStrategy {
    boolean wantsToHit(List<Card> hand, Card dealerUpCard);
}

class HumanPlayerStrategy implements PlayerStrategy {
    @Override
    public boolean wantsToHit(List<Card> hand, Card dealerUpCard) {
        return false; // This will be controlled by user input
    }
}

class BasicAIStrategy implements PlayerStrategy {
    @Override
    public boolean wantsToHit(List<Card> hand, Card dealerUpCard) {
        int handValue = hand.stream().mapToInt(Card::getValue).sum();
        return handValue < 17;
    }
}
