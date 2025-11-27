package com.example.cs.twoWeek;

import org.springframework.web.reactive.function.client.WebClient;

public class WebClientClient {

    private final WebClient client = WebClient.builder().build();

    public long call(String url) {
        long start = System.nanoTime();

        String body = client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // <<< 동기 호출

        return System.nanoTime() - start;
    }
}
