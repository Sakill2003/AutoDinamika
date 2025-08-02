package autodinamika.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import autodinamika.api.dto.CarModelDTO;
import autodinamika.api.model.CarModel;
import autodinamika.api.repository.CarModelRepository;
import autodinamika.api.repository.CarRepository;

@RestController
@RequestMapping("/api")
public class InventoryController {

    @Autowired
    private CarModelRepository carModelRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/inventory")
    public List<CarModelDTO> getInventory() {
        List<CarModel> models = carModelRepository.findAll();
        List<CarModelDTO> response = new ArrayList<>();

        for (CarModel model : models) {
            int availableUnits = carRepository.countByCarModelAndStatus(model, "available");

            if (availableUnits == 0) {
            continue;
        }
            CarModelDTO dto = new CarModelDTO();
            dto.setBrand(model.getBrand());
            dto.setModel(model.getModel());
            dto.setYear(model.getYear());
            dto.setColor(model.getColor());
            dto.setPrice(model.getPrice().intValue());
            dto.setImgf(model.getImgf());
            dto.setImgs(model.getImgs());
            dto.setImgt(model.getImgt());
            dto.setAvailableUnits(availableUnits);

            response.add(dto);
        }

        return response;
    }
}