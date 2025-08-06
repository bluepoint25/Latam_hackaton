package com.fitneservice.fitneservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "track_points")
public class TrackPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id", nullable = false)
    @NotNull
    private Activity activity;

    @Column(name = "latitude", nullable = false)
    @NotNull
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    @NotNull
    private Double longitude;

    @Column(name = "elevation")
    private Double elevation;

    @Column(name = "timestamp", nullable = false)
    @NotNull
    private Long timestamp; // Unix timestamp en milisegundos

    @Column(name = "speed")
    private Double speed; // en km/h

    @Column(name = "accuracy")
    private Double accuracy; // precisión del GPS en metros

    @Column(name = "bearing")
    private Double bearing; // dirección en grados

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Constructores
    public TrackPoint() {
        this.createdAt = LocalDateTime.now();
    }

    public TrackPoint(Activity activity, Double latitude, Double longitude) {
        this();
        this.activity = activity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getElevation() {
        return elevation;
    }

    public void setElevation(Double elevation) {
        this.elevation = elevation;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public Double getBearing() {
        return bearing;
    }

    public void setBearing(Double bearing) {
        this.bearing = bearing;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}