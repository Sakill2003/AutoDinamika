package autodinamika.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import autodinamika.api.model.Sale;
import autodinamika.api.model.Seller;
import autodinamika.api.repository.SaleRepository;
import autodinamika.api.repository.SellerRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final SellerRepository sellerRepository;
    private final SaleRepository saleRepository;

    public StatsController(SellerRepository sellerRepository, SaleRepository saleRepository) {
        this.sellerRepository = sellerRepository;
        this.saleRepository = saleRepository;
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<?> getSellerStats(@PathVariable("id") Long sellerId) {
        Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);

        if (optionalSeller.isEmpty()) {
            // Retornar 404 con mensaje 
            Map<String, String> errorResponse = Map.of("message", "Seller not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        Seller seller = optionalSeller.get();

        // Validar que birthday_Date no sea null
        String birthdayStr = "";
        if (seller.getBirthday_Date() != null) {
            birthdayStr = seller.getBirthday_Date().toString();
        } else {
            birthdayStr = "No disponible";  
        }

        List<Sale> recentSales = saleRepository.findTop7BySellerOrderByDateDesc(seller);

        Map<String, Object> response = new HashMap<>();
        response.put("sellerId", seller.getSeller_ID());
        response.put("sellerName", seller.getFirst_Name() + " " + seller.getLast_Name());
        response.put("birthday", birthdayStr);
        response.put("recentSales", recentSales);


        return ResponseEntity.ok(response);
    }

}

