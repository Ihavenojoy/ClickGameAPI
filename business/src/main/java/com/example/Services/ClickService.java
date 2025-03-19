package com.example.Services;

import com.example.Mapper.ClickMapper;
import com.example.Model.Click;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ClickService {
    @Autowired
    private DragonFlyServices dragonFlyServices;
    private final String ClickKey = "ClickUserID";


    @Async
    public CompletableFuture<Click> handleClickedAsync(int userid) {
        long startTime = System.currentTimeMillis();
        String userKey = Integer.toString(userid);

        // 🔹 Step 1: Retrieve Click object from memory asynchronously
        return dragonFlyServices.ClickFromMemory(userKey, ClickKey)
                .thenCompose(ClickMapper::ToClick) // Ensures async JSON conversion
                .thenApplyAsync(click -> {
                    if (click != null) {
                        click.Clicked(); // Mutates the click object
                    }
                    return click;
                })
                .thenComposeAsync(click -> {
                    if (click != null) {
                        return ClickMapper.ToJson(click)
                                .thenCompose(json -> dragonFlyServices.ClickToMemory(userKey, ClickKey, json));
                    }
                    return null;
                })
                .thenApplyAsync(v -> {
                    System.out.println("Click operation took: " + (System.currentTimeMillis() - startTime) + " ms");
                    return null;
                });
    }

    @Async
    public CompletableFuture<Click> StartUpRequest(int userid) {
        String userKey = Integer.toString(userid);

        // 🔹 Step 1: Retrieve Click object from memory asynchronously
        return dragonFlyServices.ClickFromMemory(userKey, ClickKey)
                .thenCompose(ClickMapper::ToClick); // Ensures async JSON conversion
    }
}
