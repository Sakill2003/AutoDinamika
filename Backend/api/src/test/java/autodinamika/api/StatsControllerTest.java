package autodinamika.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import autodinamika.api.controller.StatsController;
import autodinamika.api.model.Sale;
import autodinamika.api.model.Seller;
import autodinamika.api.repository.SaleRepository;
import autodinamika.api.repository.SellerRepository;

@ExtendWith(MockitoExtension.class)
public class StatsControllerTest {

    @InjectMocks
    private StatsController statsController;

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SellerRepository sellerRepository;

    private Seller testSeller;

    private List<Sale> recentSales;

    @BeforeEach
    public void setup() {
        testSeller = new Seller();
        testSeller.setSeller_ID(1L);
        testSeller.setFirst_Name("Luis");
        testSeller.setLast_Name("Mendoza");

        // Inicializamos birthday_Date para evitar NullPointerException en el test
        testSeller.setBirthday_Date(LocalDate.of(1990, 1, 1));

        recentSales = new ArrayList<>();
        Sale saleExample = new Sale();
        recentSales.add(saleExample);
    }

    @Test
    public void testGetSellerStatsSuccess() {
        Long sellerId = 1L;

        when(sellerRepository.findById(sellerId)).thenReturn(Optional.of(testSeller));
        when(saleRepository.findTop7BySellerOrderByDateDesc(testSeller)).thenReturn(recentSales);

        ResponseEntity<?> response = statsController.getSellerStats(sellerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals("Luis Mendoza", body.get("sellerName"));
        assertEquals("1990-01-01", body.get("birthday"));
        assertEquals(recentSales, body.get("recentSales"));
    }

    @Test
    public void testGetSellerStatsSuccessHandlesNullBirthday() {
        // Vendedor con birthday_Date null para probar manejo robusto
        Seller sellerWithNullBirthday = new Seller();
        sellerWithNullBirthday.setSeller_ID(2L);
        sellerWithNullBirthday.setFirst_Name("Ana");
        sellerWithNullBirthday.setLast_Name("Perez");
        sellerWithNullBirthday.setBirthday_Date(null);

        when(sellerRepository.findById(2L)).thenReturn(Optional.of(sellerWithNullBirthday));
        when(saleRepository.findTop7BySellerOrderByDateDesc(sellerWithNullBirthday)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = statsController.getSellerStats(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertEquals("Ana Perez", body.get("sellerName"));
        assertEquals("No disponible", body.get("birthday"));
        assertEquals(Collections.emptyList(), body.get("recentSales"));
    }

    @Test
    public void testGetSellerStatsNotFound() {
        Long sellerId = 999L;
        when(sellerRepository.findById(sellerId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = statsController.getSellerStats(sellerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) response.getBody();
        assertTrue(body.get("message").toLowerCase().contains("not found"));
    }
}
