package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminDashboard extends JFrame {
    private JLabel adminDashboardLabel;
    private JLabel userDetailsLabel;
    private JLabel userInfo;
    private JButton manageBillsButton;
    private JButton manageProductsButton;
    private JButton manageCustomersButton;
    private JButton manageBillItemsButton;
    private JButton viewBillItemsButton; // Button for Bill List View
    private JButton manageUsersButton; // New button for User Management

    public AdminDashboard(User currentUser) {
        setTitle("Admin Dashboard");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 244, 248)); // Light background color

        initializeComponents(currentUser);
        layoutComponents();
    }

    private void initializeComponents(User currentUser) {
        // Dashboard title
        adminDashboardLabel = new JLabel("Admin Dashboard");
        adminDashboardLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Modern font for heading
        adminDashboardLabel.setForeground(new Color(38, 50, 56)); // Dark color for text

        userDetailsLabel = new JLabel("User Details:");
        userDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Font for labels
        userInfo = new JLabel("Username: " + currentUser.getUsername() + 
                               ", First Name: " + currentUser.getFirstName() + 
                               ", Last Name: " + currentUser.getLastName());
        userInfo.setFont(new Font("Arial", Font.PLAIN, 18)); // Font for user info

        manageBillsButton = new JButton("Manage Bills");
        manageProductsButton = new JButton("Manage Products");
        manageCustomersButton = new JButton("Manage Customers");
        manageBillItemsButton = new JButton("Manage Bill Items");
        viewBillItemsButton = new JButton("View Bill Items"); // Button for Bill List View
        manageUsersButton = new JButton("Manage Users"); // New button for User Management

        // Set font and background color for buttons
        JButton[] buttons = {manageBillsButton, manageProductsButton, manageCustomersButton, manageBillItemsButton, viewBillItemsButton, manageUsersButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 16)); // Button font
            button.setBackground(new Color(76, 175, 80)); // Green background color
            button.setForeground(Color.WHITE); // White text color
            button.setFocusable(false); // Remove focus outline
            button.setBorderPainted(false); // Remove border
        }

        // Set action listeners for CRUD operations
        manageBillsButton.addActionListener(e -> manageBills());
        manageProductsButton.addActionListener(e -> manageProducts());
        manageCustomersButton.addActionListener(e -> manageCustomers());
        manageBillItemsButton.addActionListener(e -> manageBillItems());
        viewBillItemsButton.addActionListener(e -> viewBillItems());
        manageUsersButton.addActionListener(e -> manageUsers()); // Action listener for User Management
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Increase space between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Dashboard Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across two columns
        add(adminDashboardLabel, gbc);
        
        // User Details
        gbc.gridy = 1;
        add(userDetailsLabel, gbc);
        
        gbc.gridy = 2;
        add(userInfo, gbc);

        // CRUD Buttons
        gbc.gridwidth = 1; // Reset grid width for buttons
        gbc.gridy = 3;

        gbc.gridx = 0;
        add(manageBillsButton, gbc);
        
        gbc.gridx = 1;
        add(manageProductsButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(manageCustomersButton, gbc);
        
        gbc.gridx = 1;
        add(manageBillItemsButton, gbc);

        // Adding the new button for View Bill Items
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span across two columns
        add(viewBillItemsButton, gbc);

        // Adding the new button for User Management
        gbc.gridy = 6; // New row for User Management button
        add(manageUsersButton, gbc);
    }

    private void manageBills() {
        SwingUtilities.invokeLater(() -> {
            BillForm billForm = new BillForm();
            billForm.setVisible(true); // Open BillForm
            billForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AdminDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void manageProducts() {
        SwingUtilities.invokeLater(() -> {
            ProductForm productForm = new ProductForm();
            productForm.setVisible(true); // Open ProductForm
            productForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AdminDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void manageCustomers() {
        SwingUtilities.invokeLater(() -> {
            CustomerForm customerForm = new CustomerForm();
            customerForm.setVisible(true); // Open CustomerForm
            customerForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AdminDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void manageBillItems() {
//        SwingUtilities.invokeLater(() -> {
//            BillItemForm billItemForm = new BillItemForm();
//            billItemForm.setVisible(true); // Open BillItemForm
//            billItemForm.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    AdminDashboard.this.setVisible(true); // Show dashboard again
//                }
//            });
//            this.setVisible(false); // Hide the dashboard
//        });
    }

    private void viewBillItems() {
//        SwingUtilities.invokeLater(() -> {
//            BillListView billListView = new BillListView();
//            billListView.setVisible(true); // Open BillListView
//            billListView.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    AdminDashboard.this.setVisible(true); // Show dashboard again
//                }
//            });
//            this.setVisible(false); // Hide the dashboard
//        });
    }

    private void manageUsers() {
        SwingUtilities.invokeLater(() -> {
            UserManagementForm userManagementForm = new UserManagementForm();
            userManagementForm.setVisible(true); // Open UserManagementForm
            userManagementForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    AdminDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }
}
