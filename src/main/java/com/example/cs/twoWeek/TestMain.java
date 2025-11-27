package com.example.cs.twoWeek;

import com.example.cs.twoWeek.Http.HttpUrlConnClient;

public class TestMain {
    public static void main(String[] args) {

        HttpUrlConnClient client = new HttpUrlConnClient();

        long result = client.call("http://localhost:8080/ping");

        System.out.println("HTTP 단일 요청 시간(ns) = " + result);
        System.out.println("HTTP 단일 요청 시간(ms) = " + result / 1_000_000.0);
    }
}