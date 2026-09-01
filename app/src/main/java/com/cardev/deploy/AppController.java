package com.cardev.deploy;


public class AppController {


    private DnsServer dnsServer;

    private HttpServer httpServer;

    private NetworkManager networkManager;

    private LogManager logManager;



    public AppController(){


        dnsServer = new DnsServer();

        httpServer = new HttpServer();

        networkManager = new NetworkManager();

        logManager = new LogManager();


    }



    public void startService(){


        dnsServer.start();

        httpServer.start();


        logManager.addLog(
                "DNS服务启动"
        );


        logManager.addLog(
                "Web服务启动:"
                + httpServer.getUrl()
        );


    }



    public String getStatus(){


        return logManager.getLogs();


    }



    public NetworkManager getNetworkManager(){

        return networkManager;

    }


}
