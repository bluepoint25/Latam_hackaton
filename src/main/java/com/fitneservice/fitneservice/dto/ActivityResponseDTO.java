package com.fitneservice.fitneservice.dto;

public class ActivityResponseDTO {
    
    private boolean success;
    private String message;
    private Long activityId;
    private ActivityMetricsDTO metrics;
    
    // Constructors
    public ActivityResponseDTO() {}
    
    public ActivityResponseDTO(boolean success, String message, Long activityId, ActivityMetricsDTO metrics) {
        this.success = success;
        this.message = message;
        this.activityId = activityId;
        this.metrics = metrics;
    }
    
    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }
    
    public ActivityMetricsDTO getMetrics() { return metrics; }
    public void setMetrics(ActivityMetricsDTO metrics) { this.metrics = metrics; }
}