package com.example.api.controller;

import com.example.Models.Click;
import com.example.Models.ClickPassive;
import com.example.Services.PassiveService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/passive")
@CrossOrigin(origins = "http://localhost:3000")
@EnableScheduling
public class AutomationController {

    private final PassiveService passiveService;
    private final Executor asyncExecutor;
    private final SimpMessagingTemplate messagingTemplate;

    private final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private ScheduledFuture<?> scheduledTask;

    @Autowired
    public AutomationController(PassiveService passiveService,
                                @Qualifier("applicationTaskExecutor") Executor asyncExecutor,
                                SimpMessagingTemplate messagingTemplate) {
        this.passiveService = passiveService;
        this.asyncExecutor = asyncExecutor;
        this.messagingTemplate = messagingTemplate;
        scheduler.initialize();
    }

    @PostConstruct
    public void startPassiveIncomeTask() {
        scheduledTask = scheduler.scheduleAtFixedRate(this::sendPassiveIncome, 1000);
    }

    @PreDestroy
    public void stopPassiveIncomeTask() {
        if (scheduledTask != null) {
            scheduledTask.cancel(true);
        }
    }

    private void sendPassiveIncome() {
        try {
            int userId = 3; // hardcoded for now
            CompletableFuture<ClickPassive> future = passiveService.handlePassiveAsync(userId);
            int amount = 2;
            Click updatedClick = passiveService.SetClick(userId, amount).get();
            int updatedValue = updatedClick.getClickvalue();

            // Send via WebSocket
            messagingTemplate.convertAndSend("/topic/passiveincome", updatedValue);
        } catch (Exception e) {
            System.err.println("Failed to send passive income: " + e.getMessage());
        }
    }

    @PostMapping("/passiveincome")
    public int passiveIncome(@RequestParam int userId) throws ExecutionException, InterruptedException {
        CompletableFuture<ClickPassive> future = passiveService.handlePassiveAsync(userId);
        ClickPassive result = future.get();
        int amount = 2;
        Click click = passiveService.SetClick(userId, amount).get();
        return click.getClickvalue();
    }
}
