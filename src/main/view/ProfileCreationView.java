package main.view;

import javax.swing.*;
import main.model.UserProfile;
import java.awt.*;

/**
 * La classe ProfileCreationView rappresenta la View per la creazione del
 * profilo utente.
 * 
 * <p>
 * Pattern adottati:
 * - MVC (Model-View-Controller): come parte della View per gestire
 * l'interfaccia grafica della creazione del profilo utente;
 * - Command: per poter creare il profilo utente.
 * </p>
 */
public class ProfileCreationView extends JFrame {
    private JTextField nicknameField;
    private JComboBox<ImageIcon> avatarSelection;
    private UserProfile userProfile;

    /**
     * Costruttore che modella la finestra e i suoi componenti.
     * 
     * @param userProfile Il profilo utente da creare.
     */
    public ProfileCreationView(UserProfile userProfile) {
        this.userProfile = userProfile;
        initializeFrame();
        setupPanels();
        loadAvatars();
        setVisible(true);
    }

    /**
     * Metodo che inizializza le proprietà base della finestra.
     */
    private void initializeFrame() {
        setTitle("Creazione Profilo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Metodo che configura i pannelli principali della finestra utilizzando
     * GridBagLayout.
     * 
     * <p>
     * Pattern adottati:
     * - Composite: organizza i componenti dell'interfaccia.
     * </p>
     */
    private void setupPanels() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Pannello Nickname
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        add(createNicknamePanel(), gbc);

        // Pannello Avatar
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(createAvatarPanel(), gbc);

        // Bottone di conferma
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.SOUTH;
        add(createConfirmButton(), gbc);
    }

    /**
     * Metodo che crea il JPanel per l'inserimento del nickname.
     * 
     * @return JPanel contenente il campo di input per il nickname.
     */
    private JPanel createNicknamePanel() {
        JPanel nicknamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel nicknameLabel = new JLabel("Nickname: ");
        nicknameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nicknameField = new JTextField(20);
        nicknameField.setFont(new Font("Arial", Font.PLAIN, 18));
        nicknamePanel.add(nicknameLabel);
        nicknamePanel.add(nicknameField);
        return nicknamePanel;
    }

    /**
     * Metodo che crea il pannello per la selezione dell'avatar.
     * 
     * @return JPanel contenente la JComboBox per la selezione dell'avatar.
     */
    private JPanel createAvatarPanel() {
        JPanel avatarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel avatarLabel = new JLabel("Seleziona Avatar:");
        avatarLabel.setFont(new Font("Arial", Font.BOLD, 20));

        avatarSelection = new JComboBox<>();
        avatarSelection.setPreferredSize(new Dimension(150, 150));

        avatarPanel.add(avatarLabel);
        avatarPanel.add(avatarSelection);
        return avatarPanel;
    }

    /**
     * Metodo che aggiunge un avatar alla JComboBox di selezione.
     * 
     * @param fileName Il nome del file dell'avatar da aggiungere.
     */
    private void addAvatar(String fileName) {
        ImageIcon icon = new ImageIcon("src/main/resources/images/avatars/" + fileName);
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Errore nel caricamento dell'immagine: " + fileName);
            return;
        }
        ImageIcon resizedIcon = resizeIcon(icon, 150, 150);
        avatarSelection.addItem(resizedIcon);
    }

    /**
     * Metodo che crea il bottone di conferma per la creazione del profilo.
     * 
     * @return JButton configurato per la conferma.
     */
    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Accedi");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.addActionListener(e -> createProfile());
        return confirmButton;
    }

    /**
     * Metodo che ridimensiona un'icona alle dimensioni specificate.
     * 
     * @param icon   L'icona da ridimensionare.
     * @param width  La larghezza desiderata.
     * @param height L'altezza desiderata.
     * @return ImageIcon ridimensionata.
     */
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    /**
     * Metodo che crea o aggiorna il profilo utente con i dati inseriti.
     */
    private void createProfile() {
        String nickname = nicknameField.getText();
        ImageIcon selectedAvatar = (ImageIcon) avatarSelection.getSelectedItem();
        String avatarPath = selectedAvatar.getDescription();

        userProfile = new UserProfile(nickname, avatarPath);
        userProfile.loadProfile();

        dispose();
        new MainMenuView(userProfile).setVisible(true);
    }

    /**
     * Metodo che carica gli avatar presenti nella JComboBox di selezione.
     */
    private void loadAvatars() {
        String[] avatarFiles = { "CharlesLeclerc.png", "LewisHamilton.png", "KimiRaikkonen.png", "MaxVerstappen.png" };
        for (String fileName : avatarFiles) {
            addAvatar(fileName);
        }
    }

    /**
     * Getter che restituisce il profilo utente creato o modificato.
     * 
     * @return UserProfile che rappresenta il profilo utente.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }
}