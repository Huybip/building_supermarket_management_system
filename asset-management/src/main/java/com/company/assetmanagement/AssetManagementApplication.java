package com.company.assetmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * AssetManagementApplication - Main Application Entry Point
 * Nguyên tắc OOP: Application Bootstrap
 */
@SpringBootApplication
@EnableTransactionManagement
public class AssetManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetManagementApplication.class, args);
    }

}
