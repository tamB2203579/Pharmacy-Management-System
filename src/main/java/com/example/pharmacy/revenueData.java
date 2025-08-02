package com.example.pharmacy;

import java.util.Date;

public class revenueData {
    private String period;
    private Integer total;
    private Date periodDate;
    private String displayLabel;

    public revenueData(String period, Integer total, Date periodDate, String displayLabel) {
        this.period = period;
        this.total = total;
        this.periodDate = periodDate;
        this.displayLabel = displayLabel;
    }

    public String getPeriod() {
        return period;
    }

    public Integer getTotal() {
        return total;
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public void setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
    }
}
