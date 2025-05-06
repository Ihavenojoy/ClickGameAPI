package com.example.Services;

import com.example.DTO.UserRegistrationDTO;
import com.example.Interfaces.IUser;
import com.example.Models.Click;
import com.example.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    private final IUser Userservice;

    @Autowired
    public UserService(IUser Userservice) {
        this.Userservice = Userservice;
    }

    @Async
    public CompletableFuture<User> Login(String email, String password) {
        return null;
    }

    @Async
    public CompletableFuture<User> Register(String name, String email, String password, String passwordcheck) {
        User user = new User(6, "Test", "Test@email.com");
        UserRegistrationDTO UserRegistrationDTO = new UserRegistrationDTO("Test", "Test2@email.com", "Test@email.com");
        if (Userservice.RegisterUser(UserRegistrationDTO)) {
            return CompletableFuture.supplyAsync(() -> {
                return user;
            });
        }
        else {
            return null;
        }
    }
}