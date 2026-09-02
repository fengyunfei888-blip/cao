package com.cardev.deploy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Arrays;

public class UdpDnsServer {

    public static final int LISTEN_PORT = 5353;

    private volatile boolean running = false;
    private Thread thread;
    private DatagramSocket socket;

    private final DnsPacketParser parser;
    private final DnsRuleManager ruleManager;
    private final DomainDetector domainDetector;
    private final LogManager logManager;
    private final DnsResponseBuilder responseBuilder = new DnsResponseBuilder();

    private volatile String automaticTargetIp = "";

    public UdpDnsServer(
            DnsPacketParser parser,
            DnsRuleManager ruleManager,
            DomainDetector domainDetector,
            LogManager logManager
    ) {
        this.parser = parser;
        this.ruleManager = ruleManager;
        this.domainDetector = domainDetector;
        this.logManager = logManager;
    }

    public void setAutomaticTargetIp(String ip) {
        automaticTargetIp = ip == null ? "" : ip.trim();
    }

    public synchronized void start() {
        if (running) return;
        running = true;

        thread = new Thread(() -> {
            try {
                socket = new DatagramSocket(LISTEN_PORT);
                socket.setSoTimeout(1000);
                logManager.addLog("DNS监听已启动：UDP " + LISTEN_PORT);

                while (running) {
                    byte[] buffer = new byte[1500];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    try {
                        socket.receive(packet);
                    } catch (SocketTimeoutException timeout) {
                        continue;
                    }

                    byte[] query = Arrays.copyOf(packet.getData(), packet.getLength());
                    String domain = parser.parseDomain(query);
                    boolean target = parser.isTargetDomain(domain);

                    if (domain != null && !domain.isEmpty()) {
                        domainDetector.record(domain, target);
                        logManager.addLog("检测到域名：" + domain);
                    }

                    String overrideIp = domain == null ? null : ruleManager.getIp(domain);
                    if (overrideIp == null && target && isIpv4(automaticTargetIp)) {
                        overrideIp = automaticTargetIp;
                        ruleManager.addRule(domain, overrideIp);
                        logManager.addLog("自动替换：" + domain + " -> " + overrideIp);
                    }

                    byte[] response;
                    if (overrideIp != null) {
                        response = responseBuilder.buildAResponse(query, query.length, overrideIp);
                    } else {
                        response = forward(query);
                    }

                    if (response != null) {
                        DatagramPacket reply = new DatagramPacket(
                                response,
                                response.length,
                                packet.getAddress(),
                                packet.getPort()
                        );
                        socket.send(reply);
                    }
                }
            } catch (Exception e) {
                logManager.addLog("DNS服务错误：" + safeMessage(e));
            } finally {
                running = false;
                if (socket != null) socket.close();
                socket = null;
            }
        }, "CarDeploy-DNS");

        thread.start();
    }

    private byte[] forward(byte[] query) {
        DatagramSocket upstream = null;
        try {
            upstream = new DatagramSocket();
            upstream.setSoTimeout(1800);
            InetAddress resolver = InetAddress.getByName("1.1.1.1");
            upstream.send(new DatagramPacket(query, query.length, resolver, 53));
            byte[] response = new byte[1500];
            DatagramPacket packet = new DatagramPacket(response, response.length);
            upstream.receive(packet);
            return Arrays.copyOf(packet.getData(), packet.getLength());
        } catch (Exception e) {
            return null;
        } finally {
            if (upstream != null) upstream.close();
        }
    }

    private boolean isIpv4(String value) {
        if (value == null) return false;
        String[] parts = value.split("\\.");
        if (parts.length != 4) return false;
        try {
            for (String part : parts) {
                int n = Integer.parseInt(part);
                if (n < 0 || n > 255) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String safeMessage(Exception e) {
        String m = e.getMessage();
        return m == null ? e.getClass().getSimpleName() : m;
    }

    public synchronized void stop() {
        running = false;
        if (socket != null) socket.close();
    }

    public boolean isRunning() {
        return running;
    }
}
