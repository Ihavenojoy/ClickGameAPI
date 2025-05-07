package com.example.api;

import com.example.Configuration.MinIOProperties;
import com.example.api.configurations.DotenvConfig;
import com.example.api.configurations.PropertyVerifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication(scanBasePackages = {"com.example.api", "com.example.Services"})
@EnableAsync
@PropertySource("classpath:application.properties")
@EnableConfigurationProperties(MinIOProperties.class)
public class ApiApplication extends SpringBootServletInitializer {

    DotenvConfig dotenvConfig = new DotenvConfig();
    PropertyVerifier PropertyVerifier = new PropertyVerifier();

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}