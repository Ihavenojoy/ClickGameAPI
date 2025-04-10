package com.example.Services;

import com.example.Interfaces.IDragonfly;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class IDragonflyServices implements IDragonfly {
    private final RedisTemplate<String, String> redisTemplate; // Use String type for both key and value

    public IDragonflyServices(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Async
    public CompletableFuture<Boolean> ToMemory(String key, String saveType, String json) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Create the final key for Redis
                String finalKey = saveType + ": " + key; // finalKey like "ClickUserID: 3"
                System.out.println("💾 Storing in Redis: " + finalKey + " -> " + json);

                // Store the value (jsonClick) as a plain string with the finalKey
                redisTemplate.opsForValue().set(finalKey, json);
                System.out.println("✅ Data stored in Redis with key: " + finalKey);

                return true;  // Return true if the data is successfully stored
            } catch (Exception e) {
                System.err.println("⚠️ Error storing data in Redis: " + e.getMessage());
                e.printStackTrace();
                return false;  // Return false if there is an error
            }
        });
    }

    @Async
    public CompletableFuture<String> FromMemory(String key, String saveType) {
        return CompletableFuture.supplyAsync(() -> {
            String finalKey = saveType + ": " + key;
            System.out.println("🔑 Retrieving from Redis with key: " + finalKey);
            String json = redisTemplate.opsForValue().get(finalKey);

            if (json == null) {
                System.out.println("⚠️ No data found for key: " + finalKey);
                return "{}"; // Return default empty JSON
            }

            System.out.println("✅ Retrieved from Redis: " + json);
            return json;
        });
    }
}
