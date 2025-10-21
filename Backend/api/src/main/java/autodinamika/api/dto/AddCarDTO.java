package autodinamika.api.dto;


public class AddCarDTO {
    private String brand;
    private String model;
    private int year;
    private String chassisNumber;
    private String engineNumber;
    private Double price;
    private String color;
    private String imgf;
    private String imgs;
    private String imgt;

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getChassisNumber() {
        return this.chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public String getEngineNumber() {
        return this.engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImgf() {
        return this.imgf;
    }

    public void setImgf(String imgf) {
        this.imgf = imgf;
    }

    public String getImgs() {
        return this.imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getImgt() {
        return this.imgt;
    }

    public void setImgt(String imgt) {
        this.imgt = imgt;
    }

}
