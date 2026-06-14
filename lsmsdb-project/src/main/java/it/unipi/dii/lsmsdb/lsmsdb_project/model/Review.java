package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
public class Review {
    @Id
    private String id;

    private String rideId;
    private String authorId;
    private String targetUserId;

    private String comment;
    private int rating;
    private String date;
    private String role; // "DRIVER_REVIEW" o "PASSENGER_REVIEW"

    public Review() {
        this.date = LocalDateTime.now().toString();
    }

    // Getters/Setters standard
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRideId() { return rideId; }
    public void setRideId(String rideId) { this.rideId = rideId; }
    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }
    public String getTargetUserId() { return targetUserId; }
    public void setTargetUserId(String targetUserId) { this.targetUserId = targetUserId; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}