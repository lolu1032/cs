package com.example.cs.oneWeek;

import java.util.ArrayList;
import java.util.List;

public class ThreadStateCheck {
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(() -> {
                long sum = 0;
                for (long j = 0; j < 100_000_000L; j++) {
                    sum += j;
                }
            });
            threads.add(t);
            t.start();
        }

        // 0.1초 후 상태 확인
        Thread.sleep(100);

        long runnable = threads.stream().filter(t -> t.getState() == Thread.State.RUNNABLE).count();
        long waiting = threads.stream().filter(t -> t.getState() == Thread.State.WAITING).count();
        long timedWaiting = threads.stream().filter(t -> t.getState() == Thread.State.TIMED_WAITING).count();
        long terminated = threads.stream().filter(t -> t.getState() == Thread.State.TERMINATED).count();

        System.out.printf("RUNNABLE: %d, WAITING: %d, TIMED_WAITING: %d, TERMINATED: %d%n",
                runnable, waiting, timedWaiting, terminated);
        // 결과 RUNNABLE: 78, WAITING: 0, TIMED_WAITING: 0, TERMINATED: 25
        // 25개 스레드는 이미 작업을 완료 (TERMINATED)
        // 78개 스레드는 CPU에서 실행 중이거나, 실행을 기다리는 상태 (RUNNABLE)
        // 즉, OS 스케줄러가 CPU 코어 수(12개)에 맞춰 번갈아가며 실행 중
    }
}
