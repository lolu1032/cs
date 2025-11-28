package com.example.cs.twoWeek.WebClient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class ConcurrentWebClientNonBlockingTest {

    public static void main(String[] args) {
        int CONCURRENCY = 100;
        String URL = "http://localhost:8080/ping";

        WebClient client = WebClient.builder().build();

        long startAll = System.nanoTime();

        // 100개의 비동기 요청 생성, 병렬 스케줄러 사용
        Flux<Long> flux = Flux.range(0, CONCURRENCY)
                .flatMap(i ->
                        client.get()
                                .uri(URL)
                                .retrieve()
                                .bodyToMono(String.class)
                                .map(body -> System.nanoTime()) // 응답 도착 시 시간 기록
                                .subscribeOn(Schedulers.boundedElastic())
                );

        // Flux를 한 번만 block()하여 최종 결과 수집
        Long[] results = flux.collectList()
                .map(list -> {
                    long startTime = startAll;
                    Long[] arr = new Long[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        arr[i] = list.get(i) - startTime;
                    }
                    return arr;
                })
                .block(); // 최종 Mono<Long[]>를 block()으로 기다림

        // 통계 계산
        long total = 0;
        long min = Long.MAX_VALUE;
        long max = 0;
        for (long t : results) {
            total += t;
            min = Math.min(min, t);
            max = Math.max(max, t);
        }

        System.out.println("=== WebClient 완전 논블로킹 테스트 ===");
        System.out.println("동시 요청 개수: " + CONCURRENCY);
        System.out.println("개별 평균 응답: " + (total / CONCURRENCY) / 1_000_000.0 + " ms");
        System.out.println("개별 최소: " + min / 1_000_000.0 + " ms");
        System.out.println("개별 최대: " + max / 1_000_000.0 + " ms");
        System.out.println("전체 처리 시간: " + (System.nanoTime() - startAll) / 1_000_000.0 + " ms");
    }

}
