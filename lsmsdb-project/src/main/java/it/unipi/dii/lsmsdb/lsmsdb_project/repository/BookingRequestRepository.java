package it.unipi.dii.lsmsdb.lsmsdb_project.repository;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.BookingRequest;

@Repository
public class BookingRequestRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public BookingRequestRepository(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(BookingRequest request) {
        String key = "req_" + request.getId();

        // Master writing
        redisTemplate.opsForValue().set(key, request, Duration.ofMinutes(30));

        // Wait for replicas (CP)
        Long replicasAcknowledged = redisTemplate.execute((RedisCallback<Long>) connection -> {
            String luaScript = "return redis.call('WAIT', 2, 1000)";
            return connection.eval(
                    luaScript.getBytes(StandardCharsets.UTF_8),
                    ReturnType.INTEGER, // we use this number in order to check the status
                    0
            );
        });

        if (replicasAcknowledged == null || replicasAcknowledged < 2) {
            // ROLLBACK
            redisTemplate.delete(key);

            throw new RuntimeException("CONSISTENCY ERROR: Redis WAIT failed. " +
                    "Expected 2 replicas, got " + replicasAcknowledged + ". " +
                    "Booking rejected for safety.");
        }

        System.out.println("DEBUG: Redis Consistency OK. Replicated to " + replicasAcknowledged + " nodes.");
    }

    public Optional<BookingRequest> findById(String requestId) {
        Object data = redisTemplate.opsForValue().get("req_" + requestId);
        return Optional.ofNullable((BookingRequest) data);
    }

    public void deleteById(String requestId) {
        redisTemplate.delete("req_" + requestId);
    }

    public void deleteAll() {
        try {
            redisTemplate.getConnectionFactory().getConnection().serverCommands().flushDb();
        } catch (Exception e) {
        }
    }
}