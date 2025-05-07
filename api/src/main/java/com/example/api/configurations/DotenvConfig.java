package com.example.api.configurations;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    public DotenvConfig() {
        // Load .env file from the root directory (adjust the path accordingly)
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .load();

        // Set system properties using values from .env file
        System.setProperty("MINIO_ACCESS_KEY", dotenv.get("MINIO_ACCESS_KEY"));
        System.setProperty("MINIO_SECRET_KEY", dotenv.get("MINIO_SECRET_KEY"));
        System.setProperty("SSL_KEYSTORE_PASSWORD", dotenv.get("SSL_KEYSTORE_PASSWORD"));
    }
}