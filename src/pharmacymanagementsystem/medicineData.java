/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacymanagementsystem;

import java.sql.Date;

/**
 *
 * @author PC
 */
public class medicineData {
    
    private Integer medicineId;
    private String productName;
    private String category;
    private String status;
    private int price;
    private Date date;
    
    public medicineData(Integer medicineId, String productName, String category, String status , int price, Date date){
        this.medicineId = medicineId;
        this.productName = productName;
        this.category = category;
        this.status = status;
        this.price = price;
        this.date = date;
    }
    
    public Integer getMedicineId(){
        return medicineId;
    }

    public String getProductName(){
        return productName;
    }
    
    public String getCategory(){
        return category;
    }
    public String getStatus(){
        return status;
    }
    
    public int getPrice(){
        return price;
    }
    
    public Date getDate(){
        return date;
    }
    
}
