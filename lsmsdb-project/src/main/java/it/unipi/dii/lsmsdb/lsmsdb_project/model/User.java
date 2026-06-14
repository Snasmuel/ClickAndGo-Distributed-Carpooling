package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;
import java.util.ArrayList;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Field("personalInfo")
    private PersonalInfo personalInfo;

    @Field("documents")
    private List<UserDocument> documents;

    @Field("driverInfo")
    private DriverInfo driverInfo;

    @Field("reviews_driver")
    private ReviewStats reviewsDriver;

    @Field("reviews_passanger")
    private ReviewStats reviewsPassenger;

    private String status;

    public User() {}

    // --- Inner Classes ---
    public static class PersonalInfo {
        private String name;
        private String surname;
        private String email;
        private String phone;
        private String location;
        @Field("is_identity_verified") private boolean identityVerified;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSurname() { return surname; }
        public void setSurname(String surname) { this.surname = surname; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
        public boolean isIdentityVerified() { return identityVerified; }
        public void setIdentityVerified(boolean identityVerified) { this.identityVerified = identityVerified; }
    }

    public static class UserDocument {
        private String type;
        private String documentId;
        private boolean isValid;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getDocumentId() { return documentId; }
        public void setDocumentId(String documentId) { this.documentId = documentId; }
        public boolean isValid() { return isValid; }
        public void setValid(boolean valid) { isValid = valid; }
    }

    public static class DriverInfo {
        @Field("avg_acceptance_rate") private double avgAcceptanceRate;
        @Field("number_of_acceptance") private int numberOfAcceptance;

        private List<EmbeddedCar> cars = new ArrayList<>();

        public double getAvgAcceptanceRate() { return avgAcceptanceRate; }
        public void setAvgAcceptanceRate(double avgAcceptanceRate) { this.avgAcceptanceRate = avgAcceptanceRate; }
        public int getNumberOfAcceptance() { return numberOfAcceptance; }
        public void setNumberOfAcceptance(int numberOfAcceptance) { this.numberOfAcceptance = numberOfAcceptance; }
        public List<EmbeddedCar> getCars() { return cars; }
        public void setCars(List<EmbeddedCar> cars) { this.cars = cars; }
    }

    public static class EmbeddedCar {
        private String carId;
        private String model;

        public EmbeddedCar() {}
        public EmbeddedCar(String carId, String model) {
            this.carId = carId;
            this.model = model;
        }

        public String getCarId() { return carId; }
        public void setCarId(String carId) { this.carId = carId; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
    }

    public static class ReviewStats {
        @Field("average_rating") private double averageRating;
        private int count;

        public double getAverageRating() { return averageRating; }
        public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
    }

    // --- User Getters/Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public PersonalInfo getPersonalInfo() { return personalInfo; }
    public void setPersonalInfo(PersonalInfo personalInfo) { this.personalInfo = personalInfo; }
    public List<UserDocument> getDocuments() { return documents; }
    public void setDocuments(List<UserDocument> documents) { this.documents = documents; }
    public DriverInfo getDriverInfo() { return driverInfo; }
    public void setDriverInfo(DriverInfo driverInfo) { this.driverInfo = driverInfo; }
    public ReviewStats getReviewsDriver() { return reviewsDriver; }
    public void setReviewsDriver(ReviewStats reviewsDriver) { this.reviewsDriver = reviewsDriver; }
    public ReviewStats getReviewsPassenger() { return reviewsPassenger; }
    public void setReviewsPassenger(ReviewStats reviewsPassenger) { this.reviewsPassenger = reviewsPassenger; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}