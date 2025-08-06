package com.fitneservice.fitneservice.dto;



import com.fitneservice.fitneservice.entity.User;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    
    @Email(message = "Email debe ser válido")
    @NotBlank(message = "Email es obligatorio")
    private String email;
    
    private User.Gender gender;
    private String profilePhotoUrl;
    private LocalDate dateOfBirth;
    private Double height;
    private Double weight;
    private User.PersonalGoal personalGoal;
    private String city;
    private Integer totalPoints;
    private Integer level;
    private Integer currentStreak;
    private Integer maxStreak;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public UserDTO() {}
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.gender = user.getGender();
        this.profilePhotoUrl = user.getProfilePhotoUrl();
        this.dateOfBirth = user.getDateOfBirth();
        this.height = user.getHeight();
        this.weight = user.getWeight();
        this.personalGoal = user.getPersonalGoal();
        this.city = user.getCity();
        this.totalPoints = user.getTotalPoints();
        this.level = user.getLevel();
        this.currentStreak = user.getCurrentStreak();
        this.maxStreak = user.getMaxStreak();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
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
    
    public User.Gender getGender() { return gender; }
    public void setGender(User.Gender gender) { this.gender = gender; }
    
    public String getProfilePhotoUrl() { return profilePhotoUrl; }
    public void setProfilePhotoUrl(String profilePhotoUrl) { this.profilePhotoUrl = profilePhotoUrl; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
    public User.PersonalGoal getPersonalGoal() { return personalGoal; }
    public void setPersonalGoal(User.PersonalGoal personalGoal) { this.personalGoal = personalGoal; }
    
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
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
