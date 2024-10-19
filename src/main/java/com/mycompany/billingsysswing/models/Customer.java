package com.mycompany.billingsysswing.models;

import java.sql.Date;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Date dob; // New field for date of birth
    private String status; // New field for status (active/inactive)
    private int loyaltyPoints; // New field for loyalty points

    public Customer(int customerId, String name, String email, String phone, String address, Date dob, String status, int loyaltyPoints) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.status = status;
        this.loyaltyPoints = loyaltyPoints;
    }

    public Customer(String name, String email, String phone, String address, Date dob, String status, int loyaltyPoints) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.status = status;
        this.loyaltyPoints = loyaltyPoints;
    }

    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(int loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
}
