package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.controller.BlackjackController;
import main.model.UserProfile;

public class ProfileCreationView extends JFrame {
    private JTextField nicknameField;
    private JComboBox<String> avatarSelection;
    private JButton confirmButton;
    private UserProfile userProfile;

    public ProfileCreationView(BlackjackController controller) {
        setTitle("Crea il tuo Profilo");
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
        String[] avatars = { "Avatar1.png", "Avatar2.png", "Avatar3.png" };
        avatarSelection = new JComboBox<>(avatars);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(avatarLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(avatarSelection, gbc);

        // Bottone per confermare la creazione del profilo
        confirmButton = new JButton("Crea profilo");
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

        pack();
        setVisible(true);
    }

    private void createProfile() {
        String nickname = nicknameField.getText();
        String avatarPath = (String) avatarSelection.getSelectedItem();
        userProfile = new UserProfile(nickname, avatarPath);
        dispose(); // Chiudi la finestra corrente
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }
}