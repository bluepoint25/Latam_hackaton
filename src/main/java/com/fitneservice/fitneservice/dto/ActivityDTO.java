package com.fitneservice.fitneservice.dto;

import com.fitneservice.fitneservice.entity.ActivityType;
import com.fitneservice.fitneservice.entity.ActivityStatus;
import com.fitneservice.fitneservice.entity.Activity;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityDTO {
    
    private Long id;
    private Long userId;
    private String userFullName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ActivityType activityType;
    private Integer durationMinutes;
    private Double distanceKm;
    private Double caloriesBurned;
    private Double averagePace;
    private Double maxSpeed;
    private Integer pointsEarned;
    private ActivityStatus status; // <-- Cambiado aquí
    private String notes;
    private String photoUrl;
    private String emoticon;
    private LocalDateTime createdAt;
    private List<TrackPointDTO> trackPoints;
    
    // Constructors
    public ActivityDTO() {}
    
    public ActivityDTO(Activity activity) {
        this.id = activity.getId();
        this.userId = activity.getUser().getId();
        this.userFullName = activity.getUser().getFullName();
        this.startTime = activity.getStartTime();
        this.endTime = activity.getEndTime();
        this.activityType = activity.getActivityType();
        this.durationMinutes = activity.getDurationMinutes();
        this.distanceKm = activity.getDistanceKm();
        this.caloriesBurned = activity.getCaloriesBurned();
        this.averagePace = activity.getAveragePace();
        this.maxSpeed = activity.getMaxSpeed();
        this.pointsEarned = activity.getPointsEarned();
        this.status = activity.getStatus(); // <-- Cambiado aquí
        this.notes = activity.getNotes();
        this.photoUrl = activity.getPhotoUrl();
        this.emoticon = activity.getEmoticon();
        this.createdAt = activity.getCreatedAt();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getUserFullName() { return userFullName; }
    public void setUserFullName(String userFullName) { this.userFullName = userFullName; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }
    
    public Double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Double caloriesBurned) { this.caloriesBurned = caloriesBurned; }
    
    public Double getAveragePace() { return averagePace; }
    public void setAveragePace(Double averagePace) { this.averagePace = averagePace; }
    
    public Double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(Double maxSpeed) { this.maxSpeed = maxSpeed; }
    
    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }
    
    public ActivityStatus getStatus() { return status; } // <-- Cambiado aquí
    public void setStatus(ActivityStatus status) { this.status = status; } // <-- Cambiado aquí
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public String getEmoticon() { return emoticon; }
    public void setEmoticon(String emoticon) { this.emoticon = emoticon; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public List<TrackPointDTO> getTrackPoints() { return trackPoints; }
    public void setTrackPoints(List<TrackPointDTO> trackPoints) { this.trackPoints = trackPoints; }
}