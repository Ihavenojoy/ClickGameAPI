package com.example.clickerapi.Controller;
import com.example.clickerapi.DAL.Services.DragonFlyServices;
import com.example.clickerapi.Model.Click;
import com.example.clickerapi.Model.User;
import com.example.clickerapi.Service.ClickService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.example.clickerapi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import Bussines_Layer.*;


@RestController
public class UserController {
    private UserService userService;
    private final Executor asyncExecutor;

    @Autowired
    public UserController(UserService userService, @Qualifier("applicationTaskExecutor") Executor asyncExecutor) {
        this.userService = userService;
        this.asyncExecutor = asyncExecutor;
    }

    @GetMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password)
    {
        Testforme main = new Testforme(5);
        User user = new User(0,"Unkown","Unkown");
        return main;
    }

}
