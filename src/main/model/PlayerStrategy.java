package main.model;

import java.util.List;

public interface PlayerStrategy {
    boolean wantsToHit(List<Card> hand);
}