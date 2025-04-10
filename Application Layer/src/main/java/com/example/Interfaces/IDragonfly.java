package com.example.Interfaces;

import java.util.concurrent.CompletableFuture;

public interface IDragonfly {
    public CompletableFuture<Boolean> ToMemory(String key, String saveType, String jsonClick);
    public CompletableFuture<String> FromMemory(String key, String saveType);
}
