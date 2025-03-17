package com.example.clickerapi.Service;

import com.example.clickerapi.DAL.Services.DragonFlyServices;
import com.example.clickerapi.Model.Click;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ClickService {
    @Autowired
    private DragonFlyServices dragonFlyServices;

    @Async
    public CompletableFuture<Click> handleClickedAsync(int userid) {
        long startTime = System.currentTimeMillis();
        String userKey = Integer.toString(userid);

        // 🔹 Step 1: Retrieve click object from memory asynchronously
        CompletableFuture<Click> clickFuture = dragonFlyServices.ClickFromMemory(userKey);

        // 🔹 Step 2: Process click value
        CompletableFuture<Click> updatedClickFuture = clickFuture.thenApplyAsync(click -> {
            if (click != null) {
                click.Clicked(); // Mutates the click object
            }
            return click;
        });

        // 🔹 Step 3: Store updated click object back to memory (Runs in parallel with processing)
        CompletableFuture<Void> storeClickFuture = updatedClickFuture.thenComposeAsync(click -> {
            if (click != null) {
                return dragonFlyServices.ClickToMemory(userKey, click);
            }
            return CompletableFuture.completedFuture(null);
        });

        // 🔹 Step 4: Return final Click object after storing
        return storeClickFuture.thenCombineAsync(updatedClickFuture, (v, click) -> {
            System.out.println("Click operation took: " + (System.currentTimeMillis() - startTime) + " ms");
            return click;
        });
    }
    @Async
    public CompletableFuture<Click> StartUpRequest(int userid) {
        long startTime = System.currentTimeMillis();
        String userKey = Integer.toString(userid);

        // 🔹 Step 1: Retrieve Click object from memory asynchronously
        return dragonFlyServices.ClickFromMemory(userKey)
                .thenApply(click -> {
                    System.out.println("Click operation took: " + (System.currentTimeMillis() - startTime) + " ms");
                    return click;
                });
    }


}
