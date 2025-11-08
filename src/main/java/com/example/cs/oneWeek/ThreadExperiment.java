package com.example.cs.oneWeek;
import java.util.ArrayList;
import java.util.List;

public class ThreadExperiment {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();

        // 1만 개 스레드 생성
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(() -> {
                try { Thread.sleep(100); } catch (InterruptedException ignored) {}
            });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long end = System.currentTimeMillis();
        System.out.println("time: " + (end - start) + "ms");
    }
}
