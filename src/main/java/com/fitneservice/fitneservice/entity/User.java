package com.fitneservice.fitneservice.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;
    
    @Column(nullable = false)
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    
    @Column(unique = true, nullable = false)
    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;
    
    @Column(nullable = false)
    @NotBlank(message = "Password es obligatorio")
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private String profilePhotoUrl;
    
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    private Double height; // en cm
    private Double weight; // en kg
    
    @Enumerated(EnumType.STRING)
    private PersonalGoal personalGoal;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private Integer totalPoints = 0;
    
    @Column(nullable = false)
    private Integer level = 1;
    
    @Column(nullable = false)
    private Integer currentStreak = 0;
    
    @Column(nullable = false)
    private Integer maxStreak = 0;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> activities;
    
    // Enums
    public enum Gender {
        MALE, FEMALE, PREFER_NOT_TO_SAY
    }
    
    public enum PersonalGoal {
        LOSE_WEIGHT, GAIN_MUSCLE, IMPROVE_HEALTH, MAINTAIN_FITNESS, INCREASE_ENDURANCE
    }
    
    // Constructors
    public User() {}
    
    public User(String firstName, String lastName, String email, String password, 
                Gender gender, LocalDate dateOfBirth, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    
    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public void setProfilePhotoUrl(String profilePhotoUrl) { this.profilePhotoUrl = profilePhotoUrl; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
    public PersonalGoal getPersonalGoal() { return personalGoal; }
    public void setPersonalGoal(PersonalGoal personalGoal) { this.personalGoal = personalGoal; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public Integer getTotalPoints() { return totalPoints; }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }
    
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    
    public Integer getCurrentStreak() { return currentStreak; }
    public void setCurrentStreak(Integer currentStreak) { this.currentStreak = currentStreak; }
    
    public Integer getMaxStreak() { return maxStreak; }
    public void setMaxStreak(Integer maxStreak) { this.maxStreak = maxStreak; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<Activity> getActivities() { return activities; }
    public void setActivities(List<Activity> activities) { this.activities = activities; }
    
    // Helper methods
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    public void addPoints(Integer points) {
        this.totalPoints += points;
        updateLevel();
    }
    
    private void updateLevel() {
        // Cada 1000 puntos = 1 nivel
        this.level = (this.totalPoints / 1000) + 1;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}