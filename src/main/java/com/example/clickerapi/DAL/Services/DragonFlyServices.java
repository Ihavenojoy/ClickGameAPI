package com.example.clickerapi.DAL.Services;

import com.example.clickerapi.Mapper.ClickMapper;
import com.example.clickerapi.Model.Click;
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

    // set
    @Async
    public CompletableFuture<Void> ClickToMemory(String key, Click click) {
        return CompletableFuture.runAsync(() -> {
            String finalKey = "ClickUserID: " + key;  // Use ":" for better readability
            String json = ClickMapper.convertClickToJson(click);
            redisTemplate.opsForValue().set(finalKey, json); // Store JSON as a simple string
        });
    }
    // get
    @Async
    public CompletableFuture<Click> ClickFromMemory(String key) {
        return CompletableFuture.supplyAsync(() -> {
            String finalKey = "ClickUserID: " + key;
            Object json = redisTemplate.opsForValue().get(finalKey);
            return (json == null) ? null : ClickMapper.convertJsonToClick(json.toString());
        });
    }
}
