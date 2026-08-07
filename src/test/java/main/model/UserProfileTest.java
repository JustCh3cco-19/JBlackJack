package main.model;

import static org.junit.jupiter.api.Assertions.*;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class UserProfileTest {
    @TempDir Path directory;

    @Test
    void profileRoundTripPreservesStatistics() {
        UserProfile saved = new UserProfile("Giocatore_1", "/avatar.png", directory);
        saved.incrementGamesPlayed();
        saved.incrementGamesWon();
        assertTrue(saved.saveProfile());

        UserProfile loaded = new UserProfile("Giocatore_1", "", directory);
        assertTrue(loaded.loadProfile());
        assertEquals(1, loaded.getGamesPlayed());
        assertEquals(1, loaded.getGamesWon());
        assertEquals(100, loaded.getExperience());
        assertEquals("/avatar.png", loaded.getAvatarPath());
    }

    @Test
    void missingAndMalformedProfilesFailWithoutChangingDefaults() throws Exception {
        UserProfile missing = new UserProfile("missing", "", directory);
        assertFalse(missing.loadProfile());

        java.nio.file.Files.writeString(directory.resolve("broken_profilo.txt"), "broken\ninvalid");
        UserProfile broken = new UserProfile("broken", "", directory);
        assertFalse(broken.loadProfile());
        assertEquals(0, broken.getGamesPlayed());
    }

    @Test
    void nicknameCannotEscapeProfileDirectory() {
        assertFalse(UserProfile.isValidNickname("../outside"));
        assertThrows(IllegalArgumentException.class,
                () -> new UserProfile("../outside", "", directory));
    }

    @Test
    void firstLevelRequiresOneThousandExperience() {
        UserProfile profile = new UserProfile("level_test", "", directory);
        for (int i = 0; i < 9; i++) profile.incrementGamesWon();
        assertEquals(0, profile.getLevel());
        profile.incrementGamesWon();
        assertEquals(1, profile.getLevel());
    }
}
