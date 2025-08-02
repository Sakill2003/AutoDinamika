package autodinamika.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    private CarModelRepository carModelRepository;

    @Mock
    private CarRepository carRepository;

    private AddCarDTO request;

    @BeforeEach
    void setUp() {
        request = new AddCarDTO();
        request.setBrand("Toyota");
        request.setModel("Camry");
        request.setYear(2024);
        request.setChassisNumber("ABC123456");
        request.setEngineNumber("ENG78910");
        request.setPrice(25000.00);
        request.setColor("White");
        request.setImgf("imgf.jpg");
        request.setImgs("imgs.jpg");
        request.setImgt("imgt.jpg");

        System.out.println("Test setup complete.");
    }

    @Test
    void shouldReturnErrorIfChassisOrEngineAlreadyExists() {
        System.out.println("Test: shouldReturnErrorIfChassisOrEngineAlreadyExists");

        // Given
        Mockito.when(carRepository.existsByChassisNumberOrEngineNumber(anyString(), anyString()))
                .thenReturn(true);

        // When
        ResponseEntity<?> response = carController.addCar(request);

        // Then
        System.out.println("Response: " + response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("already exists"));

        System.out.println("Passed: Error returned as expected for duplicate chassis/engine.");
    }

    @Test
    void shouldCreateNewCarWithExistingCarModel() {
        System.out.println("Test: shouldCreateNewCarWithExistingCarModel");

        CarModel existingModel = new CarModel();
        existingModel.setModelId(1L);
        existingModel.setBrand("Toyota");
        existingModel.setModel("Camry");
        existingModel.setYear(2024);
        existingModel.setPrice(23000.00);
        existingModel.setColor("White");

        Mockito.when(carRepository.existsByChassisNumberOrEngineNumber(any(), any())).thenReturn(false);
        Mockito.when(carModelRepository.findByBrandAndModelAndYear("Toyota", "Camry", 2024))
                .thenReturn(Optional.of(existingModel));

        ResponseEntity<?> response = carController.addCar(request);

        System.out.println("Response: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Car added successfully."));

        Mockito.verify(carRepository, times(1)).save(any(Car.class));
        Mockito.verify(carModelRepository, never()).save(any(CarModel.class));

        System.out.println("Passed: Car saved using existing model.");
    }

    @Test
    void shouldCreateNewCarModelIfNotExists() {
        System.out.println("Test: shouldCreateNewCarModelIfNotExists");

        Mockito.when(carRepository.existsByChassisNumberOrEngineNumber(any(), any())).thenReturn(false);
        Mockito.when(carModelRepository.findByBrandAndModelAndYear(any(), any(), anyInt()))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = carController.addCar(request);

        System.out.println("Response: " + response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Mockito.verify(carModelRepository).save(any(CarModel.class));
        Mockito.verify(carRepository).save(any(Car.class));

        System.out.println("Passed: New model and car saved.");
    }
}
