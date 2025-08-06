package com.fitneservice.fitneservice.service;

import com.fitneservice.fitneservice.dto.UserDTO;
import com.fitneservice.fitneservice.entity.User;
import com.fitneservice.fitneservice.repository.ActivityRepository;
import com.fitneservice.fitneservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    private static final String USER_NOT_FOUND = "Usuario no encontrado";
    
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, 
                      ActivityRepository activityRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Crear un nuevo usuario
     */
    public UserDTO createUser(UserDTO userDTO, String rawPassword) {
        // Verificar si el email ya existe
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }
        
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setCity(userDTO.getCity());
        user.setProfilePhotoUrl(userDTO.getProfilePhotoUrl());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setPersonalGoal(userDTO.getPersonalGoal());
        
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }
    
    /**
     * Obtener usuario por ID
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(UserDTO::new);
    }
    
    /**
     * Obtener usuario por email
     */
    @Transactional(readOnly = true)
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::new);
    }
    
    /**
     * Actualizar perfil de usuario
     */
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setGender(userDTO.getGender());
        user.setProfilePhotoUrl(userDTO.getProfilePhotoUrl());
        user.setHeight(userDTO.getHeight());
        user.setWeight(userDTO.getWeight());
        user.setPersonalGoal(userDTO.getPersonalGoal());
        user.setCity(userDTO.getCity());
        
        User updatedUser = userRepository.save(user);
        return new UserDTO(updatedUser);
    }
    
    /**
     * Actualizar puntos del usuario
     */
    public void addPointsToUser(Long userId, Integer points) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        
        user.addPoints(points);
        userRepository.save(user);
    }
    
    /**
     * Actualizar racha del usuario
     */
    public void updateUserStreak(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        
        // Verificar si el usuario hizo ejercicio ayer
        LocalDateTime yesterday = LocalDateTime.now().minus(1, ChronoUnit.DAYS);
        boolean exercisedYesterday = user.getActivities().stream()
            .anyMatch(activity -> activity.getStartTime().toLocalDate().equals(yesterday.toLocalDate()));
        
        if (exercisedYesterday) {
            user.setCurrentStreak(user.getCurrentStreak() + 1);
            if (user.getCurrentStreak() > user.getMaxStreak()) {
                user.setMaxStreak(user.getCurrentStreak());
            }
        } else {
            user.setCurrentStreak(1); // Reiniciar racha
        }
        
        userRepository.save(user);
    }
    
    /**
     * Obtener ranking de usuarios por puntos
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUserRanking(int limit) {
        return userRepository.findAllOrderByPointsDesc()
            .stream()
            .limit(limit)
            .map(UserDTO::new)
            .toList();
    }
    
    /**
     * Obtener ranking de usuarios por ciudad
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUserRankingByCity(String city, int limit) {
        return userRepository.findTopUsersByCity(city, limit)
            .stream()
            .map(UserDTO::new)
            .toList();
    }
    
    /**
     * Buscar usuarios por nombre
     */
    @Transactional(readOnly = true)
    public List<UserDTO> searchUsersByName(String name) {
        return userRepository.findByNameContaining(name)
            .stream()
            .map(UserDTO::new)
            .toList();
    }
    
    /**
     * Obtener usuarios por ciudad
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByCity(String city) {
        return userRepository.findByCity(city)
            .stream()
            .map(UserDTO::new)
            .toList();
    }
    
    /**
     * Obtener estadísticas del usuario
     */
    @Transactional(readOnly = true)
    public UserStats getUserStats(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        
        UserStats stats = new UserStats();
        stats.setUserId(userId);
        stats.setTotalPoints(user.getTotalPoints());
        stats.setLevel(user.getLevel());
        stats.setCurrentStreak(user.getCurrentStreak());
        stats.setMaxStreak(user.getMaxStreak());
        stats.setTotalActivities(user.getActivities().size());
        
        // Calcular estadísticas adicionales
        double totalDistance = user.getActivities().stream()
            .mapToDouble(activity -> activity.getDistanceKm() != null ? activity.getDistanceKm() : 0.0)
            .sum();
        stats.setTotalDistanceKm(totalDistance);
        
        int totalDuration = user.getActivities().stream()
            .mapToInt(activity -> activity.getDurationMinutes() != null ? activity.getDurationMinutes() : 0)
            .sum();
        stats.setTotalDurationMinutes(totalDuration);

        // ACTUALIZADO PARA DOUBLE
        double totalCalories = user.getActivities().stream()
            .mapToDouble(activity -> activity.getCaloriesBurned() != null ? activity.getCaloriesBurned() : 0.0)
            .sum();
        stats.setTotalCaloriesBurned(totalCalories);
        
        return stats;
    }
    
    /**
     * Eliminar usuario
     */
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        userRepository.deleteById(userId);
    }
    
    /**
     * Verificar si el email existe
     */
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    
    // Excepciones personalizadas
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
    
    // Clase interna para estadísticas del usuario - ACTUALIZADA
    public static class UserStats {
        private Long userId;
        private Integer totalPoints;
        private Integer level;
        private Integer currentStreak;
        private Integer maxStreak;
        private Integer totalActivities;
        private Double totalDistanceKm;
        private Integer totalDurationMinutes;
        private Double totalCaloriesBurned; // CAMBIADO A DOUBLE
        
        // Getters and Setters
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        
        public Integer getTotalPoints() { return totalPoints; }
        public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
        
        public Integer getLevel() { return level; }
        public void setLevel(Integer level) { this.level = level; }
        
        public Integer getCurrentStreak() { return currentStreak; }
        public void setCurrentStreak(Integer currentStreak) { this.currentStreak = currentStreak; }
        
        public Integer getMaxStreak() { return maxStreak; }
        public void setMaxStreak(Integer maxStreak) { this.maxStreak = maxStreak; }
        
        public Integer getTotalActivities() { return totalActivities; }
        public void setTotalActivities(Integer totalActivities) { this.totalActivities = totalActivities; }
        
        public Double getTotalDistanceKm() { return totalDistanceKm; }
        public void setTotalDistanceKm(Double totalDistanceKm) { this.totalDistanceKm = totalDistanceKm; }
        
        public Integer getTotalDurationMinutes() { return totalDurationMinutes; }
        public void setTotalDurationMinutes(Integer totalDurationMinutes) { this.totalDurationMinutes = totalDurationMinutes; }
        
        // MÉTODO ACTUALIZADO A DOUBLE
        public Double getTotalCaloriesBurned() { return totalCaloriesBurned; }
        public void setTotalCaloriesBurned(Double totalCaloriesBurned) { this.totalCaloriesBurned = totalCaloriesBurned; }
    }
}