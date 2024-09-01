package main.model;

// model/UserProfile.java

public class UserProfile {
    private String nickname;
    private String avatarPath;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int level;
    private int experience;

    public UserProfile(String nickname, String avatarPath) {
        this.nickname = nickname;
        this.avatarPath = avatarPath;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.level = 1;
        this.experience = 0;
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
        addExperience(100);
    }

    public void incrementGamesLost() {
        this.gamesLost++;
        addExperience(20);
    }

    private void addExperience(int exp) {
        this.experience += exp;
        while (this.experience >= experienceNeededForNextLevel()) {
            levelUp();
        }
    }

    private void levelUp() {
        this.level++;
    }

    private int experienceNeededForNextLevel() {
        return this.level * 1000;
    }

    // Getters
    public String getNickname() {
        return nickname;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }
}
