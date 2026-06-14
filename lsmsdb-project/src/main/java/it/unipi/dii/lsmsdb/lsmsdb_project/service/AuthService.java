package it.unipi.dii.lsmsdb.lsmsdb_project.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.LoginRequest;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.Session;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.User;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.SessionRepository;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.UserRepository;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;
    @Autowired private SessionRepository sessionRepository;

    /**
     * Authenticates a user and creates a session in Redis.
     *
     * @param request the login request containing email and password
     * @return a randomly generated session token
     */
    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // NOTE: In a real-world scenario, passwords should be hashed (e.g., using BCrypt).
        if (!request.getPassword().equals("password123")) {
            throw new RuntimeException("Wrong password");
        }

        String token = UUID.randomUUID().toString();
        Session session = new Session(token, user.getId(), user.getPersonalInfo().getName());
        sessionRepository.save(session);

        return token;
    }

    /**
     * Logs out a user by removing their session from Redis.
     *
     * @param token the session token to invalidate
     */
    public void logout(String token) {
        sessionRepository.deleteById(token);
    }
}