package autodinamika.api.dto;

public class CarModelDTO {
    private String brand;
    private String model;
    private int year;
    private String color;
    private int price;
    private String imgf;
    private String imgs;
    private String imgt;
    private int availableUnits;

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

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    public int getAvailableUnits() {
        return this.availableUnits;
    }

    public void setAvailableUnits(int availableUnits) {
        this.availableUnits = availableUnits;
    }

    
}