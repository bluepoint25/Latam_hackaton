package com.fitneservice.fitneservice.dto;

public class ActivityStatsDTO {
    
    private Long userId;
    private Double totalDistance;
    private Integer totalDuration;
    private Integer totalCalories;
    private Integer totalPoints;
    
    // Contadores por tipo de actividad
    private Integer runningActivities;
    private Integer walkingActivities;
    private Integer cyclingActivities;
    private Integer swimmingActivities;
    private Integer gymActivities;
    private Integer yogaActivities;
    private Integer otherActivities;
    
    // Récords personales
    private Double maxRunningDistance;
    private Double bestRunningPace;
    
    // Estadísticas temporales
    private Integer activitiesThisWeek;
    private Integer activitiesThisMonth;
    
    // Constructor vacío correcto
    public ActivityStatsDTO() {
        // Constructor por defecto
    }
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Double getTotalDistance() { return totalDistance; }
    public void setTotalDistance(Double totalDistance) { this.totalDistance = totalDistance; }
    
    public Integer getTotalDuration() { return totalDuration; }
    public void setTotalDuration(Integer totalDuration) { this.totalDuration = totalDuration; }
    
    public Integer getTotalCalories() { return totalCalories; }
    public void setTotalCalories(Integer totalCalories) { this.totalCalories = totalCalories; }
    
    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    
    public Integer getRunningActivities() { return runningActivities; }
    public void setRunningActivities(Integer runningActivities) { this.runningActivities = runningActivities; }
    
    public Integer getWalkingActivities() { return walkingActivities; }
    public void setWalkingActivities(Integer walkingActivities) { this.walkingActivities = walkingActivities; }
    
    public Integer getCyclingActivities() { return cyclingActivities; }
    public void setCyclingActivities(Integer cyclingActivities) { this.cyclingActivities = cyclingActivities; }
    
    public Integer getSwimmingActivities() { return swimmingActivities; }
    public void setSwimmingActivities(Integer swimmingActivities) { this.swimmingActivities = swimmingActivities; }
    
    public Integer getGymActivities() { return gymActivities; }
    public void setGymActivities(Integer gymActivities) { this.gymActivities = gymActivities; }
    
    public Integer getYogaActivities() { return yogaActivities; }
    public void setYogaActivities(Integer yogaActivities) { this.yogaActivities = yogaActivities; }
    
    public Integer getOtherActivities() { return otherActivities; }
    public void setOtherActivities(Integer otherActivities) { this.otherActivities = otherActivities; }
    
    public Double getMaxRunningDistance() { return maxRunningDistance; }
    public void setMaxRunningDistance(Double maxRunningDistance) { this.maxRunningDistance = maxRunningDistance; }
    
    public Double getBestRunningPace() { return bestRunningPace; }
    public void setBestRunningPace(Double bestRunningPace) { this.bestRunningPace = bestRunningPace; }
    
    public Integer getActivitiesThisWeek() { return activitiesThisWeek; }
    public void setActivitiesThisWeek(Integer activitiesThisWeek) { this.activitiesThisWeek = activitiesThisWeek; }
    
    public Integer getActivitiesThisMonth() { return activitiesThisMonth; }
    public void setActivitiesThisMonth(Integer activitiesThisMonth) { this.activitiesThisMonth = activitiesThisMonth; }
}