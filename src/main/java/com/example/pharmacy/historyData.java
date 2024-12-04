package com.example.pharmacy;

import java.util.Date;

public class historyData {
    private Integer id;
    private Integer customerId;
    private String customerName;
    private String staffName;
    private Integer total;
    private Date createdDate;

    public historyData(Integer id, Integer customerId, String customerName, String staffName, Integer total, Date createdDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.staffName = staffName;
        this.total = total;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getStaffName() {
        return staffName;
    }

    public Integer getTotal() {
        return total;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
