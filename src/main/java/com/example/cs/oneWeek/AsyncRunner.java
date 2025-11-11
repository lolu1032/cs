package com.example.cs.oneWeek;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AsyncRunner implements CommandLineRunner {
    private final AsyncService asyncService;

    public AsyncRunner(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    @Override
    public void run(String... args) throws Exception {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            asyncService.runTask(i);
        }

        long end = System.currentTimeMillis();
        System.out.println("모든 작업 등록 완료: " + (end - start) + "ms");
    }
}
