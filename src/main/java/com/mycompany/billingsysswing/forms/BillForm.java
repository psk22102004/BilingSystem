package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.BillDAO;
import com.mycompany.billingsysswing.models.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BillForm extends JFrame {

    private JTextField billDateField;
    private JTextField customerIdField;
    private JTextField totalAmountField;
    private JTextField gstAmountField; // New field for GST amount
    private JTextField discountAmountField; // New field for discount amount
    private JTextField statusField; // New field for bill status
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton listButton;

    private JTable billTable;
    private DefaultTableModel tableModel;
    private BillDAO billDAO;

    public BillForm() {
        setTitle("Bill Form");
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        billDAO = new BillDAO();

        // Create UI components
        createUIComponents();

        // Layout
        setLayout(new BorderLayout());

        // Heading Panel
        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Billing System");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingPanel.add(headingLabel);
        add(headingPanel, BorderLayout.NORTH);

        // Add table to the frame
        add(new JScrollPane(billTable), BorderLayout.CENTER);

        // Panel for input fields and buttons (arranged using GridBagLayout)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add some padding
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Adding labels and fields in a 3-column layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Bill Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(billDateField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(customerIdField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Total Amount:"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(totalAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("GST Amount:"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(gstAmountField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Discount Amount:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(discountAmountField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(statusField, gbc);

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
        // Define the preferred size for the text fields
        Dimension fieldSize = new Dimension(200, 30);

        billDateField = new JTextField();
        billDateField.setPreferredSize(fieldSize);

        customerIdField = new JTextField();
        customerIdField.setPreferredSize(fieldSize);

        totalAmountField = new JTextField();
        totalAmountField.setPreferredSize(fieldSize);

        gstAmountField = new JTextField();
        gstAmountField.setPreferredSize(fieldSize);

        discountAmountField = new JTextField();
        discountAmountField.setPreferredSize(fieldSize);

        statusField = new JTextField();
        statusField.setPreferredSize(fieldSize);

        addButton = new JButton("Add Bill");
        updateButton = new JButton("Update Bill");
        deleteButton = new JButton("Delete Bill");
        listButton = new JButton("List Bills");

        // Initialize the table model and JTable
        String[] columnNames = {"Bill ID", "Bill Date", "Customer ID", "Total Amount", "GST Amount", "Discount Amount", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(tableModel);

        // Action listeners
        addButton.addActionListener(e -> addBill());
        updateButton.addActionListener(e -> updateBill());
        deleteButton.addActionListener(e -> deleteBill());
        listButton.addActionListener(e -> listBills());

        // Load existing bills into the table
        loadBills();
    }

    private void loadBills() {
        try {
            List<Bill> bills = billDAO.listBills();
            tableModel.setRowCount(0); // Clear existing rows
            for (Bill bill : bills) {
                tableModel.addRow(new Object[]{
                        bill.getBillId(),
                        bill.getBillDate(),
                        bill.getCustomerId(),
                        bill.getTotalAmount(),
                        bill.getGstAmount(),
                        bill.getDiscountAmount(),
                        bill.getStatus()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading bills: " + ex.getMessage());
        }
    }

    private void addBill() {
        try {
            LocalDate billDate = LocalDate.parse(billDateField.getText());
            int customerId = Integer.parseInt(customerIdField.getText());
            BigDecimal totalAmount = new BigDecimal(totalAmountField.getText());
            BigDecimal gstAmount = new BigDecimal(gstAmountField.getText());
            BigDecimal discountAmount = new BigDecimal(discountAmountField.getText());
            String status = statusField.getText();

            Bill bill = new Bill(0, billDate, customerId, totalAmount, gstAmount, discountAmount, status);
            int billId = billDAO.addBill(bill);
            if (billId > 0) {
                JOptionPane.showMessageDialog(this, "Bill added successfully! ID: " + billId);
                loadBills(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add bill.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateBill() {
        // Implementation for updating a bill can go here
        // Similar to addBill but with updating the existing bill
    }

    private void deleteBill() {
        try {
            int billId = Integer.parseInt(JOptionPane.showInputDialog("Enter Bill ID to delete:"));
            billDAO.deleteBill(billId);
            JOptionPane.showMessageDialog(this, "Bill deleted successfully!");
            loadBills(); // Refresh table
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void listBills() {
        loadBills(); // The table view acts as the list of bills
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillForm().setVisible(true));
    }
}
