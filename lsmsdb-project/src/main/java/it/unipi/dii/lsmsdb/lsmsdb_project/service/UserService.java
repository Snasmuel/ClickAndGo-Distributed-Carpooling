package it.unipi.dii.lsmsdb.lsmsdb_project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.UserSummaryDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.User;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.UserRepository;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserSummaryDTO getPublicProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserSummaryDTO dto = new UserSummaryDTO();
        dto.setId(user.getId());
        dto.setName(user.getPersonalInfo().getName());
        dto.setCity(user.getPersonalInfo().getLocation());

        if (user.getReviewsDriver() != null) {
            dto.setAverageRating(user.getReviewsDriver().getAverageRating());
            dto.setTotalReviews(user.getReviewsDriver().getCount());
        }

        return dto;
    }
}