package com.example.cs.twoWeek.Http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUrlConnClient {

    public long call(String urlString) {
        long start = System.nanoTime();

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            while (br.readLine() != null) {
                // 응답 읽기
            }

            br.close();
            conn.disconnect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return System.nanoTime() - start;
    }
}
