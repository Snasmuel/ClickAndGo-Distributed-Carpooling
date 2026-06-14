package it.unipi.dii.lsmsdb.lsmsdb_project.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Review;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByTargetUserId(String targetUserId);
    List<Review> findByAuthorId(String authorId);
}