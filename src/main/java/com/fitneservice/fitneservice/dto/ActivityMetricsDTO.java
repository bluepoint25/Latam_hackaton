package com.fitneservice.fitneservice.dto;

public class ActivityMetricsDTO {
    
    private Double distanceKm;
    private Double averageSpeed;
    private Double averagePace;
    private Double maxSpeed;
    private Double elevationGain;
    private Integer caloriesBurned;
    private Integer pointsEarned;
    
    // Constructors
    public ActivityMetricsDTO() {}
    
    // Getters and Setters
    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    
    public Double getAverageSpeed() { return averageSpeed; }
    public void setAverageSpeed(Double averageSpeed) { this.averageSpeed = averageSpeed; }
    
    public Double getAveragePace() { return averagePace; }
    public void setAveragePace(Double averagePace) { this.averagePace = averagePace; }
    
    public Double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(Double maxSpeed) { this.maxSpeed = maxSpeed; }
    
    public Double getElevationGain() { return elevationGain; }
    public void setElevationGain(Double elevationGain) { this.elevationGain = elevationGain; }
    
    public Integer getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Integer caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    
    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
}