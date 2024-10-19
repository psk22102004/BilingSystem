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

public class CustomerForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField dobField; // New field for date of birth
    private JTextField statusField; // New field for status
    private JTextField loyaltyPointsField; // New field for loyalty points
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO;

    public CustomerForm() {
        setTitle("Customer Form");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        customerDAO = new CustomerDAO();
        
        String[] columnNames = {"Customer ID", "Name", "Email", "Phone", "Address", "DOB", "Status", "Loyalty Points"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && customerTable.getSelectedRow() != -1) {
                    loadSelectedCustomer();
                }
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

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> deleteCustomer());
        inputPanel.add(deleteButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.SOUTH); // Input panel at the bottom

        setVisible(true);
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerDAO.listCustomers();
            for (Customer customer : customers) {
                Object[] row = {
                    customer.getCustomerId(),
                    customer.getName(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getAddress(),
                    customer.getDob(), // Add DOB to row
                    customer.getStatus(), // Add status to row
                    customer.getLoyaltyPoints() // Add loyalty points to row
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customers.");
        }
    }

    private void loadSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            int customerId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                Customer customer = customerDAO.getCustomer(customerId);
                if (customer != null) {
                    nameField.setText(customer.getName());
                    emailField.setText(customer.getEmail());
                    phoneField.setText(customer.getPhone());
                    addressField.setText(customer.getAddress());
                    dobField.setText(customer.getDob().toString()); // Set DOB field
                    statusField.setText(customer.getStatus()); // Set status field
                    loyaltyPointsField.setText(String.valueOf(customer.getLoyaltyPoints())); // Set loyalty points
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading customer details.");
            }
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
            if (customerTable.getSelectedRow() == -1) {
                int id = customerDAO.addCustomer(customer);
                if (id != -1) {
                    JOptionPane.showMessageDialog(this, "Customer added successfully.");
                    Object[] row = {id, name, email, phone, address, dob, status, loyaltyPoints};
                    tableModel.addRow(row);
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Error adding customer.");
                }
            } else {
                int customerId = (int) tableModel.getValueAt(customerTable.getSelectedRow(), 0);
                customer.setCustomerId(customerId);
                customerDAO.updateCustomer(customer);
                JOptionPane.showMessageDialog(this, "Customer updated successfully.");
                updateTableRow(customerId, customer);
                clearFields();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving customer.");
        }
    }

    private void updateTableRow(int customerId, Customer customer) {
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(customerId)) {
                tableModel.setValueAt(customer.getName(), i, 1);
                tableModel.setValueAt(customer.getEmail(), i, 2);
                tableModel.setValueAt(customer.getPhone(), i, 3);
                tableModel.setValueAt(customer.getAddress(), i, 4);
                tableModel.setValueAt(customer.getDob(), i, 5); // Update DOB
                tableModel.setValueAt(customer.getStatus(), i, 6); // Update status
                tableModel.setValueAt(customer.getLoyaltyPoints(), i, 7); // Update loyalty points
                break;
            }
        }
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow != -1) {
            int customerId = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                customerDAO.deleteCustomer(customerId);
                JOptionPane.showMessageDialog(this, "Customer deleted successfully.");
                tableModel.removeRow(selectedRow);
                clearFields();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting customer.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No customer selected.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerForm::new);
    }
}