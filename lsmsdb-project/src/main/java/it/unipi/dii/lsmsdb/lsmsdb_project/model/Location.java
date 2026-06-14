package it.unipi.dii.lsmsdb.lsmsdb_project.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Location")
public class Location {
    @Id
    private String name; // Pisa, Firenze...
    private Double lat;
    private Double lon;

    public Location() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getLat() { return lat; }
    public void setLat(Double lat) { this.lat = lat; }
    public Double getLon() { return lon; }
    public void setLon(Double lon) { this.lon = lon; }
}