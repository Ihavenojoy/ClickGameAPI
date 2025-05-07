package com.example.api.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PropertyVerifier {

    @PostConstruct
    public void verifyProperties() {
        // Print MinIO environment variables
        System.out.println("MINIO_ACCESS_KEY: " + System.getProperty("MINIO_ACCESS_KEY"));
        System.out.println("MINIO_SECRET_KEY: " + System.getProperty("MINIO_SECRET_KEY"));
        System.out.println("SSL_KEYSTORE_PASSWORD: " + System.getProperty("SSL_KEYSTORE_PASSWORD"));

        // Print Dragonfly environment variable
        System.out.println("DRAGONFLY_PORT: " + System.getProperty("DRAGONFLY_PORT"));

        // Print MySQL environment variables
        System.out.println("MYSQL_ROOT_PASSWORD: " + System.getProperty("MYSQL_ROOT_PASSWORD"));
        System.out.println("MYSQL_DATABASE: " + System.getProperty("MYSQL_DATABASE"));
        System.out.println("MYSQL_USER: " + System.getProperty("MYSQL_USER"));
        System.out.println("MYSQL_PASSWORD: " + System.getProperty("MYSQL_PASSWORD"));
        System.out.println("MYSQL_PORT: " + System.getProperty("MYSQL_PORT"));

        // Print allowed origins for CORS
        System.out.println("ALLOWED_ORIGINS: " + System.getProperty("ALLOWED_ORIGINS"));
    }
}