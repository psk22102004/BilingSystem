package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.CustomerDAO;
import com.mycompany.billingsysswing.models.Customer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UserCustomerForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField dobField; // New field for date of birth
    private JTextField statusField; // New field for status
    private JTextField loyaltyPointsField; // New field for loyalty points
    private JButton saveButton;
    private JButton clearButton;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO;

    public UserCustomerForm() {
        setTitle("User Customer Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLayout(new BorderLayout());

        customerDAO = new CustomerDAO();
        
        String[] columnNames = {"Customer ID", "Name", "Email", "Phone", "Address", "DOB", "Status", "Loyalty Points"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // No action needed on selection change, as updating is disabled.
            }
        });

        // Load customers into the table
        loadCustomers();

        // Add components to frame
        add(new JScrollPane(customerTable), BorderLayout.CENTER); // Customer table at the center

        // Panel for input fields at the bottom
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.add(new JLabel("Customer Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        inputPanel.add(addressField);

        inputPanel.add(new JLabel("Date of Birth:")); // New field label
        dobField = new JTextField(); // New field for DOB
        inputPanel.add(dobField);

        inputPanel.add(new JLabel("Status:")); // New field label
        statusField = new JTextField(); // New field for Status
        inputPanel.add(statusField);

        inputPanel.add(new JLabel("Loyalty Points:")); // New field label
        loyaltyPointsField = new JTextField(); // New field for Loyalty Points
        inputPanel.add(loyaltyPointsField);

        // Buttons
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveCustomer());
        inputPanel.add(saveButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.SOUTH); // Input panel at the bottom

        setVisible(true);
    }

    private void loadCustomers() {
    try {
        List<Customer> customers = customerDAO.listCustomers();  // Assuming this fetches customer IDs correctly
        for (Customer customer : customers) {
            Object[] row = {
                customer.getCustomerId(),  // Ensure this returns the ID
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getDob(),  // Add DOB to row
                customer.getStatus(),  // Add status to row
                customer.getLoyaltyPoints()  // Add loyalty points to row
            };
            tableModel.addRow(row);  // Adds the row to the table, including the Customer ID
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading customers.");
    }
}


    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        dobField.setText(""); // Clear DOB field
        statusField.setText(""); // Clear status field
        loyaltyPointsField.setText(""); // Clear loyalty points field
        customerTable.clearSelection();
    }

    private void saveCustomer() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        Date dob = Date.valueOf(dobField.getText()); // Convert String to Date
        String status = statusField.getText();
        int loyaltyPoints = Integer.parseInt(loyaltyPointsField.getText());

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || dobField.getText().isEmpty() || status.isEmpty() || loyaltyPointsField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        Customer customer = new Customer(name, email, phone, address, dob, status, loyaltyPoints);
        try {
            int id = customerDAO.addCustomer(customer);
            if (id != -1) {
                JOptionPane.showMessageDialog(this, "Customer added successfully.");
                Object[] row = {id, name, email, phone, address, dob, status, loyaltyPoints};
                tableModel.addRow(row);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding customer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving customer.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserCustomerForm::new);
    }
}
