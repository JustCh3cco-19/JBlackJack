package main.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerManager {
    private static final String PROFILE_FILE_PATH = "PlayerProfile.ser";

    // Metodo per creare un nuovo profilo
    public PlayerProfile creaProfilo(String nickname, String avatar) {
        PlayerProfile profilo = new PlayerProfile(nickname, avatar);
        salvaProfilo(profilo);
        return profilo;
    }

    // Metodo per caricare il profilo esistente
    public PlayerProfile caricaProfilo() {
        PlayerProfile profilo = null;
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(PROFILE_FILE_PATH))) {
            profilo = (PlayerProfile) input.readObject();
            System.out.println("Profilo caricato correttamente");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nessun profilo trovato. Per giocare, devi crearne uno.");
        }
        return profilo;
    }

    // Metodo per salvare il profilo su disco
    public void salvaProfilo(PlayerProfile profilo) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(PROFILE_FILE_PATH))) {
            output.writeObject(profilo);
            System.out.println("Profilo salvato correttamente");
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio del profilo");
        }
    }
}
