package autodinamika.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.math.BigDecimal;
import java.math.RoundingMode;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.CarUnitDTO;
import autodinamika.api.dto.SellCarRequestDTO;
import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;
import autodinamika.api.model.Customer;
import autodinamika.api.model.Sale;
import autodinamika.api.model.Seller;
import autodinamika.api.repository.CarRepository;
import autodinamika.api.repository.CustomerRepository;
import autodinamika.api.repository.SaleRepository;
import autodinamika.api.repository.SellerRepository;

@RestController
@RequestMapping("/api")
public class CarSaleController {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SaleRepository saleRepository;

    @GetMapping("/available-cars")
    public List<CarUnitDTO> getAvailableCars() {
        List<Car> availableCars = carRepository.findByStatus("available");
        List<CarUnitDTO> result = new ArrayList<>();

        for (Car car : availableCars) {
            CarModel model = car.getCarModel();
            CarUnitDTO dto = new CarUnitDTO();
            dto.setChasis(car.getChassisNumber()); // DROPDOWN ID
            dto.setDisplayName(model.getBrand() + " " + model.getModel() + " " + model.getYear());
            dto.setImage(model.getImgf()); // FIRST IMG
            result.add(dto);
        }

        return result;
    }

    @PostMapping("/sell-car")
    public ResponseEntity<?> sellCar(@RequestBody SellCarRequestDTO req) {
        try {
            System.out.println("carId recibido: " + req.chasis);
            Car car = carRepository.findByChassisNumberAndStatus(req.chasis, "available")
                .orElseThrow(() -> new RuntimeException("Car not available"));

            Customer customer = customerRepository.findByDocumentNumber(req.documentNumber)
                .orElseGet(() -> {
                    Customer c = new Customer();
                    c.setName(req.customerFirstName + " " + req.customerLastName);
                    c.setIdType(req.idType);
                    c.setDocumentNumber(req.documentNumber);
                    return customerRepository.save(c);
                });

            Seller seller = sellerRepository.findById(req.sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

            Sale sale = new Sale();
            sale.setCar(car);
            sale.setCustomer(customer);
            sale.setSeller(seller);
            sale.setPayment(req.paymentMethod);
            
            // Guardar la venta primero para obtener ID si se usa
            saleRepository.save(sale);

            car.setStatus("sold");
            carRepository.save(car);

            // Calcular comisión: 10% del precio usando BigDecimal
            BigDecimal price = car.getCarModel().getPrice();
            BigDecimal commission = price.multiply(BigDecimal.valueOf(0.10))
                                         .setScale(2, RoundingMode.HALF_UP);

            // Actualizar las comisiones del vendedor también usando BigDecimal
            BigDecimal currentCommissions = seller.getCommissions() != null ? seller.getCommissions() : BigDecimal.ZERO;
            BigDecimal updatedCommissions = currentCommissions.add(commission);
            seller.setCommissions(updatedCommissions);
            sellerRepository.save(seller);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("commission", commission);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}