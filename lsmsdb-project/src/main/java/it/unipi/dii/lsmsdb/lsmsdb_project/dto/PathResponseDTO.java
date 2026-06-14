package it.unipi.dii.lsmsdb.lsmsdb_project.dto;

import java.util.List;

public class PathResponseDTO {
    private List<String> path;
    private Double estimatedCost;

    public PathResponseDTO(List<String> path, Double estimatedCost) {
        this.path = path;
        this.estimatedCost = estimatedCost;
    }

    public List<String> getPath() { return path; }
    public void setPath(List<String> path) { this.path = path; }

    public Double getEstimatedCost() { return estimatedCost; }
    public void setEstimatedCost(Double estimatedCost) { this.estimatedCost = estimatedCost; }
}