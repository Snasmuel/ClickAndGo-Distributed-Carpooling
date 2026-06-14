package it.unipi.dii.lsmsdb.lsmsdb_project.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Car;

@Repository
public interface CarRepository extends MongoRepository<Car, String> {
}