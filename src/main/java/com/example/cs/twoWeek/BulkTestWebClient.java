package com.example.cs.twoWeek;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BulkTestWebClient {
    public static void main(String[] args) {
        WebClientClient client = new WebClientClient();

        long startTotal = System.nanoTime();

        Flux<Long> results = Flux.range(1, 1000)
                .flatMap(i -> Mono.from(client.callNonBlocking("http://localhost:8080/ping")), 1000);

        results.collectList()
                .doOnNext(list -> {
                    long total = list.stream().mapToLong(v -> v).sum();
                    long min = list.stream().mapToLong(v -> v).min().orElse(0);
                    long max = list.stream().mapToLong(v -> v).max().orElse(0);

                    System.out.println("동시 요청 개수: 1000");
                    System.out.println("평균 응답: " + (total / 1_000_000.0 / list.size()) + " ms");
                    System.out.println("최소: " + (min / 1_000_000.0) + " ms");
                    System.out.println("최대: " + (max / 1_000_000.0) + " ms");
                    System.out.println("전체 처리 시간: " + ((System.nanoTime() - startTotal) / 1_000_000.0) + " ms");
                })
                .block();
    }
}
