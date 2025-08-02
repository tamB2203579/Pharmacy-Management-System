package com.example.pharmacy;

import java.text.DecimalFormat;

public class RevenueAnalyticsData {
    private String period;
    private Integer revenue;
    private Integer transactions;
    private Double growthRate;
    private Double percentage;
    
    private static final DecimalFormat currencyFormat = new DecimalFormat("#,###");
    private static final DecimalFormat percentFormat = new DecimalFormat("#.##");

    public RevenueAnalyticsData(String period, Integer revenue, Integer transactions, Double growthRate, Double percentage) {
        this.period = period;
        this.revenue = revenue;
        this.transactions = transactions;
        this.growthRate = growthRate;
        this.percentage = percentage;
    }

    // Getters
    public String getPeriod() {
        return period;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public Integer getTransactions() {
        return transactions;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public Double getPercentage() {
        return percentage;
    }
    
    public Integer getAvgTransaction() {
        return transactions > 0 ? revenue / transactions : 0;
    }

    // Formatted getters for display
    public String getFormattedRevenue() {
        return currencyFormat.format(revenue) + " VND";
    }
    
    public String getFormattedAvgTransaction() {
        return currencyFormat.format(getAvgTransaction()) + " VND";
    }
    
    public String getFormattedGrowth() {
        if (growthRate == null || growthRate == 0) return "N/A";
        return percentFormat.format(growthRate) + "%";
    }
    
    public String getFormattedPercentage() {
        return percentFormat.format(percentage) + "%";
    }

    // Setters
    public void setPeriod(String period) {
        this.period = period;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public void setTransactions(Integer transactions) {
        this.transactions = transactions;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
