package main.model;

import java.io.Serializable;

public class PlayerProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nickname;
    private String avatar;
    // private Player.Ruolo ruolo; // Se il ruolo è richiesto

    // Costruttore che accetta nickname e avatar
    public PlayerProfile(String nickname, String avatar) {
        this.nickname = nickname;
        this.avatar = avatar;
    }

    public String getNome() {
        return nickname;
    }

    // Getter e Setter per nickname e avatar
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Nickname: " + nickname + ", Avatar: " + avatar;
    }
}
