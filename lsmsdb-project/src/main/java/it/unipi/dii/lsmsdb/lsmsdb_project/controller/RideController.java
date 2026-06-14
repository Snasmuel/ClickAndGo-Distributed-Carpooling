package it.unipi.dii.lsmsdb.lsmsdb_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Ride;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.RideService;

@RestController
@RequestMapping("/api/rides")
public class RideController {

    @Autowired private RideService rideService;

    // POST http://localhost:8080/api/rides
    @PostMapping
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
        Ride newRide = rideService.createRide(ride);
        return ResponseEntity.ok(newRide);
    }

    // GET http://localhost:8080/api/rides/search?latA=43.7&lonA=10.4&latB=45.4&lonB=9.1
    @GetMapping("/search")
    public List<Ride> searchRides(@RequestParam Double latA, @RequestParam Double lonA,
                                  @RequestParam Double latB, @RequestParam Double lonB) {
        return rideService.searchMatchingRides(latA, lonA, latB, lonB);
    }

    // GET http://localhost:8080/api/rides
    @GetMapping
    public List<Ride> getAllRides() {
        return rideService.getAllRides();
    }
}