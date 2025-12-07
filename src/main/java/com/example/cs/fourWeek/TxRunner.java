package com.example.cs.fourWeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TxRunner implements CommandLineRunner {

    @Autowired
    private TxTester txTester;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== rollback 테스트 ===");
        txTester.testTxH2(true);

        System.out.println("=== commit 테스트 ===");
        txTester.testTxH2(false);
    }
}
