package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.models.User;
import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private JLabel userDetailsLabel;
    private JLabel userInfo;
    private JButton manageOwnBillsButton;
    private JButton manageOwnProductsButton;

    public UserDashboard(User currentUser) {
        setTitle("User Dashboard");
        setSize(1200, 600);  // Updated window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 244, 248)); // Light background color

        initializeComponents(currentUser);
        layoutComponents();
    }

    private void initializeComponents(User currentUser) {
        userDetailsLabel = new JLabel("User Details:");
        userInfo = new JLabel("Username: " + currentUser.getUsername() + 
                               ", First Name: " + currentUser.getFirstName() + 
                               ", Last Name: " + currentUser.getLastName());

        manageOwnBillsButton = new JButton("Manage My Bills");
        manageOwnProductsButton = new JButton("Manage My Products");

        // Set action listeners for CRUD operations
        manageOwnBillsButton.addActionListener(e -> manageOwnBills());
        manageOwnProductsButton.addActionListener(e -> manageOwnProducts());
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // User Details
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across two columns
        add(userDetailsLabel, gbc);
        
        gbc.gridy = 1;
        add(userInfo, gbc);

        // CRUD Buttons
        gbc.gridwidth = 1; // Reset grid width for buttons
        gbc.gridy = 2;

        gbc.gridx = 0;
        add(manageOwnBillsButton, gbc);
        
        gbc.gridx = 1;
        add(manageOwnProductsButton, gbc);
    }

    private void manageOwnBills() {
        // Logic to manage own bills
        JOptionPane.showMessageDialog(this, "Manage My Bills clicked.");
    }

    private void manageOwnProducts() {
        // Logic to manage own products
        JOptionPane.showMessageDialog(this, "Manage My Products clicked.");
    }

    public static void main(String[] args) {
        User currentUser = new User("user", "Jane", "Smith", "user"); // Example user
        SwingUtilities.invokeLater(() -> new UserDashboard(currentUser).setVisible(true));
    }
}
