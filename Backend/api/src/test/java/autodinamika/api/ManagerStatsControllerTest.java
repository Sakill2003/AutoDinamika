package autodinamika.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import autodinamika.api.dto.ManagerStatsDTO;
import autodinamika.api.dto.RankedItemDTO;
import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;
import autodinamika.api.model.Sale;
import autodinamika.api.model.Seller;
import autodinamika.api.repository.SaleRepository;

@ExtendWith(MockitoExtension.class)
public class ManagerStatsControllerTest {

    @InjectMocks
    private ManagerStatsController controller;

    @Mock
    private SaleRepository saleRepository;

    private List<Sale> sales;

    @BeforeEach
    public void setup() {
        sales = new ArrayList<>();

        // Vendedores
        Seller seller1 = new Seller();
        seller1.setSeller_ID(1L);
        seller1.setFirst_Name("Juan");
        seller1.setLast_Name("Perez");

        Seller seller2 = new Seller();
        seller2.setSeller_ID(2L);
        seller2.setFirst_Name("Ana");
        seller2.setLast_Name("Gomez");

        // Modelos
        CarModel modelA = new CarModel();
        modelA.setBrand("Toyota");
        modelA.setModel("Corolla");
        modelA.setYear(2020);
        modelA.setPrice(new BigDecimal("20000.00"));

        CarModel modelB = new CarModel();
        modelB.setBrand("Honda");
        modelB.setModel("Civic");
        modelB.setYear(2019);
        modelB.setPrice(new BigDecimal("22000.00"));

        // Autos
        Car car1 = new Car();
        car1.setCarModel(modelA);

        Car car2 = new Car();
        car2.setCarModel(modelB);

        // Ventas
        Sale sale1 = new Sale();
        sale1.setSeller(seller1);
        sale1.setCar(car1);

        Sale sale2 = new Sale();
        sale2.setSeller(seller1);
        sale2.setCar(car2);

        Sale sale3 = new Sale();
        sale3.setSeller(seller2);
        sale3.setCar(car1);

        sales.add(sale1);
        sales.add(sale2);
        sales.add(sale3);
    }

    @Test
    public void testGetStats_withSales() {
        when(saleRepository.findAll()).thenReturn(sales);

        ManagerStatsDTO result = controller.getStats();

        assertNotNull(result);
        assertEquals(3, result.getTotalSales());
        assertEquals(62000.0, result.getTotalCollected(), 0.001);
        assertEquals(6200.0, 62000.0 * 0.10, 0.001);
        assertEquals(55800.0, result.getEarnings(), 0.001);

        // Verificar top vendedores
        List<RankedItemDTO> sellers = result.getTopSellers();
        assertNotNull(sellers);
        assertEquals(2, sellers.size());

        assertEquals("Juan Perez", sellers.get(0).getName());
        assertEquals(2, sellers.get(0).getValue());

        assertEquals("Ana Gomez", sellers.get(1).getName());
        assertEquals(1, sellers.get(1).getValue());

        // Verificar top marcas
        List<RankedItemDTO> brands = result.getTopBrands();
        assertNotNull(brands);
        assertEquals(2, brands.size());

        assertEquals("Toyota", brands.get(0).getName());
        assertEquals(2, brands.get(0).getValue());

        assertEquals("Honda", brands.get(1).getName());
        assertEquals(1, brands.get(1).getValue());

        // Verificar top modelos
        List<RankedItemDTO> models = result.getTopModels();
        assertNotNull(models);
        assertEquals(2, models.size());

        assertEquals("Corolla 2020", models.get(0).getName());
        assertEquals(2, models.get(0).getValue());

        assertEquals("Civic 2019", models.get(1).getName());
        assertEquals(1, models.get(1).getValue());
    }

    @Test
    public void testGetStats_withNoSales() {
        when(saleRepository.findAll()).thenReturn(Collections.emptyList());

        ManagerStatsDTO result = controller.getStats();

        assertNotNull(result);
        assertEquals(0, result.getTotalSales());
        assertEquals(0.0, result.getTotalCollected(), 0.001);
        assertEquals(0.0, result.getEarnings(), 0.001);

        assertTrue(result.getTopSellers().isEmpty());
        assertTrue(result.getTopBrands().isEmpty());
        assertTrue(result.getTopModels().isEmpty());
    }
}
