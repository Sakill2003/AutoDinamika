package autodinamika.api.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.ManagerStatsDTO;
import autodinamika.api.dto.RankedItemDTO;
import autodinamika.api.model.Sale;
import autodinamika.api.repository.SaleRepository;

@RestController
@RequestMapping("/api")
public class ManagerStatsController {

    private final SaleRepository saleRepository;

    // Inyección por constructor para facilitar las pruebas unitarias
    public ManagerStatsController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping("/manager/stats")
    public ResponseEntity<ManagerStatsDTO> getStats() {
        List<Sale> allSales = saleRepository.findAll();

        if (allSales.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        int totalSales = allSales.size();
        double totalCollected = allSales.stream()
            .mapToDouble(sale -> sale.getCar().getCarModel().getPrice().doubleValue())
            .sum();

        double totalCommissions = totalCollected * 0.10;
        double earnings = totalCollected - totalCommissions;

        List<RankedItemDTO> topSellers = allSales.stream()
            .collect(Collectors.groupingBy(
                s -> s.getSeller().getFirst_Name() + " " + s.getSeller().getLast_Name(),
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new RankedItemDTO(e.getKey(), e.getValue().intValue()))
            .toList();

        List<RankedItemDTO> topBrands = allSales.stream()
            .collect(Collectors.groupingBy(
                s -> s.getCar().getCarModel().getBrand(),
                Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .map(e -> new RankedItemDTO(e.getKey(), e.getValue().intValue()))
            .toList();

        List<RankedItemDTO> topModels = allSales.stream()
            .collect(Collectors.groupingBy(
                s -> s.getCar().getCarModel().getModel() + " " + s.getCar().getCarModel().getYear(),
                Collectors.counting()
            ))
            .entrySet().stream()
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

        return ResponseEntity.ok(dto);
    }
}
