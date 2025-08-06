package com.fitneservice.fitneservice.repository;

import com.fitneservice.fitneservice.entity.Activity;
import com.fitneservice.fitneservice.entity.ActivityType;
import com.fitneservice.fitneservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
    List<Activity> findByUserOrderByStartTimeDesc(User user);
    
    List<Activity> findByUserIdOrderByStartTimeDesc(Long userId);
    
    // Método estándar de Spring Data JPA para buscar por userId y activityType
    List<Activity> findByUserIdAndActivityType(Long userId, ActivityType activityType);
    
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.startTime >= :startDate ORDER BY a.startTime DESC")
    List<Activity> findUserActivitiesFromDate(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.activityType = :activityType ORDER BY a.startTime DESC")
    List<Activity> findUserActivitiesByType(@Param("userId") Long userId, @Param("activityType") ActivityType activityType);
    
    // Corregido: usar 'distance' en lugar de 'distanceKm'
    @Query("SELECT SUM(a.distance) FROM Activity a WHERE a.user.id = :userId")
    Double getTotalDistanceByUser(@Param("userId") Long userId);
    
    // Corregido: usar 'duration' en lugar de 'durationMinutes'
    @Query("SELECT SUM(a.duration) FROM Activity a WHERE a.user.id = :userId")
    Integer getTotalDurationByUser(@Param("userId") Long userId);
    
    // Corregido: cambiar tipo de retorno a Double
    @Query("SELECT SUM(a.caloriesBurned) FROM Activity a WHERE a.user.id = :userId")
    Double getTotalCaloriesByUser(@Param("userId") Long userId);
    
    @Query("SELECT SUM(a.pointsEarned) FROM Activity a WHERE a.user.id = :userId")
    Integer getTotalPointsByUser(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(a) FROM Activity a WHERE a.user.id = :userId AND a.startTime >= :startDate")
    Long countUserActivitiesFromDate(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT a FROM Activity a WHERE a.startTime >= :startDate AND a.startTime <= :endDate ORDER BY a.pointsEarned DESC")
    List<Activity> findActivitiesBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT a FROM Activity a WHERE a.user.city = :city AND a.startTime >= :startDate ORDER BY a.pointsEarned DESC")
    List<Activity> findTopActivitiesByCity(@Param("city") String city, @Param("startDate") LocalDateTime startDate);
    
    // Corregido: usar 'distance' y ActivityType como clase separada
    @Query("SELECT MAX(a.distance) FROM Activity a WHERE a.user.id = :userId AND a.activityType = :activityType")
    Double getMaxDistanceByUserAndType(@Param("userId") Long userId, @Param("activityType") ActivityType activityType);
    
    @Query("SELECT MIN(a.averagePace) FROM Activity a WHERE a.user.id = :userId AND a.activityType = :activityType AND a.averagePace > 0")
    Double getBestPaceByUserAndType(@Param("userId") Long userId, @Param("activityType") ActivityType activityType);
    
    // Corregido: usar funciones compatibles con Oracle/H2
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND CAST(a.startTime AS date) = CAST(CURRENT_DATE AS date)")
    List<Activity> findTodayActivitiesByUser(@Param("userId") Long userId);
    
    // Alternativa más compatible para semana actual
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.startTime >= :weekStart AND a.startTime < :weekEnd")
    List<Activity> findThisWeekActivitiesByUser(@Param("userId") Long userId, @Param("weekStart") LocalDateTime weekStart, @Param("weekEnd") LocalDateTime weekEnd);
    
    // Alternativa más compatible para mes actual
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.startTime >= :monthStart AND a.startTime < :monthEnd")
    List<Activity> findThisMonthActivitiesByUser(@Param("userId") Long userId, @Param("monthStart") LocalDateTime monthStart, @Param("monthEnd") LocalDateTime monthEnd);
    
    // Métodos adicionales más simples para semana y mes (sin parámetros de fecha)
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.startTime >= :startOfWeek ORDER BY a.startTime DESC")
    List<Activity> findThisWeekActivitiesByUser(@Param("userId") Long userId);
    
    @Query("SELECT a FROM Activity a WHERE a.user.id = :userId AND a.startTime >= :startOfMonth ORDER BY a.startTime DESC")
    List<Activity> findThisMonthActivitiesByUser(@Param("userId") Long userId);
}