package autodinamika.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import autodinamika.api.dto.CarModelDTO;
import autodinamika.api.model.CarModel;
import autodinamika.api.repository.CarModelRepository;
import autodinamika.api.repository.CarRepository;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerTest {

    @InjectMocks
    private InventoryController inventoryController;

    @Mock
    private CarModelRepository carModelRepository;

    @Mock
    private CarRepository carRepository;

    private List<CarModel> carModels;

    private CarModel model1;
    private CarModel model2;

    @BeforeEach
    public void setUp() {
        model1 = new CarModel();
        model1.setBrand("Toyota");
        model1.setModel("Corolla");
        model1.setYear(2022);
        model1.setColor("White");
        model1.setPrice(new BigDecimal("20000.00"));
        model1.setImgf("imgf1.jpg");
        model1.setImgs("imgs1.jpg");
        model1.setImgt("imgt1.jpg");

        model2 = new CarModel();
        model2.setBrand("Honda");
        model2.setModel("Civic");
        model2.setYear(2023);
        model2.setColor("Black");
        model2.setPrice(new BigDecimal("22000.00"));
        model2.setImgf("imgf2.jpg");
        model2.setImgs("imgs2.jpg");
        model2.setImgt("imgt2.jpg");

        carModels = Arrays.asList(model1, model2);
    }

    @Test
    public void testGetInventory_WithAvailableUnits() {
        when(carModelRepository.findAll()).thenReturn(carModels);
        when(carRepository.countByCarModelAndStatus(model1, "available")).thenReturn(5);
        when(carRepository.countByCarModelAndStatus(model2, "available")).thenReturn(3);

        List<CarModelDTO> response = inventoryController.getInventory();

        assertNotNull(response);
        assertEquals(2, response.size());

        CarModelDTO dto1 = response.get(0);
        assertEquals("Toyota", dto1.getBrand());
        assertEquals("Corolla", dto1.getModel());
        assertEquals(2022, dto1.getYear());
        assertEquals("White", dto1.getColor());
        assertEquals(new BigDecimal("20000.00"), dto1.getPrice());
        assertEquals(5, dto1.getAvailableUnits());
        assertEquals("imgf1.jpg", dto1.getImgf());
        assertEquals("imgs1.jpg", dto1.getImgs());
        assertEquals("imgt1.jpg", dto1.getImgt());

        CarModelDTO dto2 = response.get(1);
        assertEquals("Honda", dto2.getBrand());
        assertEquals("Civic", dto2.getModel());
        assertEquals(2023, dto2.getYear());
        assertEquals("Black", dto2.getColor());
        assertEquals(new BigDecimal("22000.00"), dto2.getPrice());
        assertEquals(3, dto2.getAvailableUnits());
        assertEquals("imgf2.jpg", dto2.getImgf());
        assertEquals("imgs2.jpg", dto2.getImgs());
        assertEquals("imgt2.jpg", dto2.getImgt());

        verify(carRepository, times(1)).countByCarModelAndStatus(model1, "available");
        verify(carRepository, times(1)).countByCarModelAndStatus(model2, "available");
    }

    @Test
    public void testGetInventory_NoAvailableUnits() {
        when(carModelRepository.findAll()).thenReturn(carModels);
        when(carRepository.countByCarModelAndStatus(any(CarModel.class), eq("available"))).thenReturn(0);

        List<CarModelDTO> response = inventoryController.getInventory();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(carRepository, times(carModels.size())).countByCarModelAndStatus(any(CarModel.class), eq("available"));
    }

    @Test
    public void testGetInventory_NoModels() {
        when(carModelRepository.findAll()).thenReturn(Collections.emptyList());

        List<CarModelDTO> response = inventoryController.getInventory();

        assertNotNull(response);
        assertTrue(response.isEmpty());

        verify(carRepository, never()).countByCarModelAndStatus(any(CarModel.class), anyString());
    }
}
