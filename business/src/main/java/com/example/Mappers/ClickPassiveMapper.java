package com.example.Mappers;

import com.example.Models.ClickPassive;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;

public class ClickPassiveMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Convert ClickPassive object to JSON string
    public static CompletableFuture<String> ToJson(ClickPassive clickPassive) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return objectMapper.writeValueAsString(clickPassive);
            } catch (JsonProcessingException e) {
                System.err.println("JSON Mapping Error (ClickPassive): " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        });
    }

    // Convert JSON string back to ClickPassive object
    public static CompletableFuture<ClickPassive> ToClickPassive(String json) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (json == null || json.isEmpty() || json.equals("{}")) {
                    System.out.println("⚠️ Empty JSON detected, returning default ClickPassive object.");
                    return new ClickPassive(); // Prevents null return
                }
                return objectMapper.readValue(json, ClickPassive.class);
            } catch (Exception e) {
                System.err.println("JSON parsing error (ClickPassive): " + e.getMessage());
                e.printStackTrace();
                return new ClickPassive(); // Return a default ClickPassive object on error
            }
        });
    }

    // Optional: Test main method for ClickPassive
    public static void main(String[] args) {
        ClickPassive clickPassive = new ClickPassive();
        clickPassive.setAmmount(42);

        ToJson(clickPassive).thenAccept(json -> {
            System.out.println("Serialized ClickPassive to JSON: " + json);

            ToClickPassive(json).thenAccept(deserialized ->
                    System.out.println("Deserialized ClickPassive object: Ammount = " + deserialized.getAmmount())
            );
        });

        // Prevent exit before async tasks finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
