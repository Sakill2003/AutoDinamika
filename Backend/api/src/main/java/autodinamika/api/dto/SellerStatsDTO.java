package autodinamika.api.dto;

import java.util.List;

public class SellerStatsDTO {

    private String fullName;
    private String birthDate;
    private int age;
    private String phone;
    private String email;
    private String address;

    private double totalCommissions;
    private int totalSales;

    private List<SaleInfoDTO> latestSales;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalCommissions() {
        return this.totalCommissions;
    }

    public void setTotalCommissions(double totalCommissions) {
        this.totalCommissions = totalCommissions;
    }

    public int getTotalSales() {
        return this.totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public List<SaleInfoDTO> getLatestSales() {
        return this.latestSales;
    }

    public void setLatestSales(List<SaleInfoDTO> latestSales) {
        this.latestSales = latestSales;
    }


}
