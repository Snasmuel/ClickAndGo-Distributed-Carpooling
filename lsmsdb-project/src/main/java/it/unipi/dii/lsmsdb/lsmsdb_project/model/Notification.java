package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

// TTL 7 days = 604800 s
@RedisHash(value = "notifications", timeToLive = 604800)
public class Notification implements Serializable {

    @Id
    private String id;

    @Indexed // ESSENTIAL: It allows us to do findByRecipientUserId
    private String recipientUserId;

    private String type; // es. "BOOKING_CONFIRMED", "RIDE_CANCELLED"
    private String message;
    private String timestamp;
    private boolean read;

    public Notification() {
    }

    public Notification(String recipientUserId, String type, String message) {
        this.recipientUserId = recipientUserId;
        this.type = type;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
        this.read = false;
    }

    // --- GETTERS E SETTERS ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRecipientUserId() { return recipientUserId; }
    public void setRecipientUserId(String recipientUserId) { this.recipientUserId = recipientUserId; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}