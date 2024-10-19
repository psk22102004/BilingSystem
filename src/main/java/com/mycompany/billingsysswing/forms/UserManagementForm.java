package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.UserDAO;
import com.mycompany.billingsysswing.models.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UserManagementForm extends JFrame {

    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField txtUsername, txtEmail, txtRole, txtFirstName, txtLastName;
    private JComboBox<String> cmbStatus; // Use a JComboBox for user status
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    private UserDAO userDAO;

    public UserManagementForm() {
        userDAO = new UserDAO();

        setTitle("User Management");
        setSize(1200, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.add(new JLabel("User Management", JLabel.CENTER));
        headerPanel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerPanel, BorderLayout.NORTH);

        // User table
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("User Details"));

        inputPanel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        inputPanel.add(txtUsername);

        inputPanel.add(new JLabel("First Name:"));
        txtFirstName = new JTextField();
        inputPanel.add(txtFirstName);

        inputPanel.add(new JLabel("Last Name:"));
        txtLastName = new JTextField();
        inputPanel.add(txtLastName);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Role:"));
        txtRole = new JTextField();
        inputPanel.add(txtRole);

        inputPanel.add(new JLabel("Status:")); // Change label to Status
        cmbStatus = new JComboBox<>(new String[]{"active", "inactive"}); // Status options
        inputPanel.add(cmbStatus);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        btnAdd = new JButton("Add User");
        btnUpdate = new JButton("Update User");
        btnDelete = new JButton("Delete User");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Main panel to hold input and button panels
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.EAST);

        // Initialize the table
        initializeTable();

        // Add action listeners for buttons
        btnAdd.addActionListener(e -> addUser());
        btnUpdate.addActionListener(e -> updateUser());
        btnDelete.addActionListener(e -> deleteUser());
        btnClear.addActionListener(e -> clearFields());

        // Handle table row click to populate input fields
        userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && userTable.getSelectedRow() != -1) {
                    populateFieldsFromSelectedRow();
                }
            }
        });
    }

    // Initialize the JTable with user data
    private void initializeTable() {
        String[] columnNames = {"User ID", "Username", "First Name", "Last Name", "Email", "Role", "Status"}; // Change to Status
        tableModel = new DefaultTableModel(columnNames, 0);
        userTable.setModel(tableModel);
        loadUsers();
    }

    // Load users from the database into the table
    private void loadUsers() {
        try {
            List<User> users = userDAO.listUsers();
            tableModel.setRowCount(0); // Clear existing rows
            for (User user : users) {
                Object[] row = {
                    user.getUserId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole(),
                    user.getStatus() // Use getStatus instead of isActive
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Add a new user
    private void addUser() {
        String username = txtUsername.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String role = txtRole.getText();
        String status = (String) cmbStatus.getSelectedItem(); // Get selected status

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User newUser = new User(username, "", email, role, firstName, lastName);
            newUser.setStatus(status);  // Set the status
            userDAO.addUser(newUser);
            loadUsers(); // Refresh the table
            clearFields();
            JOptionPane.showMessageDialog(this, "User added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Update selected user
    private void updateUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to update", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        String username = txtUsername.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String email = txtEmail.getText();
        String role = txtRole.getText();
        String status = (String) cmbStatus.getSelectedItem(); // Get selected status

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || role.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            User updatedUser = new User(userId, username, "", email, role, firstName, lastName, status, null, null, null); // Set status
            userDAO.updateUser(updatedUser);
            loadUsers(); // Refresh the table
            clearFields();
            JOptionPane.showMessageDialog(this, "User updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete selected user
    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);

        try {
            userDAO.deleteUser(userId);
            loadUsers(); // Refresh the table
            clearFields();
            JOptionPane.showMessageDialog(this, "User deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear input fields
    private void clearFields() {
        txtUsername.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtRole.setText("");
        cmbStatus.setSelectedItem("active"); // Reset to default status
    }

    // Populate input fields from selected row in the table
    private void populateFieldsFromSelectedRow() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow != -1) {
            txtUsername.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtFirstName.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtLastName.setText((String) tableModel.getValueAt(selectedRow, 3));
            txtEmail.setText((String) tableModel.getValueAt(selectedRow, 4));
            txtRole.setText((String) tableModel.getValueAt(selectedRow, 5));
            cmbStatus.setSelectedItem(tableModel.getValueAt(selectedRow, 6)); // Set the status from the selected row
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserManagementForm().setVisible(true));
    }
}
