package com.fitneservice.fitneservice.controller;



import com.fitneservice.fitneservice.dto.ApiResponseDTO;
import com.fitneservice.fitneservice.dto.UserDTO;
import com.fitneservice.fitneservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    
    private  UserService userService;

    /**
     * Obtener perfil de usuario
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar perfil de usuario
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener estadísticas del usuario
     */
    @GetMapping("/{id}/stats")
    public ResponseEntity<UserService.UserStats> getUserStats(@PathVariable Long id) {
        try {
            UserService.UserStats stats = userService.getUserStats(id);
            return ResponseEntity.ok(stats);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener ranking global de usuarios
     */
    @GetMapping("/ranking")
    public ResponseEntity<List<UserDTO>> getUserRanking(@RequestParam(defaultValue = "10") int limit) {
        List<UserDTO> ranking = userService.getUserRanking(limit);
        return ResponseEntity.ok(ranking);
    }

    /**
     * Obtener ranking de usuarios por ciudad
     */
    @GetMapping("/ranking/city/{city}")
    public ResponseEntity<List<UserDTO>> getUserRankingByCity(@PathVariable String city, 
                                                            @RequestParam(defaultValue = "10") int limit) {
        List<UserDTO> ranking = userService.getUserRankingByCity(city, limit);
        return ResponseEntity.ok(ranking);
    }

    /**
     * Buscar usuarios por nombre
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String name) {
        List<UserDTO> users = userService.searchUsersByName(name);
        return ResponseEntity.ok(users);
    }

    /**
     * Obtener usuarios por ciudad
     */
    @GetMapping("/city/{city}")
    public ResponseEntity<List<UserDTO>> getUsersByCity(@PathVariable String city) {
        List<UserDTO> users = userService.getUsersByCity(city);
        return ResponseEntity.ok(users);
    }

    /**
     * Eliminar usuario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponseDTO(true, "Usuario eliminado exitosamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}