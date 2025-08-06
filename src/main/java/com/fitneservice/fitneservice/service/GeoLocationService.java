package com.fitneservice.fitneservice.service;

import com.fitneservice.fitneservice.entity.Activity;
import com.fitneservice.fitneservice.entity.TrackPoint;
import com.fitneservice.fitneservice.repository.TrackPointRepository;
import com.fitneservice.fitneservice.util.GeoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GeoLocationService {

    private final TrackPointRepository trackPointRepository;

    public GeoLocationService(TrackPointRepository trackPointRepository) {
        this.trackPointRepository = trackPointRepository;
    }

    public void saveTrackPoints(Activity activity, List<TrackPoint> trackPoints) {
        for (TrackPoint point : trackPoints) {
            point.setActivity(activity);
        }
        trackPointRepository.saveAll(trackPoints);
    }

    public ActivityMetrics calculateActivityMetrics(Long activityId, String activityType,
                                                   int durationMinutes, double userWeight) {
        List<TrackPoint> trackPoints = trackPointRepository.findByActivityIdOrderByTimestampAsc(activityId);

        ActivityMetrics metrics = new ActivityMetrics();

        double distanceKm = GeoUtils.calculateTotalDistance(trackPoints);
        metrics.setDistanceKm(distanceKm);

        double averageSpeed = GeoUtils.calculateAverageSpeed(distanceKm, durationMinutes);
        metrics.setAverageSpeed(averageSpeed);

        double averagePace = GeoUtils.calculateAveragePace(distanceKm, durationMinutes);
        metrics.setAveragePace(averagePace);

        Double maxSpeed = trackPointRepository.getMaxSpeedByActivity(activityId);
        metrics.setMaxSpeed(maxSpeed != null ? maxSpeed : 0.0);

        double elevationGain = GeoUtils.calculateElevationGain(trackPoints);
        metrics.setElevationGain(elevationGain);

        // CORREGIDO: conversión double a int
        int caloriesBurned = (int) Math.round(GeoUtils.calculateCalories(activityType, durationMinutes, distanceKm, userWeight));
        metrics.setCaloriesBurned(caloriesBurned);

        int pointsEarned = GeoUtils.calculatePoints(activityType, distanceKm, durationMinutes, caloriesBurned);
        metrics.setPointsEarned(pointsEarned);

        return metrics;
    }

    @Transactional(readOnly = true)
    public List<TrackPoint> getActivityTrackPoints(Long activityId) {
        return trackPointRepository.findByActivityIdOrderByTimestampAsc(activityId);
    }

    @Transactional(readOnly = true)
    public RouteStats calculateRouteStats(Long activityId) {
        List<TrackPoint> trackPoints = trackPointRepository.findByActivityIdOrderByTimestampAsc(activityId);

        if (trackPoints.isEmpty()) {
            return new RouteStats();
        }

        RouteStats stats = new RouteStats();

        TrackPoint startPoint = trackPoints.get(0);
        TrackPoint endPoint = trackPoints.get(trackPoints.size() - 1);

        stats.setStartLatitude(startPoint.getLatitude());
        stats.setStartLongitude(startPoint.getLongitude());
        stats.setEndLatitude(endPoint.getLatitude());
        stats.setEndLongitude(endPoint.getLongitude());

        double[] center = GeoUtils.calculateCenter(trackPoints);
        stats.setCenterLatitude(center[0]);
        stats.setCenterLongitude(center[1]);

        Double maxAltitude = trackPointRepository.getMaxAltitudeByActivity(activityId);
        Double minAltitude = trackPointRepository.getMinAltitudeByActivity(activityId);

        stats.setMaxAltitude(maxAltitude != null ? maxAltitude : 0.0);
        stats.setMinAltitude(minAltitude != null ? minAltitude : 0.0);

        stats.setTotalPoints(trackPoints.size());

        return stats;
    }

    public void deleteActivityTrackPoints(Long activityId) {
        trackPointRepository.deleteByActivityId(activityId);
    }

    public static class ActivityMetrics {
        private Double distanceKm;
        private Double averageSpeed;
        private Double averagePace;
        private Double maxSpeed;
        private Double elevationGain;
        private Integer caloriesBurned;
        private Integer pointsEarned;

        // Getters y Setters
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

    public static class RouteStats {
        private Double startLatitude;
        private Double startLongitude;
        private Double endLatitude;
        private Double endLongitude;
        private Double centerLatitude;
        private Double centerLongitude;
        private Double maxAltitude;
        private Double minAltitude;
        private Integer totalPoints;

        // Getters y Setters
        public Double getStartLatitude() { return startLatitude; }
        public void setStartLatitude(Double startLatitude) { this.startLatitude = startLatitude; }

        public Double getStartLongitude() { return startLongitude; }
        public void setStartLongitude(Double startLongitude) { this.startLongitude = startLongitude; }

        public Double getEndLatitude() { return endLatitude; }
        public void setEndLatitude(Double endLatitude) { this.endLatitude = endLatitude; }

        public Double getEndLongitude() { return endLongitude; }
        public void setEndLongitude(Double endLongitude) { this.endLongitude = endLongitude; }

        public Double getCenterLatitude() { return centerLatitude; }
        public void setCenterLatitude(Double centerLatitude) { this.centerLatitude = centerLatitude; }

        public Double getCenterLongitude() { return centerLongitude; }
        public void setCenterLongitude(Double centerLongitude) { this.centerLongitude = centerLongitude; }

        public Double getMaxAltitude() { return maxAltitude; }
        public void setMaxAltitude(Double maxAltitude) { this.maxAltitude = maxAltitude; }

        public Double getMinAltitude() { return minAltitude; }
        public void setMinAltitude(Double minAltitude) { this.minAltitude = minAltitude; }

        public Integer getTotalPoints() { return totalPoints; }
        public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    }
}