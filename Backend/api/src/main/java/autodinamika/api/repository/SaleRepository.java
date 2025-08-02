package autodinamika.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.Car;
import autodinamika.api.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
