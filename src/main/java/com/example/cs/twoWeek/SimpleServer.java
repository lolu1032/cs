package com.example.cs.twoWeek;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class SimpleServer {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new java.net.InetSocketAddress(8080), 0);

        server.createContext("/ping", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws java.io.IOException {
                byte[] response = "pong".getBytes();
                exchange.sendResponseHeaders(200, response.length);
                exchange.getResponseBody().write(response);
                exchange.close();
            }
        });

        server.start();
        System.out.println("Server running on port 8080");
    }
}
