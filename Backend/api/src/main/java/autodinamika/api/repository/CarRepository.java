package autodinamika.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;

public interface CarRepository extends JpaRepository<Car,String> {
    int countByCarModelAndStatus(CarModel carModel, String status);
    List<Car> findByStatus(String status);
    Optional<Car> findByChassisNumberAndStatus(String chassisNumber, String status);
    boolean existsByChassisNumberOrEngineNumber(String chassisNumber, String engineNumber);
}