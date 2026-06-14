package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "rides")
public class Ride {

    @Id
    private String id;
    private String status; // "OPEN", "FULL"
    @Field("base_price")
    private Double basePrice;

    private DriverInfo driver;
    private CarInfo car;
    private RouteInfo route;

    @Field("booking_state")
    private BookingState bookingState;
    private Metadata metadata;

    public Ride() {}

    public static class DriverInfo {
        @Field("id") private String id;
        @Field("name") private String name;
        @Field("phone") private String phone;
        @Field("avg_acceptance_rate") private Double avgAcceptanceRate;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Double getAvgAcceptanceRate() { return avgAcceptanceRate; }
        public void setAvgAcceptanceRate(Double avgAcceptanceRate) { this.avgAcceptanceRate = avgAcceptanceRate; }
    }

    public static class CarInfo {
        private String model;
        private String plate;
        private String comfort;

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getPlate() { return plate; }
        public void setPlate(String plate) { this.plate = plate; }
        public String getComfort() { return comfort; }
        public void setComfort(String comfort) { this.comfort = comfort; }
    }

    public static class RouteInfo {
        private String origin;
        private Double originLat;
        private Double originLon;

        private String destination;
        private Double destLat;
        private Double destLon;

        @Field("route_id") private String routeId;

        // Getters/Setters standard
        public String getOrigin() { return origin; }
        public void setOrigin(String origin) { this.origin = origin; }
        public Double getOriginLat() { return originLat; }
        public void setOriginLat(Double originLat) { this.originLat = originLat; }
        public Double getOriginLon() { return originLon; }
        public void setOriginLon(Double originLon) { this.originLon = originLon; }

        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
        public Double getDestLat() { return destLat; }
        public void setDestLat(Double destLat) { this.destLat = destLat; }
        public Double getDestLon() { return destLon; }
        public void setDestLon(Double destLon) { this.destLon = destLon; }

        public String getRouteId() { return routeId; }
        public void setRouteId(String routeId) { this.routeId = routeId; }
    }

    public static class BookingState {
        @Field("total_seats") private Integer totalSeats;
        @Field("available_seats") private Integer availableSeats;
        @Field("has_waiting_list") private Boolean hasWaitingList;

        public Integer getTotalSeats() { return totalSeats; }
        public void setTotalSeats(Integer totalSeats) { this.totalSeats = totalSeats; }
        public Integer getAvailableSeats() { return availableSeats; }
        public void setAvailableSeats(Integer availableSeats) { this.availableSeats = availableSeats; }
        public Boolean getHasWaitingList() { return hasWaitingList; }
        public void setHasWaitingList(Boolean hasWaitingList) { this.hasWaitingList = hasWaitingList; }
    }

    public static class Metadata {
        @Field("created_at") private String createdAt;
        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    }

    // --- Ride Getters/Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }
    public DriverInfo getDriver() { return driver; }
    public void setDriver(DriverInfo driver) { this.driver = driver; }
    public CarInfo getCar() { return car; }
    public void setCar(CarInfo car) { this.car = car; }
    public RouteInfo getRoute() { return route; }
    public void setRoute(RouteInfo route) { this.route = route; }
    public BookingState getBookingState() { return bookingState; }
    public void setBookingState(BookingState bookingState) { this.bookingState = bookingState; }
    public Metadata getMetadata() { return metadata; }
    public void setMetadata(Metadata metadata) { this.metadata = metadata; }
}