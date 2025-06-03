package com.example.api.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsGlobalConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/ws/**")
                .allowedOriginPatterns("http://localhost:3000")
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowCredentials(true);
    }
}