package it.unipi.dii.lsmsdb.lsmsdb_project.controller;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Car;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired private CarService carService;

    // GET http://localhost:8080/api/cars/owner/user_123
    @GetMapping("/owner/{ownerId}")
    public List<Car> getCarsByOwner(@PathVariable String ownerId) {
        return carService.getCarsByOwner(ownerId);
    }

    // POST http://localhost:8080/api/cars?ownerId=user_123
    // Body: { "details": { "brand": "Fiat", "model": "Panda" ... } }
    @PostMapping
    public Car addCar(@RequestBody Car car, @RequestParam String ownerId) {
        return carService.saveCar(car, ownerId);
    }
}