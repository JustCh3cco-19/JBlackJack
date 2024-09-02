package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.BlackjackController;
import main.model.UserProfile;
import main.util.AudioManager;

public class ProfileCreationView extends JFrame {
    private JTextField nicknameField;
    private JComboBox<ImageIcon> avatarSelection;
    private JButton confirmButton;
    private UserProfile userProfile;
    private Thread backgroundMusicThread;

    public ProfileCreationView(BlackjackController controller) {
        setTitle("Crea o accedi al Profilo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usa un GridBagLayout per una disposizione flessibile
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Etichetta e campo per il nickname
        JLabel nicknameLabel = new JLabel("Nome Utente:");
        nicknameField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nicknameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nicknameField, gbc);

        // Etichetta e combobox per la selezione dell'avatar
        JLabel avatarLabel = new JLabel("Scegli un avatar:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(avatarLabel, gbc);

        // Carica e ridimensiona le immagini degli avatar
        String basePath = "/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/images/";
        String[] avatars = { "CharlesLeclerc.png", "MaxVerstappen.png", "LewisHamilton.png" };
        ImageIcon[] avatarIcons = new ImageIcon[avatars.length];
        int avatarSize = 50; // Dimensione foto tessera (es. 50x50 pixel)

        for (int i = 0; i < avatars.length; i++) {
            ImageIcon originalIcon = new ImageIcon(basePath + avatars[i]);
            Image resizedImage = originalIcon.getImage().getScaledInstance(avatarSize, avatarSize, Image.SCALE_SMOOTH);
            avatarIcons[i] = new ImageIcon(resizedImage);
        }

        // Crea il JComboBox con gli ImageIcon ridimensionati
        avatarSelection = new JComboBox<>(avatarIcons);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(avatarSelection, gbc);

        // Bottone per confermare la creazione del profilo
        confirmButton = new JButton("Accedi");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createProfile();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Il bottone occupa entrambe le colonne
        gbc.anchor = GridBagConstraints.CENTER; // Centra il bottone
        add(confirmButton, gbc);

        // Avvia il thread della musica di sottofondo
        startBackgroundMusic();

        pack();
        setVisible(true);
    }

    private void startBackgroundMusic() {
        backgroundMusicThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                AudioManager.getInstance()
                        .play("/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/audio/main_menu.wav");
                try {
                    Thread.sleep(60000); // Supponiamo che la musica duri 30 secondi
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Interrompi il thread se riceve un'interruzione
                }
            }
        });
        backgroundMusicThread.start();
    }

    private void createProfile() {
        if (backgroundMusicThread != null && backgroundMusicThread.isAlive()) {
            backgroundMusicThread.interrupt(); // Ferma la musica di sottofondo
        }

        String nickname = nicknameField.getText();
        ImageIcon selectedAvatar = (ImageIcon) avatarSelection.getSelectedItem();
        String avatarPath = selectedAvatar.getDescription(); // Ottieni il percorso dell'immagine
        userProfile = new UserProfile(nickname, avatarPath);

        AudioManager.getInstance()
                .play("/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/audio/start_game.wav");

        dispose(); // Chiudi la finestra corrente
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
