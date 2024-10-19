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
        setSize(800, 600);
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

        // Separator Line
        JSeparator separator = new JSeparator();
        add(separator, BorderLayout.SOUTH);

        // Panel for input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10)); // Adjusted rows for new fields
        inputPanel.add(new JLabel("Bill Date (YYYY-MM-DD):"));
        inputPanel.add(billDateField);
        inputPanel.add(new JLabel("Customer ID:"));
        inputPanel.add(customerIdField);
        inputPanel.add(new JLabel("Total Amount:"));
        inputPanel.add(totalAmountField);
        inputPanel.add(new JLabel("GST Amount:")); // New label for GST
        inputPanel.add(gstAmountField); // New field for GST input
        inputPanel.add(new JLabel("Discount Amount:")); // New label for discount
        inputPanel.add(discountAmountField); // New field for discount input
        inputPanel.add(new JLabel("Status:")); // New label for status
        inputPanel.add(statusField); // New field for status input
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(listButton);

        // Add input panel at the bottom
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void createUIComponents() {
        billDateField = new JTextField();
        customerIdField = new JTextField();
        totalAmountField = new JTextField();
        gstAmountField = new JTextField(); // Initialize GST field
        discountAmountField = new JTextField(); // Initialize discount field
        statusField = new JTextField(); // Initialize status field

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
                        bill.getGstAmount(), // Add GST amount to the table
                        bill.getDiscountAmount(), // Add discount amount to the table
                        bill.getStatus() // Add bill status to the table
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
            BigDecimal gstAmount = new BigDecimal(gstAmountField.getText()); // Get GST amount
            BigDecimal discountAmount = new BigDecimal(discountAmountField.getText()); // Get discount amount
            String status = statusField.getText(); // Get status

            Bill bill = new Bill(0, billDate, customerId, totalAmount, gstAmount, discountAmount, status);
            int billId = billDAO.addBill(bill);
            if (billId > 0) {
                JOptionPane.showMessageDialog(this, "Bill added successfully! ID: " + billId);
                loadBills(); // Refresh table
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add bill.");
            }
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateBill() {
        // Implementation for updating a bill can go here.
        // It would be similar to addBill but would include finding the bill by ID.
    }

    private void deleteBill() {
        try {
            int billId = Integer.parseInt(JOptionPane.showInputDialog("Enter Bill ID to delete:"));
            billDAO.deleteBill(billId);
            JOptionPane.showMessageDialog(this, "Bill deleted successfully!");
            loadBills(); // Refresh table
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void listBills() {
        // The table view now acts as the list of bills.
        // You may choose to keep this method for additional functionalities in the future.
        loadBills();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillForm().setVisible(true));
    }
}
