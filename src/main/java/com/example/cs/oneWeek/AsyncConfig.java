package com.example.cs.oneWeek;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(12); // 기본 스레드 수 (CPU 코어 수에 맞게)
        executor.setMaxPoolSize(12);  // 동시에 처리 가능한 최대 스레드 수
        executor.setQueueCapacity(10000); // 대기 가능한 작업 수
        executor.setThreadNamePrefix("AsyncThread-");
        executor.initialize();
        return executor;
    }
}
