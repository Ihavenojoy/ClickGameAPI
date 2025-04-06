package com.example.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
@Entity
public class UserRegistration {

    @JsonProperty("ID")
    @GeneratedValue
    @Id
    private int id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("PassWord")
    private String password;
    public UserRegistration(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public UserRegistration()
    {    }

}
