package autodinamika.api.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private CarModel carModel;

    private String chassisNumber;
    private String engineNumber;

    private String status; // 'available' or 'sold'
}