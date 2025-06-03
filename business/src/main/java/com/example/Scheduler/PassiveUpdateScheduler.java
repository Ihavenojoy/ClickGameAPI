package com.example.Scheduler;

import com.example.Models.Click;
import com.example.Models.ClickPassive;
import com.example.Services.PassiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PassiveUpdateScheduler {

    @Autowired
    private PassiveService passiveService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void performPassiveUpdate(int userId) {
        try {
            CompletableFuture<ClickPassive> future = passiveService.handlePassiveAsync(userId);
            ClickPassive result = future.get(); // Wait for passive value
            int ammount = result.getAmmount();

            Click click = passiveService.SetClick(userId, ammount).get(); // Update click with passive
            int clickValue = click.getClickvalue();

            // 🔴 Push to WebSocket
            messagingTemplate.convertAndSend("/topic/updates", clickValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}