package com.fitneservice.fitneservice.util;

import com.fitneservice.fitneservice.entity.TrackPoint;
import com.fitneservice.fitneservice.entity.ActivityType;
import java.util.List;

public final class GeoUtils {
    
    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final String USER_NOT_FOUND = "Usuario no encontrado";

    private GeoUtils() {
        // Constructor privado para clase utilitaria
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public static double calculateTotalDistance(List<TrackPoint> points) {
        if (points == null || points.size() < 2) {
            return 0.0;
        }

        double totalDistance = 0.0;
        for (int i = 1; i < points.size(); i++) {
            TrackPoint prev = points.get(i - 1);
            TrackPoint curr = points.get(i);
            totalDistance += calculateDistance(
                prev.getLatitude(), prev.getLongitude(),
                curr.getLatitude(), curr.getLongitude()
            );
        }
        return totalDistance;
    }

    public static double calculateAverageSpeed(double distance, int duration) {
        return duration > 0 ? (distance / duration) * 3600 : 0.0; // km/h
    }

    public static double calculateAveragePace(double distance, int duration) {
        return distance > 0 ? duration / distance : 0.0; // min/km
    }

    public static int calculateElevationGain(List<TrackPoint> points) {
        if (points == null || points.size() < 2) {
            return 0;
        }

        double totalGain = 0.0;
        for (int i = 1; i < points.size(); i++) {
            double elevationDiff = points.get(i).getElevation() - points.get(i - 1).getElevation();
            if (elevationDiff > 0) {
                totalGain += elevationDiff;
            }
        }
        return (int) Math.round(totalGain);
    }

    public static double calculateCalories(String activityType, int duration, double weight, double distance) {
        try {
            ActivityType type = ActivityType.valueOf(activityType.toUpperCase());
            double met = getMetValue(type);
            double hours = duration / 3600.0;
            return met * weight * hours;
        } catch (IllegalArgumentException e) {
            return 0.0;
        }
    }

    private static double getMetValue(ActivityType activityType) {
        return switch (activityType) {
            case RUNNING -> 8.0;
            case WALKING -> 3.5;
            case CYCLING -> 6.0;
            case HIKING -> 6.0;
            case DANCING -> 4.8;
            case SOCCER -> 7.0;
            case BASKETBALL -> 6.5;
            case TENNIS -> 7.3;
            case BOXING -> 12.0;
            case CLIMBING -> 8.0;
            case SKIING -> 7.0;
            case SURFING -> 3.0;
            default -> 4.0;
        };
    }

    public static int calculatePoints(String activityType, double distance, int duration, int elevationGain) {
        try {
            ActivityType type = ActivityType.valueOf(activityType.toUpperCase());
            int basePoints = getBasePoints(type);
            
            // Puntos por distancia
            int distancePoints = (int) (distance * 10);
            
            // Puntos por duración (1 punto por minuto)
            int durationPoints = duration / 60;
            
            // Puntos por elevación
            int elevationPoints = elevationGain / 10;
            
            return basePoints + distancePoints + durationPoints + elevationPoints;
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    private static int getBasePoints(ActivityType activityType) {
        return switch (activityType) {
            case RUNNING -> 50;
            case WALKING -> 20;
            case CYCLING -> 40;
            case HIKING -> 60;
            case DANCING -> 30;
            case SOCCER -> 70;
            case BASKETBALL -> 60;
            case TENNIS -> 65;
            case BOXING -> 80;
            case CLIMBING -> 90;
            case SKIING -> 70;
            case SURFING -> 40;
            default -> 25;
        };
    }

    public static double[] calculateCenter(List<TrackPoint> points) {
        if (points == null || points.isEmpty()) {
            return new double[]{0.0, 0.0};
        }

        double sumLat = 0.0;
        double sumLon = 0.0;
        
        for (TrackPoint point : points) {
            sumLat += point.getLatitude();
            sumLon += point.getLongitude();
        }
        
        return new double[]{
            sumLat / points.size(),
            sumLon / points.size()
        };
    }
}