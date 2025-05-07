package com.example.api.configurations;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Objects;

@Configuration
@Profile("!docker")
public class DotenvConfig {


    public DotenvConfig() {
        // Load .env file from the root directory (adjust the path accordingly)
        Dotenv dotenv = Dotenv.configure()
                .directory(System.getProperty("user.dir"))
                .load();

        // Set system properties using values from .env file
        System.setProperty("MINIO_ACCESS_KEY", Objects.requireNonNull(dotenv.get("MINIO_ACCESS_KEY")));
        System.setProperty("MINIO_SECRET_KEY", Objects.requireNonNull(dotenv.get("MINIO_SECRET_KEY")));
        System.setProperty("SSL_KEYSTORE_PASSWORD", Objects.requireNonNull(dotenv.get("SSL_KEYSTORE_PASSWORD")));

        // Set Dragonfly environment variables
        System.setProperty("DRAGONFLY_PORT", Objects.requireNonNull(dotenv.get("DRAGONFLY_PORT")));

        // Set MySQL environment variables
        System.setProperty("MYSQL_ROOT_PASSWORD", Objects.requireNonNull(dotenv.get("MYSQL_ROOT_PASSWORD")));
        System.setProperty("MYSQL_DATABASE", Objects.requireNonNull(dotenv.get("MYSQL_DATABASE")));
        System.setProperty("MYSQL_USER", Objects.requireNonNull(dotenv.get("MYSQL_USER")));
        System.setProperty("MYSQL_PASSWORD", Objects.requireNonNull(dotenv.get("MYSQL_PASSWORD")));
        System.setProperty("MYSQL_PORT", Objects.requireNonNull(dotenv.get("MYSQL_PORT")));


        // Set allowed origins for CORS
        System.setProperty("ALLOWED_ORIGINS", Objects.requireNonNull(dotenv.get("ALLOWED_ORIGINS")));
    }
}