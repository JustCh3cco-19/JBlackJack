package main.model;

import java.util.List;

/**
 * Represents the {@code PlayerStrategy} interface.
 */
public interface PlayerStrategy {

    /**
     * Determines whether hit.
     * @param hand the hand
     * @return the operation result
     */
    boolean wantsToHit(List<Card> hand);
}