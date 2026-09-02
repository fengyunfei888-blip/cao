package com.cardev.deploy;

public class AppController {

    private final UdpDnsServer udpDnsServer;
    private final HttpServer httpServer;
    private final ApkManager apkManager;
    private final ApkDownloadHandler apkDownloadHandler;
    private final NetworkManager networkManager;
    private final HotspotManager hotspotManager;
    private final AddressDetector addressDetector;
    private final DomainDetector domainDetector;
    private final LogManager logManager;
    private final ServiceStatus serviceStatus;
    private final ServiceManager serviceManager;

    public AppController() {
        DnsPacketParser parser = new DnsPacketParser();
        DnsRuleManager ruleManager = new DnsRuleManager();

        logManager = new LogManager();
        domainDetector = new DomainDetector();
        addressDetector = new AddressDetector();

        udpDnsServer = new UdpDnsServer(parser, ruleManager, domainDetector, logManager);
        httpServer = new HttpServer();
        apkManager = new ApkManager();
        apkDownloadHandler = new ApkDownloadHandler();
        networkManager = new NetworkManager();
        hotspotManager = new HotspotManager();
        serviceStatus = new ServiceStatus();

        serviceManager = new ServiceManager(
                udpDnsServer,
                httpServer,
                logManager,
                serviceStatus
        );
    }

    public void start() {
        String ip = getLocalIp();
        udpDnsServer.setAutomaticTargetIp(ip);
        serviceStatus.setNetworkReady(!"未知".equals(ip));

        logManager.addLog("手机地址：" + ip);
        logManager.addLog("等待车机DNS请求...");
        serviceManager.startAll();
    }

    public void stop() {
        serviceManager.stopAll();
    }

    public String getStatus() {
        return serviceStatus.getStatus();
    }

    public String getLogs() {
        return logManager.getLogs();
    }

    public String getLocalIp() {
        return addressDetector.detectLocalIpv4();
    }

    public String getWebAddress() {
        return "http://" + getLocalIp() + ":" + HttpServer.PORT;
    }

    public String getLatestDomain() {
        String target = domainDetector.getLatestTargetDomain();
        if (target != null && !target.isEmpty()) return target;
        return domainDetector.getLatestDomain();
    }

    public int getDnsPort() {
        return UdpDnsServer.LISTEN_PORT;
    }

    public ApkManager getApkManager() {
        return apkManager;
    }

    public ApkDownloadHandler getApkDownloadHandler() {
        return apkDownloadHandler;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public HotspotManager getHotspotManager() {
        return hotspotManager;
    }
}
