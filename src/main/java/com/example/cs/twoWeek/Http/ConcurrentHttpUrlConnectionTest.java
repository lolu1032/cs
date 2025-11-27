package com.example.cs.twoWeek.Http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ConcurrentHttpUrlConnectionTest {

    public static void main(String[] args) throws Exception {

        int CONCURRENCY = 100; // 동시에 요청할 개수
        String URL = "http://localhost:8080/ping";

        HttpUrlConnClient client = new HttpUrlConnClient();

        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY);

        List<Future<Long>> futures = new ArrayList<>();

        long startAll = System.nanoTime();

        // 100개 요청 동시에 보냄
        for (int i = 0; i < CONCURRENCY; i++) {
            futures.add(executor.submit(() -> client.call(URL)));
        }

        long total = 0;
        long min = Long.MAX_VALUE;
        long max = 0;

        // 결과 수집
        for (Future<Long> f : futures) {
            long t = f.get();
            total += t;
            min = Math.min(min, t);
            max = Math.max(max, t);
        }

        long endAll = System.nanoTime();

        System.out.println("=== HttpUrlConnection 동시 요청 테스트 ===");
        System.out.println("동시 요청 개수: " + CONCURRENCY);
        System.out.println("개별 평균 응답: " + (total / CONCURRENCY) / 1_000_000.0 + " ms");
        System.out.println("개별 최소: " + min / 1_000_000.0 + " ms");
        System.out.println("개별 최대: " + max / 1_000_000.0 + " ms");

        System.out.println("전체 처리 시간: " + (endAll - startAll) / 1_000_000.0 + " ms");

        executor.shutdown();
    }
}
