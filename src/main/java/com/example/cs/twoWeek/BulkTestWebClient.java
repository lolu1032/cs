package com.example.cs.twoWeek;

public class BulkTestWebClient {
    public static void main(String[] args) {
        WebClientClient client = new WebClientClient();

        long total = 0;
        long min = Long.MAX_VALUE;
        long max = 0;

        for (int i = 0; i < 1000; i++) {
            long t = client.call("http://localhost:8080/ping");
            total += t;
            min = Math.min(min, t);
            max = Math.max(max, t);
        }

        System.out.println("avg = " + total / 1_000_000.0 / 1000 + " ms");
        System.out.println("min = " + min / 1_000_000.0 + " ms");
        System.out.println("max = " + max / 1_000_000.0 + " ms");
    }
}
