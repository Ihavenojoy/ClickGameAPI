package com.example.Mappers;

import com.example.Models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.CompletableFuture;

public class UserMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Convert User object to JSON String asynchronously
    public static CompletableFuture<String> ToJson(User user) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    // Convert JSON String back to User object asynchronously
    public static CompletableFuture<User> ToUser(String json) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return objectMapper.readValue(json, User.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    // Test the async methods
    public static void main(String[] args) {
        // Create a User object
        User user = new User(0, "Unknown", "Unknown");

        // Convert User object to JSON string asynchronously
        ToJson(user).thenAccept(json -> {
            System.out.println("Serialized User object to JSON: " + json);

            // Convert JSON string back to User object asynchronously
            ToUser(json).thenAccept(deserializedUser ->
                    System.out.println("Deserialized User object: " + deserializedUser)
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
