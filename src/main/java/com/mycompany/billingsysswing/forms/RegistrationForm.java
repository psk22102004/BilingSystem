package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.UserDAO;
import com.mycompany.billingsysswing.models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JButton registerButton;
    private JButton loginButton; // New Login button

    private final UserDAO userDAO = new UserDAO();

    Font buttonFont = new Font("Segoe UI", Font.PLAIN, 16); // Button font size
    Font labelFont = new Font("Segoe UI", Font.BOLD, 16); // Label font size

    // Constants for layout positions
    private static final int FRAME_WIDTH = 1200;  // Consistent width
    private static final int FRAME_HEIGHT = 600; // Adjusted height
    private static final int FIELD_WIDTH = 300; // Field width
    private static final int BUTTON_WIDTH = 150; // Button width

    public RegistrationForm() {
        setTitle("Register");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 244, 248)); // Light background color

        initializeComponents();
        setLayout(new GridBagLayout()); // Use GridBagLayout for better control
        positionComponents();
    }

    private void initializeComponents() {
        // Initialize UI components
        usernameField = new JTextField(FIELD_WIDTH);
        passwordField = new JPasswordField(FIELD_WIDTH);
        emailField = new JTextField(FIELD_WIDTH);
        firstNameField = new JTextField(FIELD_WIDTH);
        lastNameField = new JTextField(FIELD_WIDTH);

        // Create buttons with colors and larger sizes
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(40, 233, 140)); // Bright green
        registerButton.setForeground(Color.BLACK);
        registerButton.setFont(buttonFont);
        registerButton.setPreferredSize(new Dimension(BUTTON_WIDTH, 40)); // Set button size

        loginButton = new JButton("Login"); // New Login button
        loginButton.setBackground(new Color(40, 140, 233)); // Bright blue
        loginButton.setForeground(Color.BLACK);
        loginButton.setFont(buttonFont);
        loginButton.setPreferredSize(new Dimension(BUTTON_WIDTH, 40)); // Set button size

        // Action listeners
        registerButton.addActionListener(e -> registerUser());
        loginButton.addActionListener(e -> openLoginForm()); // Redirect to login form
    }

    private void positionComponents() {
        // Layout using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Adjusting insets to reduce padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Register", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36)); // Set heading font size larger
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Make title span across two columns
        add(titleLabel, gbc);
        gbc.gridwidth = 1;  // Reset gridwidth to 1 for subsequent components

        // Username Label
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        // First Name Label
        gbc.gridx = 0;
        gbc.gridy = 3; // Update the row number accordingly
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        add(firstNameLabel, gbc);

        gbc.gridx = 1;
        add(firstNameField, gbc); // Add the corresponding JTextField for First Name

        // Last Name Label
        gbc.gridx = 0;
        gbc.gridy = 4; // Update the row number accordingly
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        add(lastNameLabel, gbc);

        gbc.gridx = 1;
        add(lastNameField, gbc); // Add the corresponding JTextField for Last Name

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5; // Update the row number accordingly
        add(registerButton, gbc);

        gbc.gridx = 1;
        add(loginButton, gbc); // Add Login button
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String email = emailField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        User user = new User(username, password, email, "user", firstName, lastName);
        try {
            if (userDAO.isDuplicateUser(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userDAO.addUser(user);
                JOptionPane.showMessageDialog(this, "Registration successful!");
                dispose(); // Close the registration form
                new LoginForm().setVisible(true); // Open login form
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "An error occurred during registration. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openLoginForm() {
        dispose(); // Close the registration form
        new LoginForm().setVisible(true); // Open login form
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm().setVisible(true));
    }
}
