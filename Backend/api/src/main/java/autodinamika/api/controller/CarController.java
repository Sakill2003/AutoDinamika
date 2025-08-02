package autodinamika.api.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.AddCarDTO;
import autodinamika.api.model.Car;
import autodinamika.api.model.CarModel;
import autodinamika.api.repository.CarModelRepository;
import autodinamika.api.repository.CarRepository;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarRepository carRepository;

    @PostMapping("/add-car")
    public ResponseEntity<?> addCar(@RequestBody AddCarDTO request) {
        boolean exists = carRepository.existsByChassisNumberOrEngineNumber(
            request.getChassisNumber(), request.getEngineNumber()
        );

        if (exists) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Chassis or engine number already exists."));
        }

        Optional<CarModel> existingModel = carModelRepository.findByBrandAndModelAndYear(
            request.getBrand(), request.getModel(), request.getYear()
        );

        CarModel carModel;
        if (existingModel.isPresent()) {
            carModel = existingModel.get();

            if (carModel.getPrice().compareTo(request.getPrice()) != 0) {

            }

        } else {

            carModel = new CarModel();
            carModel.setBrand(request.getBrand());
            carModel.setModel(request.getModel());
            carModel.setYear(request.getYear());
            carModel.setPrice(request.getPrice());
            carModel.setColor(request.getColor());
            carModel.setImgf(request.getImgf());
            carModel.setImgs(request.getImgs());
            carModel.setImgt(request.getImgt());

            carModelRepository.save(carModel);
        }


        Car car = new Car();
        car.setCarModel(carModel);
        car.setChassisNumber(request.getChassisNumber());
        car.setEngineNumber(request.getEngineNumber());
        car.setStatus("available");

        carRepository.save(car);

        return ResponseEntity.ok(Map.of("success", true, "message", "Car added successfully."));
    }
}
