package com.example.pharmacy;

public class purchaseData {
    private Integer customer_id;
    private String medicine_id;
    private String productName;
    private String category;
    private Integer quantity;
    private Integer price;

    public purchaseData(Integer customer_id, String medicine_id, String productName, String category, Integer quantity, Integer price) {
        this.customer_id = customer_id;
        this.medicine_id = medicine_id;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
    }
}
