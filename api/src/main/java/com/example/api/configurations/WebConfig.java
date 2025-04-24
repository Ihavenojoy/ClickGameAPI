package com.example.api.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // This allows CORS for all endpoints
                .allowedOrigins("https://localhost:3000","http://localhost:3000")  // Frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow the necessary HTTP methods
                .allowedHeaders("*");  // Allow all headers
    }
}