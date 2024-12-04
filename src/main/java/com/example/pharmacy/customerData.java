package com.example.pharmacy;

import java.util.Date;

public class customerData {
    private Integer customerId;
    private String fullName;
    private String phoneNumber;
    private Date registrationDate;
    private double total;
    private Integer loyaltyPoints;

    public customerData(Integer customerId, String fullName, String phoneNumber, Date registrationDate, Integer loyaltyPoints) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
//        this.total = total;
        this.loyaltyPoints = loyaltyPoints;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

//    public double getTotal() {
//        return total;
//    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }
}
