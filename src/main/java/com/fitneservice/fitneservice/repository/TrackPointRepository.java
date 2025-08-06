package com.fitneservice.fitneservice.repository;



import com.fitneservice.fitneservice.entity.TrackPoint;
import com.fitneservice.fitneservice.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrackPointRepository extends JpaRepository<TrackPoint, Long> {
    
    List<TrackPoint> findByActivityOrderByTimestampAsc(Activity activity);
    
    List<TrackPoint> findByActivityIdOrderByTimestampAsc(Long activityId);
    
    @Query("SELECT tp FROM TrackPoint tp WHERE tp.activity.id = :activityId AND tp.timestamp >= :startTime AND tp.timestamp <= :endTime ORDER BY tp.timestamp ASC")
    List<TrackPoint> findByActivityAndTimeRange(@Param("activityId") Long activityId, 
                                               @Param("startTime") LocalDateTime startTime, 
                                               @Param("endTime") LocalDateTime endTime);
    
    @Query("SELECT COUNT(tp) FROM TrackPoint tp WHERE tp.activity.id = :activityId")
    Long countByActivityId(@Param("activityId") Long activityId);
    
    @Query("SELECT tp FROM TrackPoint tp WHERE tp.activity.id = :activityId ORDER BY tp.timestamp ASC LIMIT 1")
    TrackPoint findFirstPointByActivity(@Param("activityId") Long activityId);
    
    @Query("SELECT tp FROM TrackPoint tp WHERE tp.activity.id = :activityId ORDER BY tp.timestamp DESC LIMIT 1")
    TrackPoint findLastPointByActivity(@Param("activityId") Long activityId);
    
    @Query("SELECT MAX(tp.speed) FROM TrackPoint tp WHERE tp.activity.id = :activityId")
    Double getMaxSpeedByActivity(@Param("activityId") Long activityId);
    
    @Query("SELECT AVG(tp.speed) FROM TrackPoint tp WHERE tp.activity.id = :activityId AND tp.speed > 0")
    Double getAverageSpeedByActivity(@Param("activityId") Long activityId);
    
    @Query("SELECT MAX(tp.altitude) FROM TrackPoint tp WHERE tp.activity.id = :activityId")
    Double getMaxAltitudeByActivity(@Param("activityId") Long activityId);
    
    @Query("SELECT MIN(tp.altitude) FROM TrackPoint tp WHERE tp.activity.id = :activityId")
    Double getMinAltitudeByActivity(@Param("activityId") Long activityId);
    
    void deleteByActivityId(Long activityId);
}