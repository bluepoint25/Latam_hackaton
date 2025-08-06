package com.fitneservice.fitneservice.dto;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequestDTO {
    
    @NotNull(message = "El ID del usuario es obligatorio")
    private Long userId;
    
    @NotBlank(message = "La contraseña actual es obligatoria")
    private String currentPassword;
    
    @NotBlank(message = "La nueva contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La nueva contraseña debe tener entre 6 y 100 caracteres")
    private String newPassword;
    
    // Constructors
    public ChangePasswordRequestDTO() {}
    
    public ChangePasswordRequestDTO(Long userId, String currentPassword, String newPassword) {
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
    
    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getCurrentPassword() { return currentPassword; }
    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
    
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}