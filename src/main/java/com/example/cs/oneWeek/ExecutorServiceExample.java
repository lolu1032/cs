package com.example.cs.oneWeek;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        // 고정 스레드풀 생성 (CPU 코어 수 기준) 12 스레드를 생성하고 사용함
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        // 12스레드가 만들어짐 각 스레드는 0.1초동안 실행
        // 0.1초동안 작업 후 수행이 끝나면 큐에서 다음 작업을 가져옴
        // 1만 작업을 만듬 -> 스레드가 12개니까 일이 끝날대마다 다음 작업을 꺼냄
        for (int i = 0; i < 10000; i++) {
            executor.submit(() -> {
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            });
        }
        // 새작업은 안받고 지금있는것만 하고 끝냄
        executor.shutdown();
        // 1분동안 기달림
        executor.awaitTermination(1, TimeUnit.MINUTES);

        long end = System.currentTimeMillis();
        System.out.println("ExecutorService time: " + (end - start) + "ms");
        System.out.println("Terminated: " + executor.isTerminated());
        // ExecutorService time: 60021ms
        // Terminated: false
        // 작업 다못끝냄
    }
}