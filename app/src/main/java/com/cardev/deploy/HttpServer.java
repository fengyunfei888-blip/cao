package com.cardev.deploy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    public static final int PORT = 8080;

    private volatile boolean running = false;
    private ServerSocket serverSocket;
    private Thread thread;

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                while (running) {
                    Socket client = serverSocket.accept();
                    handle(client);
                }
            } catch (Exception ignored) {
            } finally {
                running = false;
                try {
                    if (serverSocket != null) serverSocket.close();
                } catch (Exception ignored) {
                }
            }
        }, "CarDeploy-HTTP");
        thread.start();
    }

    private void handle(Socket client) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            reader.readLine();
            byte[] body = getHomePage().getBytes(StandardCharsets.UTF_8);
            String header = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html; charset=utf-8\r\n"
                    + "Content-Length: " + body.length + "\r\n"
                    + "Connection: close\r\n\r\n";
            OutputStream out = client.getOutputStream();
            out.write(header.getBytes(StandardCharsets.UTF_8));
            out.write(body);
            out.flush();
        } catch (Exception ignored) {
        } finally {
            try { client.close(); } catch (Exception ignored) {}
        }
    }

    public synchronized void stop() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (Exception ignored) {
        }
    }

    public boolean isRunning() {
        return running;
    }

    public String getHomePage() {
        return "<html><head><meta charset='utf-8'><title>车机应用部署助手</title></head>"
                + "<body style='font-family:sans-serif;padding:24px'>"
                + "<h2>车机应用部署助手</h2>"
                + "<p>本机替换服务已运行。</p>"
                + "</body></html>";
    }
}
