package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.UserDAO;
import com.mycompany.billingsysswing.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private final UserDAO userDAO = new UserDAO();

    Font buttonFont = new Font("Segoe UI", Font.PLAIN, 16);
    Font labelFont = new Font("Segoe UI", Font.BOLD, 16);

    private static final int FRAME_WIDTH = 1200;  
    private static final int FRAME_HEIGHT = 600; 
    private static final int LABEL_WIDTH = 100; 
    private static final int FIELD_WIDTH = 300; 
    private static final int BUTTON_WIDTH = 150; 

    public LoginForm() {
        setTitle("Login");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 244, 248)); 
        
        initializeComponents();
        setLayout(new GridBagLayout());  
        positionComponents();
    }

    private void initializeComponents() {
        usernameField = new JTextField(FIELD_WIDTH);
        passwordField = new JPasswordField(FIELD_WIDTH);
        
        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(40, 233, 140)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(buttonFont);
        loginButton.setPreferredSize(new Dimension(BUTTON_WIDTH, 40)); 
        
        registerButton = new JButton("Register"); 
        registerButton.setBackground(new Color(0, 123, 255)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(buttonFont);
        registerButton.setPreferredSize(new Dimension(BUTTON_WIDTH, 40)); 

        loginButton.addActionListener(e -> loginHandler());
        registerButton.addActionListener(e -> openRegistrationForm());

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginHandler();
                }
            }
        });
    }

    private void positionComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  
        add(titleLabel, gbc);
        gbc.gridwidth = 1;  

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(loginButton, gbc);

        gbc.gridx = 1;
        add(registerButton, gbc);
    }

    private void loginHandler() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try {
            if (login(username, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                redirectToDashboard(username);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "An error occurred during login. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean login(String username, String password) throws SQLException {
        User user = userDAO.getUserByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    private void redirectToDashboard(String username) throws SQLException {
        User user = userDAO.getUserByUsername(username); // Get User object
        if (user != null) {
            if ("admin".equals(user.getRole())) {
                SwingUtilities.invokeLater(() -> new AdminDashboard(user).setVisible(true));
            } else {
                SwingUtilities.invokeLater(() -> new UserDashboard(user).setVisible(true)); // Pass User object to UserDashboard
            }
        }   
    }

    private void openRegistrationForm() {
        dispose(); 
        new RegistrationForm().setVisible(true); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
