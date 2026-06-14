package it.unipi.dii.lsmsdb.lsmsdb_project.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.BookingRequestDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.model.Booking;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.BookingAnalyticsService;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.BookingProcessService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired private BookingProcessService processService;
    @Autowired private BookingAnalyticsService analyticsService;

    // POST http://localhost:8080/api/bookings/request
    @PostMapping("/request")
    public ResponseEntity<String> requestBooking(@RequestBody BookingRequestDTO dto) {
        String reqId = processService.createTemporaryReservation(dto);
        return ResponseEntity.ok(reqId); //temporary request Id
    }

    // POST http://localhost:8080/api/bookings/finalize/req_12345
    @PostMapping("/finalize/{requestId}") //Mongo Atomic
    public ResponseEntity<?> finalizeBooking(@PathVariable String requestId) {
        try {
            Booking booking = processService.finalizeBooking(requestId);
            return ResponseEntity.ok(booking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore during prenotation: " + e.getMessage());
        }
    }

    // GET /api/bookings/analytics/revenue?start=2025-01-01&end=2025-12-31
    @GetMapping("/analytics/revenue")
    public Document getRevenue(@RequestParam String start, @RequestParam String end) {
        return analyticsService.getRevenueStats(start, end);
    }

    // GET /api/bookings/analytics/churners
    @GetMapping("/analytics/churners")
    public List<Document> getChurners() {
        return analyticsService.getHighValueChurners(30);
    }

    // GET /api/bookings/analytics/leaderboard
    @GetMapping("/analytics/leaderboard")
    public List<Document> getLeaderboard() {
        return analyticsService.getTopDriverLeaderboard();
    }
}