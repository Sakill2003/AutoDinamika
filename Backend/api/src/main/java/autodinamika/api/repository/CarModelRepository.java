package autodinamika.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.CarModel;

public interface CarModelRepository extends JpaRepository<CarModel, Long> {}
