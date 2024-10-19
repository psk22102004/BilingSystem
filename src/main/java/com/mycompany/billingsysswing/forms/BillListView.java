package com.mycompany.billingsysswing.forms;

import com.mycompany.billingsysswing.daos.BillDAO;
import com.mycompany.billingsysswing.models.Bill;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class BillListView extends JFrame {

    private JTable billTable;
    private DefaultTableModel tableModel;
    private BillDAO billDAO;

    public BillListView() {
        setTitle("Bill List");
        setSize(1200, 600); // Set window size to 1200x600
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        billDAO = new BillDAO();

        // Create UI components
        createUIComponents();

        // Set layout
        setLayout(new BorderLayout());

        // Heading Panel
        JPanel headingPanel = new JPanel();
        JLabel headingLabel = new JLabel("Billing System - List of Bills");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingPanel.add(headingLabel);
        add(headingPanel, BorderLayout.NORTH);

        // Add table to the center of the frame
        add(new JScrollPane(billTable), BorderLayout.CENTER);

        // Load bills into the table
        loadBills();
    }

    private void createUIComponents() {
        // Initialize the table model and JTable
        String[] columnNames = {"Bill ID", "Bill Date", "Customer ID", "Total Amount", "GST Amount", "Discount Amount", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        billTable = new JTable(tableModel);

        // Set column widths for better appearance
        billTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // Bill ID
        billTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Bill Date
        billTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Customer ID
        billTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Total Amount
        billTable.getColumnModel().getColumn(4).setPreferredWidth(100); // GST Amount
        billTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Discount Amount
        billTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Status
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BillListView().setVisible(true));
    }
}
