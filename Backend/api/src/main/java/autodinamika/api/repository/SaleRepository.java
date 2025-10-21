package com.autodinamikasystem.repository;

import com.autodinamikasystem.dto.SellerStatsDTO;
import com.autodinamikasystem.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.autodinamikasystem.dto.SellerStatsDTO( " +
           "s.seller.name, " +
           "COUNT(s), " +
           "SUM(s.price), " +
           "SUM(s.commission) ) " +
           "FROM Sale s " +
           "GROUP BY s.seller.name")
    List<SellerStatsDTO> getSellerStats();
}

