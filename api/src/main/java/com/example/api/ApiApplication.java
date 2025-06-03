package com.example.api;

import com.example.Configuration.MinIOProperties;
import com.example.api.configurations.DotenvConfig;
import com.example.api.configurations.PropertyVerifier;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.awt.*;
import java.net.URI;


@SpringBootApplication(scanBasePackages = {"com.example.api", "com.example.Services"})
@EnableAsync
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(MinIOProperties.class)
@EnableScheduling
@EnableWebMvc
@OpenAPIDefinition(
        info = @Info(
                title = "ClickGame API",
                version = "v1",
                description = "API for the Click Game"
        )
)
public class ApiApplication extends SpringBootServletInitializer {

    DotenvConfig dotenvConfig = new DotenvConfig();
    PropertyVerifier PropertyVerifier = new PropertyVerifier();

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);

        try {
            Desktop.getDesktop().browse(new URI("http://localhost:8080/swagger-ui.html"));
        } catch (Exception e) {
            System.err.println("Failed to open : " + e.getMessage());
        }
    }

}