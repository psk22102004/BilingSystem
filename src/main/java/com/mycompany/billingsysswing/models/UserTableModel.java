/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.billingsysswing.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private List<User> users;
    private final String[] columnNames = {"ID", "Username", "Email", "Role"};

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> user.getUserId();
            case 1 -> user.getUsername();
            case 2 -> user.getEmail();
            case 3 -> user.getRole();
            default -> null;
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Define which cells are editable if needed
        return columnIndex != 0; // Example: ID is not editable
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 1 -> user.setUsername((String) aValue);
            case 2 -> user.setEmail((String) aValue);
            case 3 -> user.setRole((String) aValue);
        }
        fireTableCellUpdated(rowIndex, columnIndex); // Notify that a cell has been updated
    }

    public void setUsers(List<User> users) {
        this.users = users;
        fireTableDataChanged(); // Notify that the data has changed
    }

    public User getUserAt(int rowIndex) {
        return users.get(rowIndex);
    }
}
