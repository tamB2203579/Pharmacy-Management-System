package com.example.pharmacy;

public class medicineData {
    private String medicineId;
    private String productName;
    private String category;
    private Integer quantity;
    private Integer price;
    private String status;

    public medicineData(String medicineId, String productName, String category, Integer quantity, Integer price, String status){
        this.medicineId = medicineId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public String getMedicineId(){
        return medicineId;
    }

    public String getProductName(){
        return productName;
    }

    public String getCategory(){
        return category;
    }

    public Integer getQuantity(){
        return quantity;
    }

    public Integer getPrice(){
        return price;
    }

    public String getStatus(){
        return status;
    }

}
