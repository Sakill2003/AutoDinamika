package autodinamika.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.CarModel;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {
    Optional<CarModel> findByBrandAndModelAndYear(String brand, String model, int year);
}
