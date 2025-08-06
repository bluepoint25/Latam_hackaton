package com.fitneservice.fitneservice.controller;

import com.fitneservice.fitneservice.dto.*;
import com.fitneservice.fitneservice.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registrar nuevo usuario
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            authService.registerUser(request);
            return ResponseEntity.ok(new ApiResponse(true, "Usuario registrado exitosamente"));
        } catch (AuthService.UserAlreadyExistsException e) {
            return ResponseEntity.badRequest()
                .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error interno del servidor"));
        }
    }

    /**
     * Iniciar sesión
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        try {
            LoginResponseDTO response = authService.authenticateUser(request);
            return ResponseEntity.ok(response);
        } catch (AuthService.InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cambiar contraseña
     */
    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
        try {
            authService.changePassword(request);
            return ResponseEntity.ok(new ApiResponse(true, "Contraseña cambiada exitosamente"));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(false, e.getMessage()));
        } catch (AuthService.InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error interno del servidor"));
        }
    }

    /**
     * Resetear contraseña
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO request) {
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok(new ApiResponse(true, "Contraseña reseteada exitosamente"));
        } catch (AuthService.UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error interno del servidor"));
        }
    }

    /**
     * Validar token
     */
    @PostMapping("/validate-token")
    public ResponseEntity<ApiResponse> validateToken(@RequestParam String token) {
        try {
            boolean isValid = authService.validateToken(token);
            if (isValid) {
                return ResponseEntity.ok(new ApiResponse(true, "Token válido"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Token inválido"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error interno del servidor"));
        }
    }

    /**
     * Verificar si email existe
     */
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse> checkEmail(@RequestParam String email) {
        try {
            boolean exists = authService.emailExists(email);
            return ResponseEntity.ok(new ApiResponse(exists, 
                exists ? "Email ya registrado" : "Email disponible"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error interno del servidor"));
        }
    }

    // Clase para respuestas genéricas de la API
    public static class ApiResponse {
        private boolean success;
        private String message;

        public ApiResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        // Getters y setters
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}