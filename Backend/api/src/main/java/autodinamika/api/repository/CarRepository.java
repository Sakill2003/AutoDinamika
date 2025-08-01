package autodinamika.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;

public interface CarRepository extends JpaRepository<Car, Long> {
    int countByCarModelAndStatus(CarModel carModel, String status);
}