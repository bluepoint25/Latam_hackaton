package com.fitneservice.fitneservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.fitneservice.repository")
@EnableTransactionManagement
public class DatabaseConfig {
    
    // Configuraciones adicionales de base de datos si son necesarias
}