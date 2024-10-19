/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.billingsysswing.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BillTableModel extends AbstractTableModel {
    private List<Bill> bills; // List of bills
    private String[] columnNames = {"ID", "Date", "Customer ID", "Total Amount"}; // Column names

    // Constructor
    public BillTableModel(List<Bill> bills) {
        this.bills = bills;
    }

    @Override
    public int getRowCount() {
        return bills.size(); // Returns the number of bills
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Returns the number of columns
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Bill bill = bills.get(rowIndex); // Get the bill at the specified row
        return switch (columnIndex) {
            case 0 -> bill.getBillId();
            case 1 -> bill.getBillDate();
            case 2 -> bill.getCustomerId();
            case 3 -> bill.getTotalAmount();
            default -> null;
        }; // ID
        // Date
        // Customer ID
        // Total Amount
        // Default case
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Return the name of the column
    }

    // Method to update the list of bills and notify listeners
    public void setBills(List<Bill> bills) {
        this.bills = bills;
        fireTableDataChanged(); // Notify the table that data has changed
    }

    // Optionally, add a method to get a bill at a specific row
    public Bill getBillAt(int rowIndex) {
        return bills.get(rowIndex);
    }
}
