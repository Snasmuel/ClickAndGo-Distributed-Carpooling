package it.unipi.dii.lsmsdb.lsmsdb_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'personalInfo.email' : ?0 }")
    Optional<User> findByEmail(String email);
    @Query("{ 'driverInfo' : { $exists: true }, 'status': 'ACTIVE' }")
    List<User> findActiveDrivers();
    @Query("{ 'personalInfo.name': { $regex: ?0, $options: 'i' } }")
    List<User> findByNameRegex(String name);
}