package autodinamika.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.Car;
import autodinamika.api.model.Sale;
import autodinamika.api.model.Seller;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    int countBySeller(Seller seller);
    List<Sale> findTop7BySellerOrderByDateDesc(Seller seller);

}
