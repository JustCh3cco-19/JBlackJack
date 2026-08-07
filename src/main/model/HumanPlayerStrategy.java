package main.model;

import java.util.List;

/**
 * Represents the {@code HumanPlayerStrategy} class.
 */
public class HumanPlayerStrategy implements PlayerStrategy {

    /**
     * Determines whether hit.
     * @param hand the hand
     * @return the operation result
     */
    @Override
    public boolean wantsToHit(List<Card> hand) {
        return false;
    }
}