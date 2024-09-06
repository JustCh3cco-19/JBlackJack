package main.model;

import java.util.List;

public class BotStrategy implements PlayerStrategy {
    @Override
    public boolean wantsToHit(List<Card> hand) {
        int handValue = hand.stream().mapToInt(Card::getValue).sum();
        return handValue < 17;
    }
}
