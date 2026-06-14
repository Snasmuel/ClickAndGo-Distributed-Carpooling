package it.unipi.dii.lsmsdb.lsmsdb_project.dto;

public class UserSummaryDTO {
    private String id;
    private String name;
    private Double averageRating;
    private Integer totalReviews;
    private String city;

    public UserSummaryDTO() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public Integer getTotalReviews() { return totalReviews; }
    public void setTotalReviews(Integer totalReviews) { this.totalReviews = totalReviews; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}