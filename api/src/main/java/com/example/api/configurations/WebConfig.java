package com.example.api.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String allowedOrigins = System.getProperty("ALLOWED_ORIGINS");
        registry.addMapping("/**")  // This allows CORS for all endpoints
                .allowedOrigins(allowedOrigins)  // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow the necessary HTTP methods
                .allowedHeaders("*");  // Allow all headers
    }
}