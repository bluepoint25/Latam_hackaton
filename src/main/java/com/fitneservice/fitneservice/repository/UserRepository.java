package com.fitneservice.fitneservice.repository;



import com.fitneservice.fitneservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<User> findByCity(String city);
    
    @Query("SELECT u FROM User u WHERE u.totalPoints >= :minPoints ORDER BY u.totalPoints DESC")
    List<User> findTopUsersByPoints(@Param("minPoints") Integer minPoints);
    
    @Query("SELECT u FROM User u ORDER BY u.totalPoints DESC")
    List<User> findAllOrderByPointsDesc();
    
    @Query("SELECT u FROM User u WHERE u.level >= :level")
    List<User> findUsersByMinLevel(@Param("level") Integer level);
    
    @Query("SELECT u FROM User u WHERE u.currentStreak >= :streak ORDER BY u.currentStreak DESC")
    List<User> findUsersByStreak(@Param("streak") Integer streak);
    
    @Query("SELECT u FROM User u WHERE u.city = :city ORDER BY u.totalPoints DESC LIMIT :limit")
    List<User> findTopUsersByCity(@Param("city") String city, @Param("limit") Integer limit);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.createdAt >= :startDate")
    Long countNewUsersFromDate(@Param("startDate") LocalDateTime startDate);
    
    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:name% OR u.lastName LIKE %:name%")
    List<User> findByNameContaining(@Param("name") String name);
}