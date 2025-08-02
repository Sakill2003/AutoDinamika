package autodinamika.api.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "carmodel")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;

    private String brand;
    private String model;
    private int year;
    private String color;
    private Double price;
    private String imgf;
    private String imgs;
    private String imgt;

    public Long getModelId() {
        return this.modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

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

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
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

    @OneToMany(mappedBy = "carModel")
    private List<Car> cars;
}

