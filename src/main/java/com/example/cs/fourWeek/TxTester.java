package com.example.cs.fourWeek;

import org.springframework.stereotype.Component;

@Component
public class TxTester {
    private final String url = "jdbc:h2:mem:testdb";
    private final String user = "sa";
    private final String pass = "";

    public void testTxH2(boolean forceError) {
        // 네가 작성한 코드 그대로 넣어주면 됨
    }
}
