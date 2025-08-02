package com.example.pharmacy;

public class invoiceDetailData {
    private Integer id;
    private Integer historyId;
    private String medicineId;
    private String productName;
    private String category;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    public invoiceDetailData(Integer id, Integer historyId, String medicineId, String productName, 
                           String category, Integer quantity, Integer unitPrice, Integer totalPrice) {
        this.id = id;
        this.historyId = historyId;
        this.medicineId = medicineId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public Integer getId() {
        return id;
    }

    public Integer getHistoryId() {
        return historyId;
    }

    public String getMedicineId() {
        return medicineId;
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

    public Integer getUnitPrice() {
        return unitPrice;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }
}
