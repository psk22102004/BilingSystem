package com.mycompany.billingsysswing.models;

import java.math.BigDecimal;

public class BillItem {
    private int billItemId;
    private int billId;
    private int productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice; // New field for total price
    private BigDecimal gstAmount;   // New field for GST amount

    public BillItem(int billItemId, int billId, int productId, int quantity, BigDecimal price, BigDecimal totalPrice, BigDecimal gstAmount) {
        this.billItemId = billItemId;
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.gstAmount = gstAmount;
    }

    // Getters and Setters
    public int getBillItemId() { return billItemId; }
    public void setBillItemId(int billItemId) { this.billItemId = billItemId; }
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public BigDecimal getTotalPrice() { return totalPrice; }  // New Getter
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; } // New Setter
    public BigDecimal getGstAmount() { return gstAmount; } // New Getter
    public void setGstAmount(BigDecimal gstAmount) { this.gstAmount = gstAmount; } // New Setter
}
