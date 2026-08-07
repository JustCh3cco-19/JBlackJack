package main.model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

class StrategyTest {
    @Test
    void dealerUnderstandsSoftHands() {
        DealerStrategy dealer = new DealerStrategy();
        assertTrue(dealer.wantsToHit(List.of(new AceCard("hearts"),
                new NumericCard("clubs", "5", 5))));
        assertFalse(dealer.wantsToHit(List.of(new AceCard("hearts"),
                new NumericCard("clubs", "6", 6))));
    }
}
