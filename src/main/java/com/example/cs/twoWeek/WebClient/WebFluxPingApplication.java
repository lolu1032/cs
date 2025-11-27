package com.example.cs.twoWeek.WebClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class WebFluxPingApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebFluxPingApplication.class, args);
    }
}

@RestController
class PingController {
    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("pong");
    }
}
