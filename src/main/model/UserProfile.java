package main.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        this.level = 0;
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

    // Metodo per salvare il profilo utente su un file di testo
    public void saveProfile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nickname + "_profilo.txt"))) {
            writer.write(nickname + "\n");
            writer.write(avatarPath + "\n");
            writer.write(gamesPlayed + "\n");
            writer.write(gamesWon + "\n");
            writer.write(gamesLost + "\n");
            writer.write(level + "\n");
            writer.write(experience + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per caricare il profilo utente da un file di testo
    public void loadProfile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nickname + "_profilo.txt"))) {
            this.nickname = reader.readLine();
            this.avatarPath = reader.readLine();
            this.gamesPlayed = Integer.parseInt(reader.readLine());
            this.gamesWon = Integer.parseInt(reader.readLine());
            this.gamesLost = Integer.parseInt(reader.readLine());
            this.level = Integer.parseInt(reader.readLine());
            this.experience = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
