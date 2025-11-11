package com.example.cs.oneWeek;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncService {

    @Async
    public void runTask(int taskNumber) {
        try {
            Thread.sleep(100);
            System.out.println("Task " + taskNumber + " 완료 - " +
                    Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
