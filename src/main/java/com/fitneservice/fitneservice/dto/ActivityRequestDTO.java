package com.fitneservice.fitneservice.dto;

import com.fitneservice.fitneservice.entity.ActivityType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class ActivityRequestDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime startTime;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime endTime;
    
    @NotNull(message = "El tipo de actividad es obligatorio")
    private ActivityType activityType;
    
    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser positiva")
    private Integer durationMinutes;
    
    @Size(max = 1000, message = "Las notas no pueden tener más de 1000 caracteres")
    private String notes;
    
    private String photoUrl;
    
    @Size(max = 10, message = "El emoticon no puede tener más de 10 caracteres")
    private String emoticon;
    
    @Valid
    private List<TrackPointDTO> trackPoints;
    
    // Constructors
    public ActivityRequestDTO() {}
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }
    
    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
    
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getPhotoUrl() { return photoUrl; }
    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }
    
    public String getEmoticon() { return emoticon; }
    public void setEmoticon(String emoticon) { this.emoticon = emoticon; }
    
    public List<TrackPointDTO> getTrackPoints() { return trackPoints; }
    public void setTrackPoints(List<TrackPointDTO> trackPoints) { this.trackPoints = trackPoints; }
}