package com.example.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PassiveScheduledTask {

    @Autowired
    private PassiveUpdateScheduler updateScheduler;

    @Scheduled(fixedRate = 1000) // Run every 60,000 ms = 1 minute
    public void runPassiveIncomeUpdate() {
        int userId = 3; // Replace with dynamic logic if needed
        updateScheduler.performPassiveUpdate(userId);
    }
}