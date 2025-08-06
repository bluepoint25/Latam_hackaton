package com.fitneservice.fitneservice.service;

import com.fitneservice.fitneservice.dto.*;
import com.fitneservice.fitneservice.entity.Activity;
import com.fitneservice.fitneservice.entity.ActivityStatus;
import com.fitneservice.fitneservice.entity.ActivityType;
import com.fitneservice.fitneservice.entity.TrackPoint;
import com.fitneservice.fitneservice.entity.User;
import com.fitneservice.fitneservice.repository.ActivityRepository;
import com.fitneservice.fitneservice.repository.UserRepository;
import com.fitneservice.fitneservice.service.GeoLocationService.ActivityMetrics;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ActivityService {

    // Constructor injection (mejor práctica)
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final GeoLocationService geoLocationService;
    private final UserService userService;

    public ActivityService(
            ActivityRepository activityRepository,
            UserRepository userRepository,
            GeoLocationService geoLocationService,
            UserService userService
    ) {
        this.activityRepository = activityRepository;
        this.userRepository = userRepository;
        this.geoLocationService = geoLocationService;
        this.userService = userService;
    }

    /**
     * Crear una nueva actividad con puntos GPS
     */
    public ActivityResponseDTO createActivity(ActivityRequestDTO request) {
        try {
            // Verificar que el usuario existe
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Crear la actividad
            Activity activity = new Activity();
            activity.setUser(user);
            activity.setStartTime(request.getStartTime());
            activity.setEndTime(request.getEndTime());
            activity.setActivityType(request.getActivityType());
            activity.setDurationMinutes(request.getDurationMinutes());
            activity.setNotes(request.getNotes());
            activity.setPhotoUrl(request.getPhotoUrl());
            activity.setEmoticon(request.getEmoticon());
            activity.setStatus(ActivityStatus.COMPLETED);

            // Guardar la actividad primero
            Activity savedActivity = activityRepository.save(activity);

            // Convertir y guardar puntos GPS
            List<TrackPoint> trackPoints = request.getTrackPoints().stream()
                    .map(dto -> {
                        TrackPoint point = new TrackPoint();
                        point.setActivity(savedActivity);
                        point.setLatitude(dto.getLatitude());
                        point.setLongitude(dto.getLongitude());
                        // Convertir LocalDateTime a Long (milisegundos)
                        point.setTimestamp(dto.getTimestamp().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                        point.setElevation(dto.getAltitude());
                        point.setAccuracy(dto.getAccuracy());
                        point.setSpeed(dto.getSpeed());
                        return point;
                    })
                    .toList();

            geoLocationService.saveTrackPoints(savedActivity, trackPoints);

            // Calcular métricas basadas en GPS
            ActivityMetrics metrics = geoLocationService.calculateActivityMetrics(
                    savedActivity.getId(),
                    request.getActivityType().toString(),
                    request.getDurationMinutes(),
                    user.getWeight() != null ? user.getWeight() : 70.0
            );

            // Actualizar actividad con métricas calculadas
            savedActivity.setDistanceKm(metrics.getDistanceKm());
            savedActivity.setCaloriesBurned(metrics.getCaloriesBurned().doubleValue());
            savedActivity.setAveragePace(metrics.getAveragePace());
            savedActivity.setMaxSpeed(metrics.getMaxSpeed());
            savedActivity.setPointsEarned(metrics.getPointsEarned());

            activityRepository.save(savedActivity);

            // Actualizar puntos del usuario
            userService.addPointsToUser(user.getId(), metrics.getPointsEarned());

            // Actualizar racha del usuario
            userService.updateUserStreak(user.getId());

            // Crear respuesta con métricas
            ActivityMetricsDTO metricsDTO = new ActivityMetricsDTO();
            metricsDTO.setDistanceKm(metrics.getDistanceKm());
            metricsDTO.setCaloriesBurned(metrics.getCaloriesBurned());
            metricsDTO.setAveragePace(metrics.getAveragePace());
            metricsDTO.setMaxSpeed(metrics.getMaxSpeed());
            metricsDTO.setPointsEarned(metrics.getPointsEarned());
            metricsDTO.setElevationGain(metrics.getElevationGain());
            metricsDTO.setAverageSpeed(metrics.getAverageSpeed());

            return new ActivityResponseDTO(true, "Actividad guardada exitosamente",
                    savedActivity.getId(), metricsDTO);

        } catch (Exception e) {
            return new ActivityResponseDTO(false, "Error al guardar actividad: " + e.getMessage(),
                    null, null);
        }
    }

    /**
     * Obtener actividad por ID
     */
    @Transactional(readOnly = true)
    public Optional<ActivityDTO> getActivityById(Long id) {
        return activityRepository.findById(id).map(activity -> {
            ActivityDTO dto = new ActivityDTO(activity);

            // Agregar puntos GPS si es necesario
            List<TrackPoint> trackPoints = geoLocationService.getActivityTrackPoints(id);
            List<TrackPointDTO> trackPointDTOs = trackPoints.stream()
                    .map(TrackPointDTO::new)
                    .toList();
            dto.setTrackPoints(trackPointDTOs);

            return dto;
        });
    }

    /**
     * Obtener actividades de un usuario
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getUserActivities(Long userId) {
        return activityRepository.findByUserIdOrderByStartTimeDesc(userId)
                .stream()
                .map(ActivityDTO::new)
                .toList();
    }

    /**
     * Obtener actividades recientes de un usuario
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getUserRecentActivities(Long userId, int limit) {
        return activityRepository.findByUserIdOrderByStartTimeDesc(userId)
                .stream()
                .limit(limit)
                .map(ActivityDTO::new)
                .toList();
    }

    /**
     * Obtener actividades por tipo
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getUserActivitiesByType(Long userId, ActivityType activityType) {
        List<Activity> activities = activityRepository.findByUserIdAndActivityType(userId, activityType);
        return activities.stream().map(ActivityDTO::new).toList();
    }

    /**
     * Obtener actividades desde una fecha
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getUserActivitiesFromDate(Long userId, LocalDateTime startDate) {
        return activityRepository.findUserActivitiesFromDate(userId, startDate)
                .stream()
                .map(ActivityDTO::new)
                .toList();
    }

    /**
     * Obtener actividades de hoy
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getTodayActivities(Long userId) {
        return activityRepository.findTodayActivitiesByUser(userId)
                .stream()
                .map(ActivityDTO::new)
                .toList();
    }

    /**
     * Obtener actividades de esta semana
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getThisWeekActivities(Long userId) {
        return activityRepository.findThisWeekActivitiesByUser(userId)
                .stream()
                .map(ActivityDTO::new)
                .toList();
    }

    /**
     * Obtener actividades de este mes
     */
    @Transactional(readOnly = true)
    public List<ActivityDTO> getThisMonthActivities(Long userId) {
        return activityRepository.findThisMonthActivitiesByUser(userId)
                .stream()
                .map(ActivityDTO::new)
                .toList();
    }

    /**
     * Actualizar actividad
     */
    public ActivityDTO updateActivity(Long activityId, ActivityDTO activityDTO) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        activity.setNotes(activityDTO.getNotes());
        activity.setPhotoUrl(activityDTO.getPhotoUrl());
        activity.setEmoticon(activityDTO.getEmoticon());

        Activity updatedActivity = activityRepository.save(activity);
        return new ActivityDTO(updatedActivity);
    }

    /**
     * Eliminar actividad
     */
    public void deleteActivity(Long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));

        // Restar puntos del usuario
        User user = activity.getUser();
        user.setTotalPoints(user.getTotalPoints() - (activity.getPointsEarned() != null ? activity.getPointsEarned() : 0));
        userRepository.save(user);

        // Eliminar puntos GPS
        geoLocationService.deleteActivityTrackPoints(activityId);

        // Eliminar actividad
        activityRepository.delete(activity);
    }

    /**
     * Obtener estadísticas de actividades del usuario
     */
    @Transactional(readOnly = true)
    public ActivityStatsDTO getUserActivityStats(Long userId) {
        ActivityStatsDTO stats = new ActivityStatsDTO();
        stats.setUserId(userId);

        // Estadísticas totales
        stats.setTotalDistance(activityRepository.getTotalDistanceByUser(userId));
        stats.setTotalDuration(activityRepository.getTotalDurationByUser(userId));
        
        // Convertir Double a Integer para calorías
        Double totalCaloriesDouble = activityRepository.getTotalCaloriesByUser(userId);
        stats.setTotalCalories(totalCaloriesDouble != null ? totalCaloriesDouble.intValue() : 0);
        
        stats.setTotalPoints(activityRepository.getTotalPointsByUser(userId));

        // Contar actividades por tipo (usando los valores correctos del enum)
        for (ActivityType type : ActivityType.values()) {
            int count = activityRepository.findByUserIdAndActivityType(userId, type).size();
            switch (type) {
                case RUNNING:
                    stats.setRunningActivities(count);
                    stats.setMaxRunningDistance(activityRepository.getMaxDistanceByUserAndType(userId, type));
                    stats.setBestRunningPace(activityRepository.getBestPaceByUserAndType(userId, type));
                    break;
                case WALKING:
                    stats.setWalkingActivities(count);
                    break;
                case CYCLING:
                    stats.setCyclingActivities(count);
                    break;
                case HIKING:
                    // Si no tienes setter específico, usa otherActivities
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case DANCING:
                    // Si no tienes setter específico, usa otherActivities
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case SOCCER:
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case BASKETBALL:
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case TENNIS:
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case BOXING:
                    stats.setGymActivities((stats.getGymActivities() != null ? stats.getGymActivities() : 0) + count);
                    break;
                case CLIMBING:
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case SKIING:
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                case SURFING:
                    stats.setOtherActivities((stats.getOtherActivities() != null ? stats.getOtherActivities() : 0) + count);
                    break;
                default:
                    break;
            }
        }

        // Actividades recientes
        LocalDateTime lastWeek = LocalDateTime.now().minusWeeks(1);
        stats.setActivitiesThisWeek(activityRepository.countUserActivitiesFromDate(userId, lastWeek).intValue());

        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        stats.setActivitiesThisMonth(activityRepository.countUserActivitiesFromDate(userId, lastMonth).intValue());

        return stats;
    }

    /**
     * Obtener puntos GPS de una actividad
     */
    @Transactional(readOnly = true)
    public List<TrackPointDTO> getActivityTrackPoints(Long activityId) {
        return geoLocationService.getActivityTrackPoints(activityId)
                .stream()
                .map(TrackPointDTO::new)
                .toList();
    }

    /**
     * Obtener estadísticas de ruta de una actividad
     */
    @Transactional(readOnly = true)
    public GeoLocationService.RouteStats getActivityRouteStats(Long activityId) {
        return geoLocationService.calculateRouteStats(activityId);
    }
}