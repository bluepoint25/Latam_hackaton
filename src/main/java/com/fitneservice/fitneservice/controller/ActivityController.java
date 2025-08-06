package com.fitneservice.fitneservice.controller;


import com.fitneservice.fitneservice.entity.ActivityType;
import com.fitneservice.fitneservice.dto.*;
import com.fitneservice.fitneservice.service.ActivityService;
import com.fitneservice.fitneservice.service.GeoLocationService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {


    private  ActivityService activityService;

    /**
     * Crear nueva actividad
     */
    @PostMapping
    public ResponseEntity<ActivityResponseDTO> createActivity(@Valid @RequestBody ActivityRequestDTO request) {
        ActivityResponseDTO response = activityService.createActivity(request);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Obtener actividad por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ActivityDTO> getActivityById(@PathVariable Long id) {
        Optional<ActivityDTO> activity = activityService.getActivityById(id);
        return activity.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtener actividades de un usuario
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ActivityDTO>> getUserActivities(@PathVariable Long userId) {
        List<ActivityDTO> activities = activityService.getUserActivities(userId);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtener actividades recientes de un usuario
     */
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<ActivityDTO>> getUserRecentActivities(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        List<ActivityDTO> activities = activityService.getUserRecentActivities(userId, limit);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtener actividades por tipo
     */
    @GetMapping("/user/{userId}/type/{activityType}")
    public ResponseEntity<List<ActivityDTO>> getUserActivitiesByType(
            @PathVariable Long userId,
            @PathVariable ActivityType activityType) {
        List<ActivityDTO> activities = activityService.getUserActivitiesByType(userId, activityType);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtener actividades desde una fecha
     */
    @GetMapping("/user/{userId}/from-date")
    public ResponseEntity<List<ActivityDTO>> getUserActivitiesFromDate(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        List<ActivityDTO> activities = activityService.getUserActivitiesFromDate(userId, startDate);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtener actividades de hoy
     */
    @GetMapping("/user/{userId}/today")
    public ResponseEntity<List<ActivityDTO>> getTodayActivities(@PathVariable Long userId) {
        List<ActivityDTO> activities = activityService.getTodayActivities(userId);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtener actividades de esta semana
     */
    @GetMapping("/user/{userId}/this-week")
    public ResponseEntity<List<ActivityDTO>> getThisWeekActivities(@PathVariable Long userId) {
        List<ActivityDTO> activities = activityService.getThisWeekActivities(userId);
        return ResponseEntity.ok(activities);
    }

    /**
     * Obtener actividades de este mes
     */
    @GetMapping("/user/{userId}/this-month")
    public ResponseEntity<List<ActivityDTO>> getThisMonthActivities(@PathVariable Long userId) {
        List<ActivityDTO> activities = activityService.getThisMonthActivities(userId);
        return ResponseEntity.ok(activities);
    }

    /**
     * Actualizar actividad
     */
    @PutMapping("/{id}")
    public ResponseEntity<ActivityDTO> updateActivity(
            @PathVariable Long id,
            @Valid @RequestBody ActivityDTO activityDTO) {
        try {
            ActivityDTO updatedActivity = activityService.updateActivity(id, activityDTO);
            return ResponseEntity.ok(updatedActivity);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Eliminar actividad
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteActivity(@PathVariable Long id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok(new ApiResponseDTO(true, "Actividad eliminada exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener estadísticas de actividades del usuario
     */
    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<ActivityStatsDTO> getUserActivityStats(@PathVariable Long userId) {
        try {
            ActivityStatsDTO stats = activityService.getUserActivityStats(userId);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener puntos GPS de una actividad
     */
    @GetMapping("/{id}/track-points")
    public ResponseEntity<List<TrackPointDTO>> getActivityTrackPoints(@PathVariable Long id) {
        List<TrackPointDTO> trackPoints = activityService.getActivityTrackPoints(id);
        return ResponseEntity.ok(trackPoints);
    }

    /**
     * Obtener estadísticas de ruta de una actividad
     */
    @GetMapping("/{id}/route-stats")
    public ResponseEntity<GeoLocationService.RouteStats> getActivityRouteStats(@PathVariable Long id) {
        try {
            GeoLocationService.RouteStats routeStats = activityService.getActivityRouteStats(id);
            return ResponseEntity.ok(routeStats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}