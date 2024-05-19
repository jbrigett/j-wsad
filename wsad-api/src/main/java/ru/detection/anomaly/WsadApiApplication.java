package ru.detection.anomaly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WsadApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WsadApiApplication.class, args);
    }

}
