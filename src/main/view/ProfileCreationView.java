package main.view;

import javax.swing.*;
import main.model.UserProfile;
import java.awt.*;

/**
 * Represents the {@code ProfileCreationView} class.
 */
public class ProfileCreationView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nicknameField;
    private JComboBox<ImageIcon> avatarSelection;
    private UserProfile userProfile;

    /**
     * Creates a new {@code ProfileCreationView} instance.
     * @param userProfile the user profile
     */
    public ProfileCreationView(UserProfile userProfile) {
        this.userProfile = userProfile;
        initializeFrame();
        setupPanels();
        loadAvatars();
        setVisible(true);
    }

    /**
     * Initializes the frame.
     */
    private void initializeFrame() {
        setTitle("Creazione Profilo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Configures the panels.
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
     * Creates the nickname panel.
     * @return the operation result
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
     * Creates the avatar panel.
     * @return the operation result
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
     * Adds the avatar.
     * @param fileName the file name
     */
    private void addAvatar(String fileName) {
        java.net.URL resource = getClass().getResource("/images/avatars/" + fileName);
        if (resource == null) return;
        ImageIcon icon = new ImageIcon(resource);
        if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            return;
        }
        ImageIcon resizedIcon = resizeIcon(icon, 150, 150);
        avatarSelection.addItem(resizedIcon);
    }

    /**
     * Creates the confirm button.
     * @return the operation result
     */
    private JButton createConfirmButton() {
        JButton confirmButton = new JButton("Accedi");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 20));
        confirmButton.addActionListener(e -> createProfile());
        return confirmButton;
    }

    /**
     * Performs the {@code resizeIcon} operation.
     * @param icon the icon
     * @param width the width
     * @param height the height
     * @return the operation result
     */
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    /**
     * Creates the profile.
     */
    private void createProfile() {
        String nickname = nicknameField.getText().trim();
        if (!UserProfile.isValidNickname(nickname)) {
            JOptionPane.showMessageDialog(this,
                    "Usa da 1 a 24 lettere, numeri, trattini o underscore.",
                    "Nickname non valido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ImageIcon selectedAvatar = (ImageIcon) avatarSelection.getSelectedItem();
        String avatarPath = selectedAvatar == null ? "" : selectedAvatar.getDescription();

        userProfile = new UserProfile(nickname, avatarPath);
        userProfile.loadProfile();
        userProfile.saveProfile();

        dispose();
        new MainMenuView(userProfile).setVisible(true);
    }

    /**
     * Loads the avatars.
     */
    private void loadAvatars() {
        String[] avatarFiles = { "CharlesLeclerc.png", "LewisHamilton.png", "KimiRaikkonen.png", "MaxVerstappen.png" };
        for (String fileName : avatarFiles) {
            addAvatar(fileName);
        }
    }

    /**
     * Returns the user profile.
     * @return the user profile
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }
}
