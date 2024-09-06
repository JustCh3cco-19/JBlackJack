package main.view;

import javax.swing.*;
import main.model.UserProfile;
import java.awt.*;

public class ProfileCreationView extends JFrame {
    private JTextField nicknameField;
    private JComboBox<ImageIcon> avatarSelection;
    private UserProfile userProfile;

    public ProfileCreationView(UserProfile userProfile) {
        this.userProfile = userProfile;
        initializeFrame();
        setupPanels();
        loadAvatars(); // Metodo per caricare gli avatar
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Creazione Profilo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void setupPanels() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Imposta gli spazi tra i componenti
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Pannello Nickname
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Estende il componente su due colonne
        gbc.anchor = GridBagConstraints.NORTH; // Allinea il componente in alto
        add(createNicknamePanel(), gbc);

        // Pannello Avatar
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centra il componente
        add(createAvatarPanel(), gbc);

        // Bottone di conferma
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH; // Allinea il componente in basso
        add(createConfirmButton(), gbc);
    }

    private JPanel createNicknamePanel() {
        JPanel nicknamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nicknameLabel = new JLabel("Nickname: ");
        nicknameLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Font più grande
        nicknameField = new JTextField(20);
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 18)); // Font più grande
        nicknamePanel.add(nicknameLabel);
        nicknamePanel.add(nicknameField);
        return nicknamePanel;
    }

    private JPanel createAvatarPanel() {
        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel avatarLabel = new JLabel("Seleziona Avatar:");
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Font più grande

        // Inizializzazione di avatarSelection prima di usarlo
        avatarSelection = new JComboBox<>();
        avatarSelection.setPreferredSize(new Dimension(150, 150)); // Riduci la dimensione della selezione dell'avatar

        avatarPanel.add(avatarLabel);
        avatarPanel.add(avatarSelection);
        return avatarPanel;
    }

    private void addAvatar(String fileName) {
        ImageIcon icon = new ImageIcon("src/main/resources/images/avatars/" + fileName);
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Errore nel caricamento dell'immagine: " + fileName);
            return;
        }
        // Modifica le dimensioni qui per ingrandire l'immagine
        ImageIcon resizedIcon = resizeIcon(icon, 150, 150); // Aumenta le dimensioni a 150x150 pixel
        avatarSelection.addItem(resizedIcon);
    }

    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Accedi");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20)); // Font più grande
        confirmButton.addActionListener(e -> createProfile());
        return confirmButton;
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private void createProfile() {
        String nickname = nicknameField.getText();
        ImageIcon selectedAvatar = (ImageIcon) avatarSelection.getSelectedItem();
        String avatarPath = selectedAvatar.getDescription();

        // Carica il profilo se esiste già, altrimenti crea un nuovo profilo
        userProfile = new UserProfile(nickname, avatarPath);
        userProfile.loadProfile(); // Carica il profilo se esiste

        dispose();
        new MainMenuView(userProfile).setVisible(true);
    }

    private void loadAvatars() {
        String[] avatarFiles = { "CharlesLeclerc.png", "LewisHamilton.png", "KimiRaikkonen.png", "MaxVerstappen.png" };
        for (String fileName : avatarFiles) {
            addAvatar(fileName);
        }
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}
