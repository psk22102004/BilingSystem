package com.mycompany.billingsysswing.daos;

import com.mycompany.billingsysswing.models.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/BillManagement";
    private final String jdbcUsername = "root"; // your DB username
    private final String jdbcPassword = "parthkadam22"; // your DB password

    // Establishes a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Create a new product
    public int addProduct(Product product) throws SQLException {
        String sql = "INSERT INTO products (product_name, product_description, price, SKU, status, GST_rate, stock_quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setString(4, product.getSKU());
            statement.setString(5, product.getStatus());
            statement.setBigDecimal(6, product.getGSTRate());
            statement.setInt(7, product.getStockQuantity());
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                // Get the generated product ID
                try (var generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated product ID
                    }
                }
            }
        }
        return -1; // Return -1 if the insert failed
    }

    // Get a product by ID
    public Product getProduct(int productId) throws SQLException {
        Product product = null;
        String sql = "SELECT * FROM products WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getString("SKU"),
                        resultSet.getString("status"),
                        resultSet.getBigDecimal("GST_rate"),
                        resultSet.getInt("stock_quantity")
                );
            }
        }
        return product;
    }

    // List all products
    public List<Product> listProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getInt("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getString("SKU"),
                        resultSet.getString("status"),
                        resultSet.getBigDecimal("GST_rate"),
                        resultSet.getInt("stock_quantity")
                );
                products.add(product);
            }
        }
        return products;
    }

    // Update product
    public void updateProduct(Product product) throws SQLException {
        String sql = "UPDATE products SET product_name = ?, product_description = ?, price = ?, SKU = ?, status = ?, GST_rate = ?, stock_quantity = ? WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getProductDescription());
            statement.setBigDecimal(3, product.getPrice());
            statement.setString(4, product.getSKU());
            statement.setString(5, product.getStatus());
            statement.setBigDecimal(6, product.getGSTRate());
            statement.setInt(7, product.getStockQuantity());
            statement.setInt(8, product.getProductId());
            statement.executeUpdate();
        }
    }

    // Delete product
    public void deleteProduct(int productId) throws SQLException {
        String sql = "DELETE FROM products WHERE product_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        }
    }
}
