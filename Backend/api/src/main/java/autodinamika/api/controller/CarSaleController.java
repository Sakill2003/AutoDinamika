package autodinamika.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.CarUnitDTO;
import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;
import autodinamika.api.repository.CarRepository;

@RestController
@RequestMapping("/api")
public class CarSaleController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/available-cars")
    public List<CarUnitDTO> getAvailableCars() {
        List<Car> availableCars = carRepository.findByStatus("available");
        List<CarUnitDTO> result = new ArrayList<>();

        for (Car car : availableCars) {
            CarModel model = car.getCarModel();
            CarUnitDTO dto = new CarUnitDTO();
            dto.setId(car.getCarId()); // DROPDOWN ID
            dto.setDisplayName(model.getBrand() + " " + model.getModel() + " " + model.getYear());
            dto.setImage(model.getImgf()); // FIRST IMG
            result.add(dto);
        }

        return result;
    }
}