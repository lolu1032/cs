package com.example.cs.twoWeek;

import java.util.ArrayList;
import java.util.List;

public class GCTest {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<>();

        for (int i = 0; i < 10_000_000; i++) {
            list.add(new Object());
        }

        System.out.println("Done");
    }
}