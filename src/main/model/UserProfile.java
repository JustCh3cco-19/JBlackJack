package main.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Represents the {@code UserProfile} class.
 */
public class UserProfile {
    private static final Pattern VALID_NICKNAME = Pattern.compile("[\\p{L}\\p{N}_-]{1,24}");
    private final Path profilesDirectory;
    private String nickname;
    private String avatarPath;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int level;
    private int experience;

    /**
     * Creates a new {@code UserProfile} instance.
     * @param nickname the nickname
     * @param avatarPath the avatar path
     */
    public UserProfile(String nickname, String avatarPath) {
        this(nickname, avatarPath, Path.of("profiles"));
    }

    UserProfile(String nickname, String avatarPath, Path profilesDirectory) {
        validateNickname(nickname);
        this.profilesDirectory = profilesDirectory;
        this.nickname = nickname;
        this.avatarPath = avatarPath == null ? "" : avatarPath;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.level = 0;
        this.experience = 0;
    }

    /**
     * Increments the games played.
     */
    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    /**
     * Increments the games won.
     */
    public void incrementGamesWon() {
        this.gamesWon++;
        addExperience(100);
    }

    /**
     * Increments the games lost.
     */
    public void incrementGamesLost() {
        this.gamesLost++;
    }

    /**
     * Adds the experience.
     * @param exp the exp
     */
    private void addExperience(int exp) {
        this.experience += exp;
        while (this.experience >= experienceNeededForNextLevel()) {
            levelUp();
        }
    }

    /**
     * Performs the {@code levelUp} operation.
     */
    private void levelUp() {
        this.level++;
    }

    /**
     * Performs the {@code experienceNeededForNextLevel} operation.
     * @return the operation result
     */
    private int experienceNeededForNextLevel() {
        return (this.level + 1) * 1000;
    }

    /**
     * Saves the profile.
     * @return the operation result
     */
    public boolean saveProfile() {
        try {
            Files.createDirectories(profilesDirectory);
            Path destination = profilePath();
            Path temporary = Files.createTempFile(profilesDirectory, nickname, ".tmp");
            Files.write(temporary, List.of(nickname, avatarPath, Integer.toString(gamesPlayed),
                    Integer.toString(gamesWon), Integer.toString(gamesLost), Integer.toString(level),
                    Integer.toString(experience)), StandardCharsets.UTF_8);
            try {
                Files.move(temporary, destination, StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (java.nio.file.AtomicMoveNotSupportedException e) {
                Files.move(temporary, destination, StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Loads the profile.
     * @return the operation result
     */
    public boolean loadProfile() {
        Path path = profilePath();
        if (!Files.isRegularFile(path)) return false;
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            if (lines.size() != 7 || !nickname.equals(lines.get(0))) return false;
            int loadedPlayed = nonNegative(lines.get(2));
            int loadedWon = nonNegative(lines.get(3));
            int loadedLost = nonNegative(lines.get(4));
            int loadedLevel = nonNegative(lines.get(5));
            int loadedExperience = nonNegative(lines.get(6));
            if (loadedWon + loadedLost > loadedPlayed) return false;
            avatarPath = lines.get(1);
            gamesPlayed = loadedPlayed;
            gamesWon = loadedWon;
            gamesLost = loadedLost;
            level = loadedLevel;
            experience = loadedExperience;
            return true;
        } catch (IOException | NumberFormatException e) {
            return false;
        }
    }

    private Path profilePath() {
        return profilesDirectory.resolve(nickname + "_profilo.txt");
    }

    private static int nonNegative(String value) {
        int parsed = Integer.parseInt(value);
        if (parsed < 0) throw new NumberFormatException("negative value");
        return parsed;
    }

    /**
     * Checks whether a nickname is safe and valid for profile storage.
     *
     * @param nickname the nickname to validate
     * @return whether the nickname is valid
     */
    public static boolean isValidNickname(String nickname) {
        return nickname != null && VALID_NICKNAME.matcher(nickname.trim()).matches();
    }

    private static void validateNickname(String nickname) {
        if (!isValidNickname(nickname)) {
            throw new IllegalArgumentException("Il nickname deve contenere 1-24 lettere, numeri, _ o -");
        }
    }

    /**
     * Returns the nickname.
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the avatar path.
     * @return the avatar path
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Returns the games played.
     * @return the games played
     */
    public int getGamesPlayed() {
        return gamesPlayed;
    }

    /**
     * Returns the games won.
     * @return the games won
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Returns the games lost.
     * @return the games lost
     */
    public int getGamesLost() {
        return gamesLost;
    }

    /**
     * Returns the level.
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the experience.
     * @return the experience
     */
    public int getExperience() {
        return experience;
    }
}
