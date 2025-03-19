package com.example.Model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public class User {
    @JsonProperty("ID")
    public int id;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Email")
    public String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public User()
    {    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}