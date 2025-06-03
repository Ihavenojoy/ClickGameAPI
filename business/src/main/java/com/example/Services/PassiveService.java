package com.example.Services;

import com.example.Interfaces.IDragonfly;
import com.example.Mappers.ClickMapper;
import com.example.Mappers.ClickPassiveMapper;
import com.example.Models.Click;
import com.example.Models.ClickPassive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Service
public class PassiveService {
    @Autowired
    private IDragonfly dragonFlyServices;

    private final String ClickKey = "ClickUserID";  // Consistent save type for Click objects
    private final String PassiveKey = "PassiveUserID";  // Consistent save type for Click objects

    @Async
    public CompletableFuture<ClickPassive> handlePassiveAsync(int userid) {
        long startTime = System.currentTimeMillis();
        String userKey = Integer.toString(userid);

        // 🔹 Step 1: Retrieve ClickPassive object from Redis asynchronously
        return dragonFlyServices.FromMemory(userKey, PassiveKey)
                .thenCompose(this::toClickPassive)  // Convert the JSON string to ClickPassive object
                .thenComposeAsync(ClickPassive -> {
                    if (ClickPassive != null) {
                        return CompletableFuture.completedFuture(ClickPassive);
                    }
                    return CompletableFuture.completedFuture(null);  // Skip if null
                })
                .thenComposeAsync(click -> {
                    if (click != null) {
                        return toJson(click)
                                .thenCompose(json -> dragonFlyServices.ToMemory(userKey, PassiveKey, json)
                                        .thenApply(success -> success ? click : new ClickPassive())); // Return default if failed
                    }
                    return CompletableFuture.completedFuture(null);  // Skip if null
                })
                .thenApplyAsync(click -> {
                    System.out.println("ClickPassive operation took: " + (System.currentTimeMillis() - startTime) + " ms");
                    return click;
                });
    }

    private CompletableFuture<String> toJson(ClickPassive click) {
        return ClickPassiveMapper.ToJson(click);  // You must update ClickMapper to handle ClickPassive
    }

    private CompletableFuture<ClickPassive> toClickPassive(String json) {
        return ClickPassiveMapper.ToClickPassive(json);  // You must create this method in ClickMapper
    }

    @Async
    public CompletableFuture<ClickPassive> StartUpRequest(int userid) {
        String userKey = Integer.toString(userid);

        return dragonFlyServices.FromMemory(userKey, PassiveKey)
                .thenCompose(ClickPassiveMapper::ToClickPassive);  // Updated mapper method
    }

    @Async
    public CompletableFuture<Click> SetClick(int userId, int amount) {
        long startTime = System.currentTimeMillis();
        String userKey = Integer.toString(userId);

        return dragonFlyServices.FromMemory(userKey, ClickKey)
                .thenCompose(this::toClick)  // Deserialize JSON -> Click object
                .thenComposeAsync(click -> {
                    if (click != null) {
                        int updatedValue = click.getClickvalue() + amount;
                        click.setClickvalue(updatedValue);
                        return CompletableFuture.completedFuture(click);
                    }
                    // Return a default Click if none found? Or consider Optional<Click>
                    return CompletableFuture.completedFuture(null);
                })
                .thenComposeAsync(click -> {
                    if (click != null) {
                        return toJson(click)
                                .thenCompose(json -> dragonFlyServices.ToMemory(userKey, ClickKey, json))
                                .thenApply(success -> success ? click : new Click());
                    }
                    return CompletableFuture.completedFuture(null);
                })
                .whenComplete((click, ex) -> {
                    if (ex != null) {
                        System.err.println("Error in setClick: " + ex.getMessage());
                    }
                    System.out.println("Click operation took: " + (System.currentTimeMillis() - startTime) + " ms");
                });
    }


    private CompletableFuture<String> toJson(Click click) {
        return ClickMapper.ToJson(click);
    }

    private CompletableFuture<Click> toClick(String json) {
        return ClickMapper.ToClick(json);
    }
}
