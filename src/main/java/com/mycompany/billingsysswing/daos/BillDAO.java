package com.mycompany.billingsysswing.daos;

import com.mycompany.billingsysswing.models.Bill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/BillManagement";
    private final String jdbcUsername = "root"; // your DB username
    private final String jdbcPassword = "parthkadam22"; // your DB password

    // Establishes a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Create a new bill
    public int addBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO bills (bill_date, customer_id, total_amount, gst_amount, discount_amount, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, java.sql.Date.valueOf(bill.getBillDate())); // Convert LocalDate to java.sql.Date
            statement.setInt(2, bill.getCustomerId());
            statement.setBigDecimal(3, bill.getTotalAmount());
            statement.setBigDecimal(4, bill.getGstAmount());
            statement.setBigDecimal(5, bill.getDiscountAmount());
            statement.setString(6, bill.getStatus());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Get the generated bill ID
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated bill ID
                    }
                }
            }
        }
        return -1; // Return -1 if the insert failed
    }

    // Get a bill by ID
    public Bill getBill(int billId) throws SQLException {
        Bill bill = null;
        String sql = "SELECT * FROM bills WHERE bill_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bill = new Bill(
                        resultSet.getInt("bill_id"),
                        resultSet.getDate("bill_date").toLocalDate(),
                        resultSet.getInt("customer_id"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("gst_amount"), // Get GST amount
                        resultSet.getBigDecimal("discount_amount"), // Get discount amount
                        resultSet.getString("status") // Get status
                );
            }
        }
        return bill;
    }

    // List all bills
    public List<Bill> listBills() throws SQLException {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM bills";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Bill bill = new Bill(
                        resultSet.getInt("bill_id"),
                        resultSet.getDate("bill_date").toLocalDate(),
                        resultSet.getInt("customer_id"),
                        resultSet.getBigDecimal("total_amount"),
                        resultSet.getBigDecimal("gst_amount"), // Get GST amount
                        resultSet.getBigDecimal("discount_amount"), // Get discount amount
                        resultSet.getString("status") // Get status
                );
                bills.add(bill);
            }
        }
        return bills;
    }

    // Update bill
    public void updateBill(Bill bill) throws SQLException {
        String sql = "UPDATE bills SET bill_date = ?, customer_id = ?, total_amount = ?, gst_amount = ?, discount_amount = ?, status = ? WHERE bill_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(bill.getBillDate()));
            statement.setInt(2, bill.getCustomerId());
            statement.setBigDecimal(3, bill.getTotalAmount());
            statement.setBigDecimal(4, bill.getGstAmount());
            statement.setBigDecimal(5, bill.getDiscountAmount());
            statement.setString(6, bill.getStatus());
            statement.setInt(7, bill.getBillId());
            statement.executeUpdate();
        }
    }

    // Delete bill
    public void deleteBill(int billId) throws SQLException {
        String sql = "DELETE FROM bills WHERE bill_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            statement.executeUpdate();
        }
    }
}
