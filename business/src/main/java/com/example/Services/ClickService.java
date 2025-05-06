package com.example.Services;

import com.example.Interfaces.IDragonfly;
import com.example.Mappers.ClickMapper;
import com.example.Models.Click;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ClickService {

    @Autowired
    private IDragonfly dragonFlyServices;

    private final String ClickKey = "ClickUserID";  // Consistent save type for Click objects

    // Handle click operation asynchronously for a given user
    @Async
    public CompletableFuture<Click> handleClickedAsync(int userid) {
        long startTime = System.currentTimeMillis();
        String userKey = Integer.toString(userid);

        // 🔹 Step 1: Retrieve Click object from Redis asynchronously
        return dragonFlyServices.FromMemory(userKey, ClickKey)
                .thenCompose(this::toClick)  // Convert the JSON string to Click object
                .thenComposeAsync(click -> {
                    if (click != null) {
                        return click.Clicked()  // Perform the async click operation
                                .thenApplyAsync(aVoid -> click);  // Return the click after the Clicked() method
                    }
                    return CompletableFuture.completedFuture(click);  // Skip if click is null
                })
                .thenComposeAsync(click -> {
                    if (click != null) {
                        // Convert the Click object to JSON and save it back to Redis
                        return toJson(click)
                                .thenCompose(json -> {
                                    // Save updated Click object to Redis, returning a CompletableFuture<Boolean>
                                    return dragonFlyServices.ToMemory(userKey, ClickKey, json)
                                            .thenApply(success -> {
                                                if (success) {
                                                    return click;  // Return the updated Click object if saving was successful
                                                } else {
                                                    // Handle failure: return default click or log error as needed
                                                    return new Click();  // Return a default Click object if saving failed
                                                }
                                            });
                                });
                    }
                    return CompletableFuture.completedFuture(click);  // If click is null, skip saving
                })
                .thenApplyAsync(click -> {
                    System.out.println("Click operation took: " + (System.currentTimeMillis() - startTime) + " ms");
                    return click;  // Return the updated Click object
                });
    }

    private CompletableFuture<String> toJson(Click click) {
        return ClickMapper.ToJson(click);
    }

    private CompletableFuture<Click> toClick(String json) {
        return ClickMapper.ToClick(json);
    }

    // Handle the startup request to get Click object from Redis asynchronously
    @Async
    public CompletableFuture<Click> StartUpRequest(int userid) {
        String userKey = Integer.toString(userid);

        // Retrieve Click object from Redis and convert it asynchronously
        return dragonFlyServices.FromMemory(userKey, ClickKey)
                .thenCompose(ClickMapper::ToClick); // Convert JSON to Click asynchronously
    }
}
