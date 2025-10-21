package autodinamika.api.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.SaleInfoDTO;
import autodinamika.api.dto.SellerStatsDTO;
import autodinamika.api.model.CarModel;
import autodinamika.api.model.Sale;
import autodinamika.api.model.Seller;
import autodinamika.api.repository.SaleRepository;
import autodinamika.api.repository.SellerRepository;

@RestController
@RequestMapping("/api")
public class StatsController {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @GetMapping("/seller/stats/{id}")
    public ResponseEntity<SellerStatsDTO> getSellerStats(@PathVariable Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        SellerStatsDTO dto = new SellerStatsDTO();
        dto.setFullName(seller.getFirst_Name() + " " + seller.getLast_Name());
        dto.setBirthDate(seller.getBirthday_Date().toString());
        dto.setAge(Period.between(seller.getBirthday_Date(), LocalDate.now()).getYears());
        dto.setPhone(seller.getPhone());
        dto.setEmail(seller.getEmail());
        dto.setAddress(seller.getAddress());
        dto.setTotalCommissions(seller.getCommissions());

        int totalSales = saleRepository.countBySeller(seller);
        dto.setTotalSales(totalSales);

        List<Sale> recentSales = saleRepository.findTop7BySellerOrderByDateDesc(seller);

        List<SaleInfoDTO> salesInfo = recentSales.stream().map(sale -> {
            CarModel model = sale.getCar().getCarModel();

            SaleInfoDTO s = new SaleInfoDTO();
            s.setBrand(model.getBrand());
            s.setModel(model.getModel());
            s.setYear(model.getYear());
            s.setSaleDate(sale.getDate().toString());
            return s;
        }).toList();

        dto.setLatestSales(salesInfo);

        return ResponseEntity.ok(dto);
    }

}
