package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

// TTL: 24h = 86400 s
@RedisHash(value = "sessions", timeToLive = 86400)
public class Session implements Serializable {

    @Id
    private String id; // Session token (UUID)

    @Indexed
    private String userId;
    private String username;

    public Session() {}

    public Session(String id, String userId, String username) {
        this.id = id;
        this.userId = userId;
        this.username = username;
    }

    // Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}