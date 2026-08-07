package main.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CardFactoryTest {
    @Test
    void createsValidCards() {
        assertInstanceOf(AceCard.class, CardFactory.createCard("hearts", "ace"));
        assertInstanceOf(FaceCard.class, CardFactory.createCard("clubs", "king"));
        assertEquals(7, CardFactory.createCard("spades", "7").getValue());
    }

    @Test
    void rejectsInvalidSuitAndRank() {
        assertThrows(IllegalArgumentException.class, () -> CardFactory.createCard("stars", "ace"));
        assertThrows(IllegalArgumentException.class, () -> CardFactory.createCard("hearts", "1"));
        assertThrows(IllegalArgumentException.class, () -> CardFactory.createCard("hearts", "11"));
    }
}
