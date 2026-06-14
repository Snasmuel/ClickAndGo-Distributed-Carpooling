package it.unipi.dii.lsmsdb.lsmsdb_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookingRequestDTO {
    // Gestiamo sia snake_case (dal JSON del collega) che camelCase
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("ride_id")
    private String rideId;

    @JsonProperty("seats_requested")
    private int seatsRequested;

    private String pickup;
    private String dropoff;

    public BookingRequestDTO() {}

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public int getSeatsRequested() { return seatsRequested; }
    public void setSeatsRequested(int seatsRequested) { this.seatsRequested = seatsRequested; }
    public String getPickup() { return pickup; }
    public void setPickup(String pickup) { this.pickup = pickup; }
    public String getDropoff() { return dropoff; }
    public void setDropoff(String dropoff) { this.dropoff = dropoff; }
}