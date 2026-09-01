package com.cardev.deploy;


public class AppController {


    private DnsServer dnsServer;

    private UdpDnsServer udpDnsServer;

    private HttpServer httpServer;


    private ApkManager apkManager;

    private ApkDownloadHandler apkDownloadHandler;


    private NetworkManager networkManager;

    private HotspotManager hotspotManager;


    private LogManager logManager;

    private ServiceStatus serviceStatus;


    private PermissionManager permissionManager;


    private ServiceManager serviceManager;



    public AppController(){


        dnsServer = new DnsServer();


        DnsPacketParser parser =
                new DnsPacketParser();


        DnsRuleManager ruleManager =
                new DnsRuleManager();


        udpDnsServer =
                new UdpDnsServer(
                        parser,
                        ruleManager
                );



        httpServer =
                new HttpServer();



        apkManager =
                new ApkManager();



        apkDownloadHandler =
                new ApkDownloadHandler();



        networkManager =
                new NetworkManager();



        hotspotManager =
                new HotspotManager();



        logManager =
                new LogManager();



        serviceStatus =
                new ServiceStatus();



        serviceManager =
                new ServiceManager(
                        udpDnsServer,
                        httpServer,
                        logManager,
                        serviceStatus
                );


    }




    public void start(){


        serviceManager.startAll();


        logManager.addLog(
                "车机部署服务启动"
        );


        logManager.addLog(
                "访问地址:"
                + hotspotManager.getWebAddress()
        );


    }




    public void stop(){


        serviceManager.stopAll();


    }




    public String getStatus(){


        return serviceStatus.getStatus();


    }




    public String getLogs(){


        return logManager.getLogs();


    }




    public ApkManager getApkManager(){


        return apkManager;


    }




    public ApkDownloadHandler getApkDownloadHandler(){


        return apkDownloadHandler;


    }




    public NetworkManager getNetworkManager(){


        return networkManager;


    }




    public HotspotManager getHotspotManager(){


        return hotspotManager;


    }



}
