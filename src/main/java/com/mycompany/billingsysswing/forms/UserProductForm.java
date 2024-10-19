package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.ProductDAO;
import com.mycompany.billingsysswing.models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class UserProductForm extends JFrame {
    private JTextField nameField, priceField, descriptionField, SKUField, GSTRateField, stockQuantityField;
    private JComboBox<String> statusComboBox;
    private JButton saveButton, clearButton;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private ProductDAO productDAO;

    public UserProductForm() {
        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 600);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center the frame on screen

        productDAO = new ProductDAO();

        // Heading
        JLabel headingLabel = new JLabel("Product Management", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(new Color(72, 187, 120)); // Custom color for heading
        add(headingLabel, BorderLayout.NORTH);

        // Table for displaying products
        String[] columnNames = {"Product ID", "Name", "Description", "Price", "SKU", "Status", "GST Rate", "Stock Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && productTable.getSelectedRow() != -1) {
                loadSelectedProduct();
            }
        });

        // Load products into the table
        loadProducts();

        // Scrollable panel for the product table
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        // Horizontal line separator
        JSeparator separator = new JSeparator();
        add(separator, BorderLayout.SOUTH);

        createInputPanel(); // Create and add input panel

        setVisible(true);
    }

    private void createInputPanel() {
        // Panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the panel

        inputPanel.add(new JLabel("Product Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("SKU:"));
        SKUField = new JTextField();
        inputPanel.add(SKUField);

        inputPanel.add(new JLabel("Status:"));
        String[] statusOptions = {"active", "inactive", "discontinued"};
        statusComboBox = new JComboBox<>(statusOptions);
        inputPanel.add(statusComboBox);

        inputPanel.add(new JLabel("GST Rate:"));
        GSTRateField = new JTextField();
        inputPanel.add(GSTRateField);

        inputPanel.add(new JLabel("Stock Quantity:"));
        stockQuantityField = new JTextField();
        inputPanel.add(stockQuantityField);

        // Buttons
        saveButton = new JButton("Add Product");
        saveButton.addActionListener(e -> saveProduct());
        inputPanel.add(saveButton);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> clearFields());
        inputPanel.add(clearButton);

        // Add input panel to the bottom of the frame
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void loadProducts() {
        try {
            List<Product> products = productDAO.listProducts();
            for (Product product : products) {
                Object[] row = {
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductDescription(),
                    product.getPrice(),
                    product.getSKU(),
                    product.getStatus(),
                    product.getGSTRate(),
                    product.getStockQuantity()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading products.");
        }
    }

    private void loadSelectedProduct() {
        // No need to load selected product since users can't update or delete
    }

    private void clearFields() {
        nameField.setText("");
        descriptionField.setText("");
        priceField.setText("");
        SKUField.setText("");
        statusComboBox.setSelectedIndex(0);
        GSTRateField.setText("");
        stockQuantityField.setText("");
        productTable.clearSelection();
    }

    private void saveProduct() {
        String name = nameField.getText();
        String priceStr = priceField.getText();
        String description = descriptionField.getText();
        String SKU = SKUField.getText();
        String status = (String) statusComboBox.getSelectedItem();
        String GSTRateStr = GSTRateField.getText();
        String stockQuantityStr = stockQuantityField.getText();

        if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty() || SKU.isEmpty() || GSTRateStr.isEmpty() || stockQuantityStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        BigDecimal price, GSTRate;
        int stockQuantity;
        try {
            price = new BigDecimal(priceStr);
            GSTRate = new BigDecimal(GSTRateStr);
            stockQuantity = Integer.parseInt(stockQuantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format.");
            return;
        }

        Product product = new Product(name, description, price, SKU, status, GSTRate, stockQuantity);
        try {
            int id = productDAO.addProduct(product);
            if (id != -1) {
                JOptionPane.showMessageDialog(this, "Product added successfully.");
                Object[] row = {id, name, description, price, SKU, status, GSTRate, stockQuantity};
                tableModel.addRow(row);
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Error adding product.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving product.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserProductForm::new);
    }
}
