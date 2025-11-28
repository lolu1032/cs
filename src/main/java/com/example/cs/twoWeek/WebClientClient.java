package com.example.cs.twoWeek;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class WebClientClient {

    private final WebClient client;

    public WebClientClient() {
        HttpClient httpClient = HttpClient.create()
                .keepAlive(true)          // ★ TCP 연결 재사용
                .compress(true)           // 압축 지원
                .wiretap(false);          // 디버깅 로그(필요 없으면 false)

        this.client = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    public Mono<Long> callNonBlocking(String url) {
        long start = System.nanoTime();

        return client.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .map(body -> System.nanoTime() - start);
    }

}
