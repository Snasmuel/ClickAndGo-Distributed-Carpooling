package it.unipi.dii.lsmsdb.lsmsdb_project.service;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Car;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.User;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.CarRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired private CarRepository carRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private MongoTemplate mongoTemplate; // Necessario per l'operazione atomica $push

    public List<Car> getCarsByOwner(String ownerId) {
        User user = userRepository.findById(ownerId).orElse(null);

        if (user == null || user.getDriverInfo() == null || user.getDriverInfo().getCars() == null) {
            return new ArrayList<>();
        }

        List<String> carIds = user.getDriverInfo().getCars().stream()
                .map(User.EmbeddedCar::getCarId)
                .collect(Collectors.toList());

        return (List<Car>) carRepository.findAllById(carIds);
    }

    public Car saveCar(Car car, String ownerId) {
        Car savedCar = carRepository.save(car);

        String modelName = (savedCar.getDetails() != null) ? savedCar.getDetails().getModel() : "Unknown";
        User.EmbeddedCar embeddedData = new User.EmbeddedCar(savedCar.getId(), modelName);

        Query query = new Query(Criteria.where("_id").is(ownerId));
        Update update = new Update().push("driverInfo.cars", embeddedData);

        mongoTemplate.updateFirst(query, update, User.class);

        return savedCar;
    }
}