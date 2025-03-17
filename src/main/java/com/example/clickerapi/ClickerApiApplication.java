package com.example.clickerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClickerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClickerApiApplication.class, args);
    }

}
