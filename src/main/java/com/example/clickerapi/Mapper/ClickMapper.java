package com.example.clickerapi.Mapper;

import com.example.clickerapi.Model.Click;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ClickMapper {

    // Method to convert Click object to JSON String
    public static String convertClickToJson(Click click) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Serialize Click object to JSON string
            return objectMapper.writeValueAsString(click);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to convert JSON String back to Click object
    public static Click convertJsonToClick(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize JSON string to Click object
            return objectMapper.readValue(json, Click.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        // Create a Click object
        Click click = new Click(100,10, 2.5, true);

        // Convert Click object to JSON string
        String json = convertClickToJson(click);
        System.out.println("Serialized Click object to JSON: " + json);

        // Convert JSON string back to Click object
        Click deserializedClick = convertJsonToClick(json);
        System.out.println("Deserialized Click object: " + deserializedClick);
    }
}
