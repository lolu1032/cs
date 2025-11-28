package com.example.cs.twoWeek.WebClient;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpPerformanceTest {

    private static final String TEST_URL = "https://jsonplaceholder.typicode.com/posts/1";
    private static final int PARALLEL_REQUESTS = 50;
    private static final WebClient webClient = WebClient.create();

    public static void main(String[] args) {
        System.out.println("=== HTTP 성능 비교 실험 ===\n");

        // 1. 단일 요청 테스트
        System.out.println("1. 단일 요청 테스트");
        testSingleRequest();

        // 2. 병렬 요청 테스트
        System.out.println("\n2. 병렬 요청 테스트 (" + PARALLEL_REQUESTS + "개)");
        testParallelRequests();

        // 3. 순차 요청 테스트
        System.out.println("\n3. 순차 요청 테스트 (10개)");
        testSequentialRequests();
    }

    // ===== HttpURLConnection 방식 =====

    private static String httpUrlConnectionGet(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            conn.disconnect();
            return response.toString();
        }
        conn.disconnect();
        throw new Exception("Failed: HTTP " + responseCode);
    }

    // ===== WebClient 방식 =====

    private static Mono<String> webClientGet(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(5));
    }

    // ===== 테스트 메서드 =====

    private static void testSingleRequest() {
        // HttpURLConnection
        long start = System.currentTimeMillis();
        try {
            String result = httpUrlConnectionGet(TEST_URL);
            long duration = System.currentTimeMillis() - start;
            System.out.println("HttpURLConnection: " + duration + "ms (응답 길이: " + result.length() + ")");
        } catch (Exception e) {
            System.out.println("HttpURLConnection 실패: " + e.getMessage());
        }

        // WebClient
        start = System.currentTimeMillis();
        try {
            String result = webClientGet(TEST_URL).block();
            long duration = System.currentTimeMillis() - start;
            System.out.println("WebClient: " + duration + "ms (응답 길이: " + result.length() + ")");
        } catch (Exception e) {
            System.out.println("WebClient 실패: " + e.getMessage());
        }
    }

    private static void testParallelRequests() {
        // HttpURLConnection (쓰레드 풀 사용)
        ExecutorService executor = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();

        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (int i = 0; i < PARALLEL_REQUESTS; i++) {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                try {
                    return httpUrlConnectionGet(TEST_URL);
                } catch (Exception e) {
                    return "ERROR";
                }
            }, executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        long duration1 = System.currentTimeMillis() - start;
        executor.shutdown();

        long successCount1 = futures.stream()
                .filter(f -> !f.join().equals("ERROR"))
                .count();

        System.out.println("HttpURLConnection: " + duration1 + "ms (성공: " +
                successCount1 + "/" + PARALLEL_REQUESTS + ")");

        // WebClient (Reactive 방식)
        start = System.currentTimeMillis();

        List<String> results = Flux.range(0, PARALLEL_REQUESTS)
                .flatMap(i -> webClientGet(TEST_URL)
                        .onErrorReturn("ERROR"))
                .collectList()
                .block();

        long duration2 = System.currentTimeMillis() - start;
        long successCount2 = results.stream()
                .filter(r -> !r.equals("ERROR"))
                .count();

        System.out.println("WebClient: " + duration2 + "ms (성공: " +
                successCount2 + "/" + PARALLEL_REQUESTS + ")");

        // 성능 차이 계산
        double improvement = ((double)(duration1 - duration2) / duration1) * 100;
        System.out.println("\n성능 차이: " + String.format("%.1f", improvement) + "% " +
                (improvement > 0 ? "(WebClient가 더 빠름)" : "(HttpURLConnection이 더 빠름)"));
    }

    private static void testSequentialRequests() {
        int count = 10;

        // HttpURLConnection
        long start = System.currentTimeMillis();
        int success1 = 0;
        for (int i = 0; i < count; i++) {
            try {
                httpUrlConnectionGet(TEST_URL);
                success1++;
            } catch (Exception e) {
                // 실패
            }
        }
        long duration1 = System.currentTimeMillis() - start;
        System.out.println("HttpURLConnection: " + duration1 + "ms (성공: " +
                success1 + "/" + count + ")");

        // WebClient
        start = System.currentTimeMillis();
        List<String> results = Flux.range(0, count)
                .concatMap(i -> webClientGet(TEST_URL)
                        .onErrorReturn("ERROR"))
                .collectList()
                .block();

        long duration2 = System.currentTimeMillis() - start;
        long success2 = results.stream()
                .filter(r -> !r.equals("ERROR"))
                .count();

        System.out.println("WebClient: " + duration2 + "ms (성공: " +
                success2 + "/" + count + ")");
    }
    private static void testSingleRequestNonBlocking() {
        System.out.println("\n[Non-Blocking] 1. 단일 요청 테스트");

        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);

        webClientGet(TEST_URL)
                .doOnNext(result -> {
                    long duration = System.currentTimeMillis() - start;
                    System.out.println("WebClient (non-blocking): " + duration +
                            "ms (응답 길이: " + result.length() + ")");
                })
                .doOnError(e -> System.out.println("WebClient non-blocking 실패: " + e.getMessage()))
                .doFinally(sig -> latch.countDown())
                .subscribe();

        try { latch.await(); } catch (InterruptedException ignored) {}
    }

    private static void testParallelRequestsNonBlocking() {
        System.out.println("\n[Non-Blocking] 2. 병렬 요청 테스트 (" + PARALLEL_REQUESTS + "개)");

        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, PARALLEL_REQUESTS)
                .flatMap(i -> webClientGet(TEST_URL).onErrorReturn("ERROR"))
                .collectList()
                .doOnNext(list -> {
                    long duration = System.currentTimeMillis() - start;
                    long success = list.stream().filter(v -> !"ERROR".equals(v)).count();

                    System.out.println("WebClient (non-blocking): " + duration +
                            "ms (성공: " + success + "/" + PARALLEL_REQUESTS + ")");
                })
                .doFinally(sig -> latch.countDown())
                .subscribe();

        try { latch.await(); } catch (InterruptedException ignored) {}
    }

    private static void testSequentialRequestsNonBlocking() {
        System.out.println("\n[Non-Blocking] 3. 순차 요청 테스트 (10개)");

        int count = 10;
        long start = System.currentTimeMillis();
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(0, count)
                .concatMap(i -> webClientGet(TEST_URL).onErrorReturn("ERROR"))
                .collectList()
                .doOnNext(list -> {
                    long duration = System.currentTimeMillis() - start;
                    long success = list.stream().filter(v -> !"ERROR".equals(v)).count();

                    System.out.println("WebClient (non-blocking): " + duration +
                            "ms (성공: " + success + "/" + count + ")");
                })
                .doFinally(sig -> latch.countDown())
                .subscribe();

        try { latch.await(); } catch (InterruptedException ignored) {}
    }
}