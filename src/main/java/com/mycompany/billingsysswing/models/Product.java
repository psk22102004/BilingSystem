package com.mycompany.billingsysswing.models;

import java.math.BigDecimal;

public class Product {
    private int productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private String SKU; // Unique stock keeping unit
    private String status; // 'active', 'inactive', 'discontinued'
    private BigDecimal GSTRate; // GST rate
    private int stockQuantity; // Quantity in stock

    // Constructor for existing products
    public Product(int productId, String productName, String productDescription, BigDecimal price, String SKU, String status, BigDecimal GSTRate, int stockQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.SKU = SKU;
        this.status = status;
        this.GSTRate = GSTRate;
        this.stockQuantity = stockQuantity;
    }

    // Constructor for new products
    public Product(String productName, String productDescription, BigDecimal price, String SKU, String status, BigDecimal GSTRate, int stockQuantity) {
        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.SKU = SKU;
        this.status = status;
        this.GSTRate = GSTRate;
        this.stockQuantity = stockQuantity;
    }

    // Getters and Setters
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getSKU() { return SKU; }
    public void setSKU(String SKU) { this.SKU = SKU; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getGSTRate() { return GSTRate; }
    public void setGSTRate(BigDecimal GSTRate) { this.GSTRate = GSTRate; }
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }
}
