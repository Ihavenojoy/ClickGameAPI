package com.example.api.controller;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.example.Models.Click;
import com.example.Services.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ClickController {

    private ClickService clickService;
    private final Executor asyncExecutor;

    @Autowired
    public ClickController(ClickService clickservice, @Qualifier("applicationTaskExecutor") Executor asyncExecutor) {
        clickService = clickservice;
        this.asyncExecutor = asyncExecutor;
    }

    @GetMapping("/click/{userid}")
    public CompletableFuture<Click> Clicked(@PathVariable  int userid) {
        return clickService.handleClickedAsync(userid);
    }

    @GetMapping("/start")
    public CompletableFuture<Click> Start(@RequestParam int userid) {
        return clickService.StartUpRequest(userid);
    }

    @GetMapping("/test")
    public String test() {
        return "Controller is working!";
    }
}