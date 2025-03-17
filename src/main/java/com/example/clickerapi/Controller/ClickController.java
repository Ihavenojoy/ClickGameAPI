package com.example.clickerapi.Controller;
import com.example.clickerapi.DAL.Services.DragonFlyServices;
import com.example.clickerapi.Model.Click;
import com.example.clickerapi.Service.ClickService;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173") //Premision from port to be allowed in
@RestController
public class ClickController {

    private ClickService clickService;
    private final Executor asyncExecutor;
    //Click click = new Click(8,6,false); // <== Database integration needed

    @Autowired
    public ClickController(ClickService clickservice, @Qualifier("applicationTaskExecutor") Executor asyncExecutor)
    {
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
