package com.example.Mapper;

import com.example.Model.Click;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.CompletableFuture;

public class ClickMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // Method to convert Click object to JSON String
    public static CompletableFuture<String> ToJson(Click click) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return objectMapper.writeValueAsString(click);
            } catch (JsonProcessingException e) {
                System.err.println("JSON Mapping Error: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        });
    }

    // Method to convert JSON String back to Click object
    public static CompletableFuture<Click> ToClick(String json) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                if (json == null || json.isEmpty() || json.equals("{}")) {
                    System.out.println("⚠️ Empty JSON detected, returning default Click object.");
                    return new Click(); // Voorkomt een null-object
                }
                return objectMapper.readValue(json, Click.class);
            } catch (Exception e) {
                System.err.println("JSON parsing error: " + e.getMessage());
                e.printStackTrace();
                return new Click(); // Retourneer een default Click object
            }
        });
    }

    // Test the async methods
    public static void main(String[] args) {
        // Create a Click object
        Click click = new Click(100, 10, 2.5, true);

        // Convert Click object to JSON string asynchronously
        ToJson(click).thenAccept(json -> {
            System.out.println("Serialized Click object to JSON: " + json);

            // Convert JSON string back to Click object asynchronously
            ToClick(json).thenAccept(deserializedClick ->
                    System.out.println("Deserialized Click object: " + deserializedClick)
            );
        });

        // Ensure the main thread doesn't exit before async operations complete
        try {
            Thread.sleep(2000);  // Wait for async tasks to finish (for demo purposes)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
