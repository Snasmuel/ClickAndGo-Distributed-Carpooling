package it.unipi.dii.lsmsdb.lsmsdb_project.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.unipi.dii.lsmsdb.lsmsdb_project.model.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, String> {
    List<Notification> findByRecipientUserId(String recipientUserId);
}