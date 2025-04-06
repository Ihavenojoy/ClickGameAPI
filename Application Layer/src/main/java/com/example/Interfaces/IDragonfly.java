package com.example.Interfaces;

import java.util.concurrent.CompletableFuture;

public interface IDragonfly {
    public CompletableFuture<Boolean> ClickToMemory(String key, String saveType, String jsonClick);
    public CompletableFuture<String> ClickFromMemory(String key, String saveType);
}
