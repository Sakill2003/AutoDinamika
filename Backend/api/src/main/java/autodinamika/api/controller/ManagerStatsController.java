package autodinamika.api.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.ManagerStatsDTO;
import autodinamika.api.dto.RankedItemDTO;
import autodinamika.api.model.Sale;
import autodinamika.api.repository.SaleRepository;


@RestController
@RequestMapping("/api")
public class ManagerStatsController {

    @Autowired
    private SaleRepository saleRepository;

    @GetMapping("/manager/stats")
    public ManagerStatsDTO getStats() {
        List<Sale> allSales = saleRepository.findAll();

        int totalSales = allSales.size();
        double totalCollected = allSales.stream()
            .mapToDouble(sale -> sale.getCar().getCarModel().getPrice().doubleValue())
            .sum();

        double totalCommissions = allSales.stream()
            .mapToDouble(sale -> sale.getCar().getCarModel().getPrice().doubleValue() * 0.10)
            .sum();

        double earnings = totalCollected - totalCommissions;

        // Top sellers
        Map<String, Long> sellerCount = allSales.stream()
            .collect(Collectors.groupingBy(
                s -> s.getSeller().getFirst_Name() + " " + s.getSeller().getLast_Name(),
                Collectors.counting()
            ));

        List<RankedItemDTO> topSellers = sellerCount.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new RankedItemDTO(e.getKey(), e.getValue().intValue()))
            .toList();

        // Top brands
        Map<String, Long> brandCount = allSales.stream()
            .collect(Collectors.groupingBy(
                s -> s.getCar().getCarModel().getBrand(),
                Collectors.counting()
            ));

        List<RankedItemDTO> topBrands = brandCount.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new RankedItemDTO(e.getKey(), e.getValue().intValue()))
            .toList();

        // Top models
        Map<String, Long> modelCount = allSales.stream()
            .collect(Collectors.groupingBy(
                s -> s.getCar().getCarModel().getModel() + " " + s.getCar().getCarModel().getYear(),
                Collectors.counting()
            ));

        List<RankedItemDTO> topModels = modelCount.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new RankedItemDTO(e.getKey(), e.getValue().intValue()))
            .toList();

        ManagerStatsDTO dto = new ManagerStatsDTO();
        dto.setTotalSales(totalSales);
        dto.setTotalCollected(totalCollected);
        dto.setEarnings(earnings);
        dto.setTopSellers(topSellers);
        dto.setTopBrands(topBrands);
        dto.setTopModels(topModels);

        return dto;
    }
    
}
