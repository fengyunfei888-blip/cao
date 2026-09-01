package com.cardev.deploy;


public class AppController {


    private DnsServer dnsServer;

    private HttpServer httpServer;

    private NetworkManager networkManager;

    private LogManager logManager;


    private HotspotManager hotspotManager;

    private ApkDownloadHandler apkDownloadHandler;

    private InstallPageGenerator pageGenerator;

    private ServiceStatus serviceStatus;



    public AppController(){


        dnsServer = new DnsServer();

        httpServer = new HttpServer();

        networkManager = new NetworkManager();

        logManager = new LogManager();


        hotspotManager = new HotspotManager();

        apkDownloadHandler = new ApkDownloadHandler();

        pageGenerator = new InstallPageGenerator();

        serviceStatus = new ServiceStatus();


    }



    public void startService(){


        dnsServer.start();

        httpServer.start();


        serviceStatus.setDnsRunning(
                dnsServer.isRunning()
        );


        serviceStatus.setWebRunning(
                httpServer.isRunning()
        );


        serviceStatus.setNetworkReady(
                hotspotManager.getLocalIp() != null
        );


        logManager.addLog(
                "DNS服务启动"
        );


        logManager.addLog(
                "Web服务启动:"
                + httpServer.getUrl()
        );


    }



    public String getLogs(){

        return logManager.getLogs();

    }



    public ServiceStatus getServiceStatus(){

        return serviceStatus;

    }



    public HotspotManager getHotspotManager(){

        return hotspotManager;

    }



    public ApkDownloadHandler getApkDownloadHandler(){

        return apkDownloadHandler;

    }



    public InstallPageGenerator getPageGenerator(){

        return pageGenerator;

    }



    public DnsServer getDnsServer(){

        return dnsServer;

    }



    public HttpServer getHttpServer(){

        return httpServer;

    }


}
