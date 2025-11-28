package com.example.cs.twoWeek.WebClient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

public class ConcurrentWebClientTest {

    public static void main(String[] args) {
        int CONCURRENCY = 100;
        String URL = "http://localhost:8080/ping";

        WebClient client = WebClient.builder().build();

        long startAll = System.nanoTime();

        // 비동기 요청 100개 생성 + 동시성 100 보장
        var results = Flux.range(0, CONCURRENCY)
                .flatMap(i -> {
                    long start = System.nanoTime();
                    return client.get()
                            .uri(URL)
                            .retrieve()
                            .bodyToMono(String.class)
                            .map(x -> System.nanoTime() - start);
                }, CONCURRENCY)
                .collectList()   // List<Long>
                .block();        // 동기 수집

        long endAll = System.nanoTime();

        long total = 0;
        long min = Long.MAX_VALUE;
        long max = 0;

        for (long t : results) {
            total += t;
            min = Math.min(min, t);
            max = Math.max(max, t);
        }

        System.out.println("=== WebClient 동시 요청 테스트 ===");
        System.out.println("동시 요청 개수: " + CONCURRENCY);
        System.out.println("개별 평균 응답: " + (total / CONCURRENCY) / 1_000_000.0 + " ms");
        System.out.println("개별 최소: " + min / 1_000_000.0 + " ms");
        System.out.println("개별 최대: " + max / 1_000_000.0 + " ms");
        System.out.println("전체 처리 시간: " + (endAll - startAll) / 1_000_000.0 + " ms");
    }
}
