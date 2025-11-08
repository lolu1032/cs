package com.example.cs.oneWeek;

class Counter {
    public static int count = 0;
}

public class ThreadExample {
    public static void main(String[] args) {
        Runnable r = () -> {
            for (int i = 0; i < 1000; i++) {
                Counter.count++;
            }
        };


        new Thread(r).start();
        new Thread(r).start();
    }
}
