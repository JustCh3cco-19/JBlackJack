package main.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerTest {
    private Player player() {
        return new Player("Test", new HumanPlayerStrategy(), true);
    }

    @Test
    void aceBecomesOneToAvoidBust() {
        Player player = player();
        player.addCard(new AceCard("hearts"));
        player.addCard(new NumericCard("clubs", "9", 9));
        player.addCard(new NumericCard("spades", "5", 5));
        assertEquals(15, player.getHandValue());
    }

    @Test
    void multipleAcesAreHandledCorrectly() {
        Player player = player();
        player.addCard(new AceCard("hearts"));
        player.addCard(new AceCard("clubs"));
        player.addCard(new NumericCard("spades", "9", 9));
        assertEquals(21, player.getHandValue());
    }

    @Test
    void exposedHandCannotBeModified() {
        Player player = player();
        assertThrows(UnsupportedOperationException.class,
                () -> player.getHand().add(new AceCard("hearts")));
    }
}
