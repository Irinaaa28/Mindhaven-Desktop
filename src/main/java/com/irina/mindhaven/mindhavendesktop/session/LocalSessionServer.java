package com.irina.mindhaven.mindhavendesktop.session;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class LocalSessionServer {

    private HttpServer server;

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8765), 0);
        server.createContext("/session", this::handleSession);
        server.start();
        System.out.println("Local session server started successfully");
    }

    private void handleSession(HttpExchange exchange) throws IOException {
        String uuid = SessionContext.getUserUuid();
        if (uuid == null)
            uuid = "";
        String response = "{\"userUuid\":\"" + uuid + "\"}";
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, response.length());
        try(OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Local session server stopped successfully");
        }
    }
}
