package com.example.cs.twoWeek;

import com.example.cs.twoWeek.Http.HttpUrlConnClient;

public class HttpTestMain {
    public static void main(String[] args) {
        HttpUrlConnClient client = new HttpUrlConnClient();

        long total = 0;
        long min = Long.MAX_VALUE;
        long max = 0;

        for (int i = 0; i < 100; i++) {
            long t = client.call("http://localhost:8080/ping");
            total += t;
            min = Math.min(min, t);
            max = Math.max(max, t);
        }

        System.out.println("avg = " + total / 100_000_000.0 + " ms");
        System.out.println("min = " + min / 1_000_000.0 + " ms");
        System.out.println("max = " + max / 1_000_000.0 + " ms");
    }
}