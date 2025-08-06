package com.fitneservice.fitneservice.dto;

import com.fitneservice.fitneservice.entity.TrackPoint;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TrackPointDTO {
    
    private Long id;
    private Long activityId;
    private Double latitude;
    private Double longitude;
    private LocalDateTime timestamp;
    private Double altitude;
    private Double accuracy;
    private Double speed;
    
    // Constructors
    public TrackPointDTO() {}

    public TrackPointDTO(TrackPoint trackPoint) {
        this.id = trackPoint.getId();
        this.activityId = trackPoint.getActivity().getId();
        this.latitude = trackPoint.getLatitude();
        this.longitude = trackPoint.getLongitude();

        // Conversión de Long (epoch millis) a LocalDateTime
        this.timestamp = Instant.ofEpochMilli(trackPoint.getTimestamp())
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();

        // Usar getElevation() en vez de getAltitude()
        this.altitude = trackPoint.getElevation();

        this.accuracy = trackPoint.getAccuracy();
        this.speed = trackPoint.getSpeed();
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public Double getAltitude() { return altitude; }
    public void setAltitude(Double altitude) { this.altitude = altitude; }
    
    public Double getAccuracy() { return accuracy; }
    public void setAccuracy(Double accuracy) { this.accuracy = accuracy; }
    
    public Double getSpeed() { return speed; }
    public void setSpeed(Double speed) { this.speed = speed; }
}