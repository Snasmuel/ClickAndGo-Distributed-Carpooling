package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

// TTL 30 minutes = 1800 s
@RedisHash(value = "booking_requests", timeToLive = 1800)
public class BookingRequest implements Serializable {

    @Id
    private String id;
    @Indexed
    private String passengerId;
    @Indexed
    private String rideId;
    private int seatsRequested;

    public BookingRequest() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPassengerId() { return passengerId; }
    public void setPassengerId(String passengerId) { this.passengerId = passengerId; }
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public int getSeatsRequested() { return seatsRequested; }
    public void setSeatsRequested(int seatsRequested) { this.seatsRequested = seatsRequested; }
}