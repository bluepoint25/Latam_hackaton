package com.fitneservice.fitneservice.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ResetPasswordRequestDTO {
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
    private String newPassword;
    
    // Constructors
    public ResetPasswordRequestDTO() {}
    
    public ResetPasswordRequestDTO(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }
    
    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}