package autodinamika.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import autodinamika.api.controller.CarController;
import autodinamika.api.dto.AddCarDTO;
import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;
import autodinamika.api.repository.CarModelRepository;
import autodinamika.api.repository.CarRepository;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @InjectMocks
    private CarController carController;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarModelRepository carModelRepository;

    private AddCarDTO request;

    @BeforeEach
    public void setup() {
        request = new AddCarDTO();
        request.setBrand("Toyota");
        request.setModel("Camry");
        request.setYear(2024);
        request.setChassisNumber("ABC123456");
        request.setEngineNumber("ENG78910");
        request.setPrice(new BigDecimal("25000.00"));  // ← Aquí el cambio importante
        request.setColor("White");
        request.setImgf("imgf.jpg");
        request.setImgs("imgs.jpg");
        request.setImgt("imgt.jpg");
    }

    @Test
    public void testAddCarDuplicateChassisOrEngine() {
        when(carRepository.existsByChassisNumberOrEngineNumber(anyString(), anyString())).thenReturn(true);

        ResponseEntity<?> response = carController.addCar(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("already exists"));

        verify(carRepository, never()).save(any());
        verify(carModelRepository, never()).save(any());
    }

    @Test
    public void testAddCarWithExistingModel() {
        CarModel existingModel = new CarModel();
        existingModel.setModelId(1L);
        existingModel.setBrand("Toyota");
        existingModel.setModel("Camry");
        existingModel.setYear(2024);
        existingModel.setPrice(new BigDecimal("23000.00"));  // ← BigDecimal aquí también
        existingModel.setColor("White");

        when(carRepository.existsByChassisNumberOrEngineNumber(anyString(), anyString())).thenReturn(false);
        when(carModelRepository.findByBrandAndModelAndYear("Toyota", "Camry", 2024)).thenReturn(Optional.of(existingModel));

        ResponseEntity<?> response = carController.addCar(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Car added successfully."));

        verify(carRepository, times(1)).save(any(Car.class));
        verify(carModelRepository, never()).save(any(CarModel.class));
    }

    @Test
    public void testAddCarWithNewModel() {
        when(carRepository.existsByChassisNumberOrEngineNumber(anyString(), anyString())).thenReturn(false);
        when(carModelRepository.findByBrandAndModelAndYear(anyString(), anyString(), anyInt())).thenReturn(Optional.empty());

        ResponseEntity<?> response = carController.addCar(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Car added successfully."));

        verify(carModelRepository, times(1)).save(any(CarModel.class));
        verify(carRepository, times(1)).save(any(Car.class));
    }
}
