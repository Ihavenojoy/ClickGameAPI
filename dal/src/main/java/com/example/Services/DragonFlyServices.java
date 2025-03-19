package com.example.Services;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DragonFlyServices {
    private RedisTemplate<String, Object> redisTemplate;

    public DragonFlyServices(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ✅ Correcte methode om op te slaan
    @Async
    public CompletableFuture<Void> ClickToMemory(String key, String saveType, String jsonClick) {
        return CompletableFuture.runAsync(() -> {
            String finalKey = saveType + ": " + key;
            System.out.println(finalKey);
            System.out.println("💾 Storing in Redis: " + finalKey + " -> " + jsonClick);
            redisTemplate.opsForValue().set(finalKey, jsonClick);
        });
    }

    // ✅ Correcte methode om op te halen
    @Async
    public CompletableFuture<String> ClickFromMemory(String key, String saveType) {
        return CompletableFuture.supplyAsync(() -> {
            String finalKey = saveType + ": " + key;
            Object json = redisTemplate.opsForValue().get(finalKey);
            System.out.println("✅ Retrieved from Redis: " + json.toString());

            if (json == null) {
                System.out.println("⚠️ No data found for key: " + finalKey);
                return "{}"; // Prevent null pointer errors
            }


            return json.toString();
        });
    }
}

