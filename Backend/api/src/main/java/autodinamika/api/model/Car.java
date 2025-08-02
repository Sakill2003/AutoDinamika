package autodinamika.api.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "car")
public class Car {

    @Id
    @Column(name = "chasis")
    private String chassisNumber;

    @Column(name = "motor")
    private String engineNumber;

    private String status;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    public CarModel getCarModel() {
        return this.carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}