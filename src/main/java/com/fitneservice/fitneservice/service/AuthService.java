package com.fitneservice.fitneservice.service;

import com.fitneservice.fitneservice.dto.*;
import com.fitneservice.fitneservice.entity.User;
import com.fitneservice.fitneservice.repository.UserRepository;
import com.fitneservice.fitneservice.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private static final String USER_NOT_FOUND = "Usuario no encontrado";
    private static final String INVALID_CREDENTIALS = "Credenciales inválidas";
    private static final String EMAIL_ALREADY_EXISTS = "El email ya está registrado";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                      PasswordEncoder passwordEncoder,
                      AuthenticationManager authenticationManager,
                      JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Registrar un nuevo usuario
     */
    public void registerUser(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setCity(request.getCity());
        user.setHeight(request.getHeight());
        user.setWeight(request.getWeight());
        // Conversión segura de String a Enum
        if (request.getPersonalGoal() != null && !request.getPersonalGoal().isEmpty()) {
            try {
                user.setPersonalGoal(User.PersonalGoal.valueOf(request.getPersonalGoal().toUpperCase()));
            } catch (IllegalArgumentException e) {
                user.setPersonalGoal(User.PersonalGoal.LOSE_WEIGHT);
            }
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }

    /**
     * Autenticar usuario
     */
    public LoginResponseDTO authenticateUser(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            // Generar token usando email
            String token = jwtTokenProvider.generateToken(user.getEmail());

            // Crear respuesta
            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);
            response.setTokenType("Bearer");
            response.setExpiresIn(jwtTokenProvider.getJwtExpirationInMs());
            response.setUserId(user.getId());
            response.setEmail(user.getEmail());
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());

            return response;

        } catch (AuthenticationException e) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS);
        }
    }

    public void changePassword(ChangePasswordRequestDTO request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Contraseña actual incorrecta");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void resetPassword(ResetPasswordRequestDTO request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Excepciones personalizadas
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) { super(message); }
    }
    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) { super(message); }
    }
    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) { super(message); }
    }
}