package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String bookingDate;
    @Field("ride_id") private String rideId;
    @Field("car_plate") private String carPlate;
    private double finalPrice;
    private String paymentStatus;

    private PassengerSummary passenger;
    private DriverSummary driver;
    private Locations locations;

    public static class PassengerSummary {
        @Field("id") private String id;
        private String name;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class DriverSummary {
        @Field("id") private String id;
        private String name;
        @Field("car_plate") private String carPlate;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCarPlate() { return carPlate; }
        public void setCarPlate(String carPlate) { this.carPlate = carPlate; }
    }

    public static class Locations {
        private String pickup;
        private String dropoff;

        public String getPickup() { return pickup; }
        public void setPickup(String pickup) { this.pickup = pickup; }
        public String getDropoff() { return dropoff; }
        public void setDropoff(String dropoff) { this.dropoff = dropoff; }
    }

    // --- Getters/Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public PassengerSummary getPassenger() { return passenger; }
    public void setPassenger(PassengerSummary passenger) { this.passenger = passenger; }
    public DriverSummary getDriver() { return driver; }
    public void setDriver(DriverSummary driver) { this.driver = driver; }
    public Locations getLocations() { return locations; }
    public void setLocations(Locations locations) { this.locations = locations; }
    public double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(double finalPrice) { this.finalPrice = finalPrice; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
    public String getCarPlate() { return carPlate; }
    public void setCarPlate(String carPlate) { this.carPlate = carPlate; }
}