package it.unipi.dii.lsmsdb.lsmsdb_project.service;

import it.unipi.dii.lsmsdb.lsmsdb_project.dto.PathResponseDTO;
import it.unipi.dii.lsmsdb.lsmsdb_project.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class RouteService {

    @Autowired private RouteRepository routeRepository;

    public PathResponseDTO getShortestPath(String from, String to) {
        return routeRepository.findShortestPath(from, to)
                .map(result -> new PathResponseDTO(result.getPathNames(), result.getTotalCost()))
                .orElse(new PathResponseDTO(Collections.emptyList(), 0.0));
    }
}