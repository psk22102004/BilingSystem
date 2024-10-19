package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.BillItemDAO;
import com.mycompany.billingsysswing.models.BillItem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BillItemForm extends JFrame {

    private JTextField billIdField;
    private JTextField productIdField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField totalPriceField; // New field for total price
    private JTextField gstAmountField; // New field for GST amount
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton listButton;

    private JTable billItemTable;
    private DefaultTableModel tableModel;
    private BillItemDAO billItemDAO;

    public BillItemForm() {
        setTitle("Bill Item Form");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        billItemDAO = new BillItemDAO();

        // Create UI components
        createUIComponents();

        // Layout
        setLayout(new BorderLayout());

        // Heading Panel
        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Bill Item Management");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingPanel.add(headingLabel);
        add(headingPanel, BorderLayout.NORTH);

        // Add table to the frame
        add(new JScrollPane(billItemTable), BorderLayout.CENTER);

        // Panel for input fields and buttons (arranged using GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding labels and fields in a 3-column layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Bill ID:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(billIdField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(productIdField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(priceField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Total Price:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(totalPriceField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(new JLabel("GST Amount:"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(gstAmountField, gbc);

        // Place buttons below the form fields
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(listButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);

        // Add formPanel at the bottom of the BorderLayout
        add(formPanel, BorderLayout.SOUTH);
    }

    private void createUIComponents() {
        // Define the preferred size for the text fields (uniform size for all)
        Dimension fieldSize = new Dimension(200, 30); // You can adjust the size as needed

        billIdField = new JTextField();
        billIdField.setPreferredSize(fieldSize); // Set preferred size

        productIdField = new JTextField();
        productIdField.setPreferredSize(fieldSize); // Set preferred size

        quantityField = new JTextField();
        quantityField.setPreferredSize(fieldSize); // Set preferred size

        priceField = new JTextField();
        priceField.setPreferredSize(fieldSize); // Set preferred size

        totalPriceField = new JTextField(); // Initialize total price field
        totalPriceField.setPreferredSize(fieldSize); // Set preferred size

        gstAmountField = new JTextField(); // Initialize GST amount field
        gstAmountField.setPreferredSize(fieldSize); // Set preferred size

        addButton = new JButton("Add Item");
        updateButton = new JButton("Update Item");
        deleteButton = new JButton("Delete Item");
        listButton = new JButton("List Items");

        // Initialize the table model and JTable
        String[] columnNames = {"Bill Item ID", "Bill ID", "Product ID", "Quantity", "Price", "Total Price", "GST Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billItemTable = new JTable(tableModel);

        // Action listeners
        addButton.addActionListener(e -> addBillItem());
        updateButton.addActionListener(e -> updateBillItem());
        deleteButton.addActionListener(e -> deleteBillItem());
        listButton.addActionListener(e -> listBillItems());

        // Load existing bill items into the table
        loadBillItems();
    }

    private void loadBillItems() {
        try {
            List<BillItem> billItems = billItemDAO.listBillItems();
            tableModel.setRowCount(0); // Clear existing rows
            for (BillItem billItem : billItems) {
                tableModel.addRow(new Object[]{
                        billItem.getBillItemId(),
                        billItem.getBillId(),
                        billItem.getProductId(),
                        billItem.getQuantity(),
                        billItem.getPrice(),
                        billItem.getTotalPrice(), // Add total price to the table
                        billItem.getGstAmount() // Add GST amount to the table
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading bill items: " + ex.getMessage());
        }
    }

    private void addBillItem() {
        try {
            int billId = Integer.parseInt(billIdField.getText());
            int productId = Integer.parseInt(productIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            BigDecimal price = new BigDecimal(priceField.getText());
            BigDecimal totalPrice = new BigDecimal(totalPriceField.getText()); // Get total price
            BigDecimal gstAmount = new BigDecimal(gstAmountField.getText()); // Get GST amount

            BillItem billItem = new BillItem(0, billId, productId, quantity, price, totalPrice, gstAmount);
            int billItemId = billItemDAO.addBillItem(billItem);
            if (billItemId > 0) {
                JOptionPane.showMessageDialog(this, "Bill item added successfully! ID: " + billItemId);
                loadBillItems(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add bill item.");
            }
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateBillItem() {
        // Implementation for updating a bill item can go here.
        // Similar to addBillItem but with updating the existing bill item.
    }

    private void deleteBillItem() {
        try {
            int billItemId = Integer.parseInt(JOptionPane.showInputDialog("Enter Bill Item ID to delete:"));
            billItemDAO.deleteBillItem(billItemId);
            JOptionPane.showMessageDialog(this, "Bill item deleted successfully!");
            loadBillItems(); // Refresh table
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void listBillItems() {
        // The table view now acts as the list of bill items.
        // You may choose to keep this method for additional functionalities in the future.
        loadBillItems();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillItemForm().setVisible(true));
    }
}
