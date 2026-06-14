package it.unipi.dii.lsmsdb.lsmsdb_project.controller;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.PathResponseDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired private RouteService routeService;

    // GET http://localhost:8080/api/routes/path?from=Torre&to=Stazione Pisa Centrale
    @GetMapping("/path")
    public ResponseEntity<PathResponseDTO> getShortestPath(@RequestParam String from, @RequestParam String to) {
        PathResponseDTO response = routeService.getShortestPath(from, to);

        if (response.getPath().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }
}