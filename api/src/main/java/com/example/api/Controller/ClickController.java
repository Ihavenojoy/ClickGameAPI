package com.example.api.Controller;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.example.Model.Click;
import com.example.Services.ClickService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000") //Premision from port to be allowed in
@RestController
@RequestMapping("/api")
public class ClickController {

    private ClickService clickService;
    private final Executor asyncExecutor;

    @Autowired
    public ClickController(ClickService clickservice, @Qualifier("applicationTaskExecutor") Executor asyncExecutor) {
        clickService = clickservice;
        this.asyncExecutor = asyncExecutor;
    }

    @GetMapping("/click")
    public CompletableFuture<Click> Clicked(@RequestParam int userid) {
        return clickService.handleClickedAsync(userid);
    }

    @GetMapping("/start")
    public CompletableFuture<Click> Start(@RequestParam int userid) {
        return clickService.StartUpRequest(userid);
    }
}