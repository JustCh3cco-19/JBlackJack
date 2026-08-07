package main.model;

import java.util.List;

/**
 * Represents the {@code DealerStrategy} class.
 */
public class DealerStrategy implements PlayerStrategy {
    /**
     * Determines whether hit.
     * @param hand the hand
     * @return the operation result
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        return BotStrategy.handValue(hand) < 17;
    }
}
