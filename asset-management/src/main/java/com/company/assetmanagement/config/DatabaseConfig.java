package com.company.assetmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * DatabaseConfig - Cấu hình kết nối Database
 * Nguyên tắc OOP: Configuration Pattern, Dependency Injection
 */
@Configuration
@EntityScan("com.company.assetmanagement.model")
@EnableJpaRepositories("com.company.assetmanagement.repository")
public class DatabaseConfig {
    
    // Configuration tự động được Spring Boot xử lý qua application.properties
    // File này có thể mở rộng với custom Bean definitions nếu cần
    
}
