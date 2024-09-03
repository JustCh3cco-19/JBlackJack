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
        setSize(800, 600); // Imposta la dimensione della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Usa un GridBagLayout per una disposizione flessibile
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Margini più ampi per i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL; // I componenti riempiono orizzontalmente lo spazio disponibile

        // Etichetta e campo per il nickname
        JLabel nicknameLabel = new JLabel("Nome Utente:");
        nicknameLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Aumenta la dimensione del font
        nicknameField = new JTextField(20); // Campo di testo più lungo
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Aumenta la dimensione del font
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nicknameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nicknameField, gbc);

        // Etichetta e combobox per la selezione dell'avatar
        JLabel avatarLabel = new JLabel("Scegli un avatar:");
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Aumenta la dimensione del font
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(avatarLabel, gbc);

        // Carica e ridimensiona le immagini degli avatar
        String basePath = "/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/images/avatars/";
        String[] avatars = { "CharlesLeclerc.png", "MaxVerstappen.png", "LewisHamilton.png", "KimiRaikkonen.png" };
        ImageIcon[] avatarIcons = new ImageIcon[avatars.length];
        int avatarSize = 100; // Dimensione maggiore per gli avatar (es. 100x100 pixel)

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
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20)); // Aumenta la dimensione del font
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

        setVisible(true);
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
                .play("/home/justch3cco/eclipse-workspace/JBlackJack/src/main/resources/audio/game.wav");

        dispose(); // Chiudi la finestra corrente
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
