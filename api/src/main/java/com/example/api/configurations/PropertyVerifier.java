package com.example.api.configurations;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class PropertyVerifier {

    @PostConstruct
    public void verifyProperties() {
        System.out.println("MINIO_ACCESS_KEY: " + System.getProperty("MINIO_ACCESS_KEY"));
        System.out.println("MINIO_SECRET_KEY: " + System.getProperty("MINIO_SECRET_KEY"));
        System.out.println("SSL_KEYSTORE_PASSWORD: " + System.getProperty("SSL_KEYSTORE_PASSWORD"));
    }
}