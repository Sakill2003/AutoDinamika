package autodinamika.api.dto;

import java.util.List;

public class ManagerStatsDTO {
    private int totalSales;
    private double totalCollected;
    private double earnings;

    private List<RankedItemDTO> topSellers;
    private List<RankedItemDTO> topBrands;
    private List<RankedItemDTO> topModels;

    public int getTotalSales() {
        return this.totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public double getTotalCollected() {
        return this.totalCollected;
    }

    public void setTotalCollected(double totalCollected) {
        this.totalCollected = totalCollected;
    }

    public double getEarnings() {
        return this.earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public List<RankedItemDTO> getTopSellers() {
        return this.topSellers;
    }

    public void setTopSellers(List<RankedItemDTO> topSellers) {
        this.topSellers = topSellers;
    }

    public List<RankedItemDTO> getTopBrands() {
        return this.topBrands;
    }

    public void setTopBrands(List<RankedItemDTO> topBrands) {
        this.topBrands = topBrands;
    }

    public List<RankedItemDTO> getTopModels() {
        return this.topModels;
    }

    public void setTopModels(List<RankedItemDTO> topModels) {
        this.topModels = topModels;
    }


}
