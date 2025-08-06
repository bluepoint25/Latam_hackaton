// src/main/java/com/fitneservice/fitneservice/entity/Activity.java
package com.fitneservice.fitneservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ActivityStatus status;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Positive
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Positive
    @Column(name = "distance_km")
    private Double distanceKm;

    @Positive
    @Column(name = "calories_burned")
    private Double caloriesBurned;

    @Column(name = "average_speed")
    private Double averageSpeed;

    @Column(name = "max_speed")
    private Double maxSpeed;

    @Column(name = "average_pace")
    private Double averagePace;

    @Column(name = "elevation_gain")
    private Double elevationGain;

    @Column(name = "points_earned")
    private Integer pointsEarned;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "emoticon")
    private String emoticon;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrackPoint> trackPoints;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructores
    public Activity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = ActivityStatus.PLANNED; // Estado por defecto
    }

    public Activity(String name, ActivityType activityType, User user) {
        this();
        this.name = name;
        this.activityType = activityType;
        this.user = user;
    }

    // Métodos de ciclo de vida JPA
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public ActivityStatus getStatus() { return status; }
    public void setStatus(ActivityStatus status) { this.status = status; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Double getCaloriesBurned() { return caloriesBurned; }
    public void setCaloriesBurned(Double caloriesBurned) { this.caloriesBurned = caloriesBurned; }

    public Double getAverageSpeed() { return averageSpeed; }
    public void setAverageSpeed(Double averageSpeed) { this.averageSpeed = averageSpeed; }

    public Double getMaxSpeed() { return maxSpeed; }
    public void setMaxSpeed(Double maxSpeed) { this.maxSpeed = maxSpeed; }

    public Double getAveragePace() { return averagePace; }
    public void setAveragePace(Double averagePace) { this.averagePace = averagePace; }

    public Double getElevationGain() { return elevationGain; }
    public void setElevationGain(Double elevationGain) { this.elevationGain = elevationGain; }

    public Integer getPointsEarned() { return pointsEarned; }
    public void setPointsEarned(Integer pointsEarned) { this.pointsEarned = pointsEarned; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public String getEmoticon() { return emoticon; }
    public void setEmoticon(String emoticon) { this.emoticon = emoticon; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<TrackPoint> getTrackPoints() { return trackPoints; }
    public void setTrackPoints(List<TrackPoint> trackPoints) { this.trackPoints = trackPoints; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Métodos de utilidad
    public boolean isCompleted() { return this.status == ActivityStatus.COMPLETED; }
    public boolean isInProgress() { return this.status == ActivityStatus.IN_PROGRESS; }
    public boolean isPlanned() { return this.status == ActivityStatus.PLANNED; }
    public boolean isCancelled() { return this.status == ActivityStatus.CANCELLED; }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", activityType=" + activityType +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", durationMinutes=" + durationMinutes +
                ", distanceKm=" + distanceKm +
                ", caloriesBurned=" + caloriesBurned +
                ", averageSpeed=" + averageSpeed +
                ", maxSpeed=" + maxSpeed +
                ", averagePace=" + averagePace +
                ", elevationGain=" + elevationGain +
                ", pointsEarned=" + pointsEarned +
                ", notes='" + notes + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", emoticon='" + emoticon + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}