package main.model;

import java.util.List;

public class HumanPlayerStrategy implements PlayerStrategy {
    @Override
    public boolean wantsToHit(List<Card> hand, Card dealerUpCard) {
        return false;
    }
}
