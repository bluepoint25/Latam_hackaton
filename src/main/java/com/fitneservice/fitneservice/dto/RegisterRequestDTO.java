package com.fitneservice.fitneservice.dto;


import com.fitneservice.fitneservice.entity.User;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class RegisterRequestDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String lastName;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    private String password;
    
    @NotNull(message = "El género es obligatorio")
    private User.Gender gender;
    
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate dateOfBirth;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 100, message = "La ciudad no puede tener más de 100 caracteres")
    private String city;
    
    @DecimalMin(value = "50.0", message = "La altura debe ser al menos 50 cm")
    @DecimalMax(value = "250.0", message = "La altura no puede ser mayor a 250 cm")
    private Double height;
    
    @DecimalMin(value = "20.0", message = "El peso debe ser al menos 20 kg")
    @DecimalMax(value = "300.0", message = "El peso no puede ser mayor a 300 kg")
    private Double weight;
    
    @Size(max = 500, message = "El objetivo personal no puede tener más de 500 caracteres")
    private String personalGoal;
    
    // Constructors
    public RegisterRequestDTO() {}
    
    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public User.Gender getGender() { return gender; }
    public void setGender(User.Gender gender) { this.gender = gender; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    
    public String getPersonalGoal() { return personalGoal; }
    public void setPersonalGoal(String personalGoal) { this.personalGoal = personalGoal; }
}