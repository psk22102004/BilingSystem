package com.mycompany.billingsysswing.daos;

import com.mycompany.billingsysswing.models.BillItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillItemDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/BillManagement";
    private final String jdbcUsername = "root"; // your DB username
    private final String jdbcPassword = "thinkpad"; // your DB password

    // Establishes a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Create a new bill item
    public int addBillItem(BillItem billItem) throws SQLException {
        String sql = "INSERT INTO bill_items (bill_id, product_id, quantity, price, total_price, gst_amount) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, billItem.getBillId());
            statement.setInt(2, billItem.getProductId());
            statement.setInt(3, billItem.getQuantity());
            statement.setBigDecimal(4, billItem.getPrice());
            statement.setBigDecimal(5, billItem.getTotalPrice()); // Set total price
            statement.setBigDecimal(6, billItem.getGstAmount());   // Set GST amount
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Get the generated bill item ID
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated bill item ID
                    }
                }
            }
        }
        return -1; // Return -1 if the insert failed
    }

    // Get a bill item by ID
    public BillItem getBillItem(int billItemId) throws SQLException {
        BillItem billItem = null;
        String sql = "SELECT * FROM bill_items WHERE bill_item_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billItemId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                billItem = new BillItem(
                        resultSet.getInt("bill_item_id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getBigDecimal("total_price"), // Retrieve total price
                        resultSet.getBigDecimal("gst_amount")   // Retrieve GST amount
                );
            }
        }
        return billItem;
    }

    // List all bill items
    public List<BillItem> listBillItems() throws SQLException {
        List<BillItem> billItems = new ArrayList<>();
        String sql = "SELECT * FROM bill_items";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                BillItem billItem = new BillItem(
                        resultSet.getInt("bill_item_id"),
                        resultSet.getInt("bill_id"),
                        resultSet.getInt("product_id"),
                        resultSet.getInt("quantity"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getBigDecimal("total_price"), // Retrieve total price
                        resultSet.getBigDecimal("gst_amount")   // Retrieve GST amount
                );
                billItems.add(billItem);
            }
        }
        return billItems;
    }

    // Update bill item
    public void updateBillItem(BillItem billItem) throws SQLException {
        String sql = "UPDATE bill_items SET bill_id = ?, product_id = ?, quantity = ?, price = ?, total_price = ?, gst_amount = ? WHERE bill_item_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billItem.getBillId());
            statement.setInt(2, billItem.getProductId());
            statement.setInt(3, billItem.getQuantity());
            statement.setBigDecimal(4, billItem.getPrice());
            statement.setBigDecimal(5, billItem.getTotalPrice()); // Update total price
            statement.setBigDecimal(6, billItem.getGstAmount());   // Update GST amount
            statement.setInt(7, billItem.getBillItemId());
            statement.executeUpdate();
        }
    }

    // Delete bill item
    public void deleteBillItem(int billItemId) throws SQLException {
        String sql = "DELETE FROM bill_items WHERE bill_item_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billItemId);
            statement.executeUpdate();
        }
    }
}
