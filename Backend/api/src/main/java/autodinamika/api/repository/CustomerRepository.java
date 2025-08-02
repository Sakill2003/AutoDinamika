package autodinamika.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import autodinamika.api.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByDocumentNumber(String documentNumber);
}