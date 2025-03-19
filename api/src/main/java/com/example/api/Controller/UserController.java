package com.example.api.Controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.example.Model.User;
import com.example.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private final Executor asyncExecutor;

    @Autowired
    public UserController(UserService userService, @Qualifier("applicationTaskExecutor") Executor asyncExecutor) {
        this.userService = userService;
        this.asyncExecutor = asyncExecutor;
    }

    @GetMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        User user = new User(0, "Unkown", "Unkown");
        return user;
    }
}

