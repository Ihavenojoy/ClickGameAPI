package com.example.api.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // This must match your SockJS client URL:
        registry.addEndpoint("/ws-endpoint")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS(); // Enables SockJS fallback options
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory broker for "/topic" destinations
        config.enableSimpleBroker("/topic");
        // Prefix for messages bound for @MessageMapping methods (if any)
        config.setApplicationDestinationPrefixes("/app");
    }
}
