package main.model;

import java.util.List;

/**
 * Represents the {@code BotStrategy} class.
 */
public class BotStrategy implements PlayerStrategy {

    /**
     * Determines whether hit.
     * @param hand the hand
     * @return the operation result
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        return handValue(hand) < 17;
    }

    static int handValue(List<Card> hand) {
        int value = hand.stream().mapToInt(Card::getValue).sum();
        long aces = hand.stream().filter(card -> "ace".equals(card.getRank())).count();
        while (value > 21 && aces-- > 0) value -= 10;
        return value;
    }
}
