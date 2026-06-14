package it.unipi.dii.lsmsdb.lsmsdb_project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.UserSummaryDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.User;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }

    // Public view optimized endpoinf
    @GetMapping("/{id}/summary")
    public ResponseEntity<UserSummaryDTO> getUserSummary(@PathVariable String id) {
        return ResponseEntity.ok(userService.getPublicProfile(id));
    }
}