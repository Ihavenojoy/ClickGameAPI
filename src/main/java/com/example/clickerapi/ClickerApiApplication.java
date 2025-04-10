package com.example.clickerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.example.api", "com.example.Services"})
@EnableAsync
@EnableConfigurationProperties(MinIOProperties.class)
public class ClickerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickerApiApplication.class, args);
    }

}
