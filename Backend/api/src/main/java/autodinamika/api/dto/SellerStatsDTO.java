package com.autodinamikasystem.dto;

public class SellerStatsDTO {
    private String sellerName;
    private long totalSales;
    private double totalRevenue;
    private double totalCommission;

    public SellerStatsDTO(String sellerName, long totalSales, double totalRevenue, double totalCommission) {
        this.sellerName = sellerName;
        this.totalSales = totalSales;
        this.totalRevenue = totalRevenue;
        this.totalCommission = totalCommission;
    }

    public String getSellerName() {
        return sellerName;
    }

    public long getTotalSales() {
        return totalSales;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public double getTotalCommission() {
        return totalCommission;
    }
}
