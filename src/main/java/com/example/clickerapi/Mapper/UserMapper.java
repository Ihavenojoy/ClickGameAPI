package com.example.clickerapi.Mapper;

import com.example.clickerapi.Model.Click;
import com.example.clickerapi.Model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserMapper {

    // Method to convert User object to JSON String
    public static String convertUserToJson(User user) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Serialize Click object to JSON string
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert JSON String back to User object
    public static User convertJsonToUser(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize JSON string to Click object
            return objectMapper.readValue(json, User.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Create a User object
        User user = new User(0,"Unkown","Unkown");

        // Convert Click object to JSON string
        String json = convertUserToJson(user);
        System.out.println("Serialized Click object to JSON: " + json);

        // Convert JSON string back to Click object
        User deserializedUser = convertJsonToUser(json);
        System.out.println("Deserialized Click object: " + deserializedUser);
    }
}
