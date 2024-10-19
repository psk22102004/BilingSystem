package com.mycompany.billingsysswing.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bill {
    private int billId;
    private LocalDate billDate; // Ensure this is LocalDate
    private int customerId;
    private BigDecimal totalAmount;
    private BigDecimal gstAmount; // New field for GST
    private BigDecimal discountAmount; // New field for discount
    private String status; // New field for bill status

    // Constructor
    public Bill(int billId, LocalDate billDate, int customerId, BigDecimal totalAmount, BigDecimal gstAmount, BigDecimal discountAmount, String status) {
        this.billId = billId;
        this.billDate = billDate;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.gstAmount = gstAmount;
        this.discountAmount = discountAmount;
        this.status = status;
    }

    // Getters and Setters
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getGstAmount() {
        return gstAmount;
    }

    public void setGstAmount(BigDecimal gstAmount) {
        this.gstAmount = gstAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
