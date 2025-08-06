package com.fitneservice.fitneservice.config;

import com.fitneservice.fitneservice.entity.*;
import com.fitneservice.fitneservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            ActivityRepository activityRepository,
            TrackPointRepository trackPointRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            // Verificar si ya existen datos para evitar duplicados
            if (userRepository.count() > 0) {
                return; // Ya hay datos, no inicializar
            }

            // Crear usuario de ejemplo
            User user = new User();
            // Usar los nombres correctos de los métodos según tu entidad User
            user.setEmail("usuario1@email.com");
            user.setPassword(passwordEncoder.encode("MiContraseñaSegura123!")); // Contraseña segura
            user.setFirstName("Juan");
            user.setLastName("Pérez");
            // user.setEnabled(true); // Solo si existe este método
            userRepository.save(user);

            // Crear actividad de ejemplo
            Activity activity = new Activity();
            activity.setName("Entrenamiento matutino");
            activity.setDescription("Carrera suave por el parque.");
            activity.setActivityType(ActivityType.RUNNING);
            activity.setStatus(ActivityStatus.COMPLETED);
            activity.setStartTime(LocalDateTime.now().minusHours(2));
            activity.setEndTime(LocalDateTime.now().minusHours(1));
            activity.setDurationMinutes(60);
            activity.setDistanceKm(10.5);
            activity.setCaloriesBurned(650.0);
            activity.setAverageSpeed(10.5);
            activity.setMaxSpeed(14.0);
            activity.setAveragePace(5.7);
            activity.setElevationGain(100.0);
            activity.setPointsEarned(100);
            activity.setNotes("Buen ritmo, sin molestias.");
            activity.setPhotoUrl("https://ejemplo.com/foto.jpg");
            activity.setEmoticon("🏃"); // Emoji simple sin caracteres especiales
            activity.setUser(user);

            activityRepository.save(activity);

            // Crear trackpoints de ejemplo
            TrackPoint tp1 = new TrackPoint();
            tp1.setActivity(activity);
            tp1.setLatitude(-33.4569);
            tp1.setLongitude(-70.6483);
            // Usar el tipo correcto para timestamp según tu entidad TrackPoint
            tp1.setTimestamp(System.currentTimeMillis() - 7200000); // 2 horas atrás en milisegundos

            TrackPoint tp2 = new TrackPoint();
            tp2.setActivity(activity);
            tp2.setLatitude(-33.4570);
            tp2.setLongitude(-70.6485);
            tp2.setTimestamp(System.currentTimeMillis() - 5400000); // 1.5 horas atrás en milisegundos

            trackPointRepository.saveAll(Arrays.asList(tp1, tp2));

            System.out.println("Datos de ejemplo inicializados correctamente.");
        };
    }
}