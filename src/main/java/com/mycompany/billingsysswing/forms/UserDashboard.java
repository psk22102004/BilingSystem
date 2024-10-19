package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserDashboard extends JFrame {
    private JLabel userDashboardLabel;
    private JLabel userDetailsLabel;
    private JLabel userRoleLabel;
    private JLabel userInfo;
    private JButton addBillButton;
    private JButton viewBillsButton;
    private JButton addProductButton;
    private JButton addCustomerButton;
    private JButton viewBillItemsButton;

    public UserDashboard(User currentUser) {
        setTitle("User Dashboard");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 244, 248)); // Light background color

        initializeComponents(currentUser);
        layoutComponents();
    }

    private void initializeComponents(User currentUser) {
        // Dashboard title
        userDashboardLabel = new JLabel("User Dashboard");
        userDashboardLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Modern font for heading
        userDashboardLabel.setForeground(new Color(38, 50, 56)); // Dark color for text 
        
        userRoleLabel = new JLabel("Role: " + currentUser.getRole());
        userRoleLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Font for user role
        
        userDetailsLabel = new JLabel("User Details:");
        userDetailsLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Font for labels
        userInfo = new JLabel("Username: " + currentUser.getUsername() +
                               ", First Name: " + currentUser.getFirstName() +
                               ", Last Name: " + currentUser.getLastName());
        userInfo.setFont(new Font("Arial", Font.PLAIN, 18)); // Font for user info

        addBillButton = new JButton("Add Bill");
        viewBillsButton = new JButton("View Bills");
        addProductButton = new JButton("Add Product");
        addCustomerButton = new JButton("Add Customer");
        viewBillItemsButton = new JButton("View Bill Items");

        // Set font and background color for buttons
        JButton[] buttons = {addBillButton, viewBillsButton, addProductButton, addCustomerButton, viewBillItemsButton};
        for (JButton button : buttons) {
            button.setFont(new Font("Arial", Font.BOLD, 16)); // Button font
            button.setBackground(new Color(76, 175, 80)); // Green background color
            button.setForeground(Color.WHITE); // White text color
            button.setFocusable(false); // Remove focus outline
            button.setBorderPainted(false); // Remove border
        }

        // Set action listeners for CRUD operations
        addBillButton.addActionListener(e -> addBill());
        viewBillsButton.addActionListener(e -> viewBills());
        addProductButton.addActionListener(e -> addProduct());
        addCustomerButton.addActionListener(e -> addCustomer());
        viewBillItemsButton.addActionListener(e -> viewBillItems());
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
        add(userDashboardLabel, gbc);
        
        // User Details
        gbc.gridy = 1;
        add(userDetailsLabel, gbc);
        
        gbc.gridy = 2;
        add(userInfo, gbc);

        // User Role
        gbc.gridy = 3; // Position below user info
        add(userRoleLabel, gbc); // Add user role label

        // CRUD Buttons
        gbc.gridwidth = 1; // Reset grid width for buttons
        gbc.gridy = 4; // Position for buttons

        gbc.gridx = 0;
        add(addBillButton, gbc);
        
        gbc.gridx = 1;
        add(viewBillsButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(addProductButton, gbc);
        
        gbc.gridx = 1;
        add(addCustomerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6; // Update grid y position for viewBillItemsButton
        gbc.gridwidth = 2; // Span across two columns
        add(viewBillItemsButton, gbc);
    }
    
    private void addBill() {
        SwingUtilities.invokeLater(() -> {
            UserBillForm billForm = new UserBillForm();
            billForm.setVisible(true); // Open BillForm
            billForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    UserDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void viewBills() {
        SwingUtilities.invokeLater(() -> {
            BillListView billListView = new BillListView();
            billListView.setVisible(true); // Open BillListView
            billListView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    UserDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void addProduct() {
        SwingUtilities.invokeLater(() -> {
            UserProductForm productForm = new UserProductForm();
            productForm.setVisible(true); // Open ProductForm
            productForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    UserDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void addCustomer() {
        SwingUtilities.invokeLater(() -> {
            UserCustomerForm customerForm = new UserCustomerForm();
            customerForm.setVisible(true); // Open CustomerForm
            customerForm.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    UserDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }

    private void viewBillItems() {
        SwingUtilities.invokeLater(() -> {
            UserBillItemForm bif = new UserBillItemForm();
            bif.setVisible(true); // Open BillListView
            bif.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    UserDashboard.this.setVisible(true); // Show dashboard again
                }
            });
            this.setVisible(false); // Hide the dashboard
        });
    }
}
