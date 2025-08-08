package autodinamika.api.controller;

import autodinamika.api.dto.CarUnitDTO;
import autodinamika.api.dto.SellCarRequestDTO;
import autodinamika.api.model.*;
import autodinamika.api.repository.CarRepository;
import autodinamika.api.repository.CustomerRepository;
import autodinamika.api.repository.SaleRepository;
import autodinamika.api.repository.SellerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarSaleControllerTest {

    @InjectMocks
    private CarSaleController carSaleController;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private SaleRepository saleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSellCar_Success() {
        // Arrange
        SellCarRequestDTO request = new SellCarRequestDTO();
        request.chasis = "CH123";
        request.paymentMethod = "cash";
        request.sellerId = 1L;
        request.documentNumber = "123456789";
        request.idType = "CC";
        request.customerFirstName = "Luis";
        request.customerLastName = "Torres";

        CarModel carModel = new CarModel();
        carModel.setBrand("Toyota");
        carModel.setModel("Corolla");
        carModel.setYear(2022);
        carModel.setPrice(BigDecimal.valueOf(50000));

        Car car = new Car();
        car.setChassisNumber("CH123");
        car.setStatus("available");
        car.setCarModel(carModel);

        Seller seller = new Seller();
        seller.setSeller_ID(1L);
        seller.setCommissions(BigDecimal.ZERO);

        Customer customer = new Customer();
        customer.setName("Luis Torres");
        customer.setDocumentNumber("123456789");

        when(carRepository.findByChassisNumberAndStatus("CH123", "available")).thenReturn(Optional.of(car));
        when(customerRepository.findByDocumentNumber("123456789")).thenReturn(Optional.of(customer));
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(saleRepository.save(any(Sale.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(sellerRepository.save(any(Seller.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        ResponseEntity<?> response = carSaleController.sellCar(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertTrue((Boolean) body.get("success"));
        assertEquals(BigDecimal.valueOf(5000.00).setScale(2), body.get("commission")); // 10% of 50000
    }

    @Test
    void testGetAvailableCars() {
        // Arrange
        CarModel model = new CarModel();
        model.setBrand("Toyota");
        model.setModel("Yaris");
        model.setYear(2021);
        model.setImgf("yaris.png");

        Car car = new Car();
        car.setChassisNumber("CH999");
        car.setStatus("available");
        car.setCarModel(model);

        when(carRepository.findByStatus("available")).thenReturn(List.of(car));

        // Act
        List<CarUnitDTO> result = carSaleController.getAvailableCars();

        // Assert
        assertEquals(1, result.size());
        CarUnitDTO dto = result.get(0);
        assertEquals("CH999", dto.getChasis());
        assertEquals("Toyota Yaris 2021", dto.getDisplayName());
        assertEquals("yaris.png", dto.getImage());
    }
}
