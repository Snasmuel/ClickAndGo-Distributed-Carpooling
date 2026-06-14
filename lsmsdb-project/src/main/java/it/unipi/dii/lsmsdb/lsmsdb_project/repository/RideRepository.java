package it.unipi.dii.lsmsdb.lsmsdb_project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Ride;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findByRouteOriginIgnoreCaseAndRouteDestinationIgnoreCase(String origin, String destination);
    List<Ride> findByRouteOriginInAndRouteDestinationIn(List<String> origins, List<String> destinations);
    @Query("{ 'status': 'OPEN', 'booking_state.available_seats': { $gt: 0 } }")
    List<Ride> findAvailableRides();
}