package com.mycompany.billingsysswing.daos;

import com.mycompany.billingsysswing.models.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/BillManagement";
    private final String jdbcUsername = "root"; // your DB username
    private final String jdbcPassword = "thinkpad"; // your DB password

    // Establishes a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Create a new customer
    public int addCustomer(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (name, email, phone, address, dob, status, loyalty_points) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setDate(5, customer.getDob()); // Set the dob
            statement.setString(6, customer.getStatus()); // Set the status
            statement.setInt(7, customer.getLoyaltyPoints()); // Set the loyalty points

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Get the generated customer ID
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated customer ID
                    }
                }
            }
        }
        return -1; // Return -1 if the insert failed
    }

    // Get a customer by ID
    public Customer getCustomer(int customerId) throws SQLException {
        Customer customer = null;
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getDate("dob"), // Retrieve the dob
                        resultSet.getString("status"), // Retrieve the status
                        resultSet.getInt("loyalty_points") // Retrieve the loyalty points
                );
            }
        }
        return customer;
    }

    // List all customers
    public List<Customer> listCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address"),
                        resultSet.getDate("dob"), // Retrieve the dob
                        resultSet.getString("status"), // Retrieve the status
                        resultSet.getInt("loyalty_points") // Retrieve the loyalty points
                );
                customers.add(customer);
            }
        }
        return customers;
    }

    // Update customer
    public void updateCustomer(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET name = ?, email = ?, phone = ?, address = ?, dob = ?, status = ?, loyalty_points = ? WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getEmail());
            statement.setString(3, customer.getPhone());
            statement.setString(4, customer.getAddress());
            statement.setDate(5, customer.getDob()); // Set the dob
            statement.setString(6, customer.getStatus()); // Set the status
            statement.setInt(7, customer.getLoyaltyPoints()); // Set the loyalty points
            statement.setInt(8, customer.getCustomerId());
            statement.executeUpdate();
        }
    }

    // Delete customer
    public void deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        }
    }
}
