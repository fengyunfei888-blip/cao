package com.cardev.deploy;


public class ServiceManager {


    private UdpDnsServer dnsServer;

    private HttpServer httpServer;

    private LogManager logManager;

    private ServiceStatus serviceStatus;



    public ServiceManager(
            UdpDnsServer dnsServer,
            HttpServer httpServer,
            LogManager logManager,
            ServiceStatus serviceStatus
    ){

        this.dnsServer = dnsServer;

        this.httpServer = httpServer;

        this.logManager = logManager;

        this.serviceStatus = serviceStatus;

    }



    public void startAll(){


        dnsServer.start();

        httpServer.start();



        serviceStatus.setDnsRunning(
                dnsServer.isRunning()
        );


        serviceStatus.setWebRunning(
                httpServer.isRunning()
        );


        logManager.addLog(
                "所有服务启动"
        );


    }



    public void stopAll(){


        dnsServer.stop();

        httpServer.stop();



        serviceStatus.setDnsRunning(false);

        serviceStatus.setWebRunning(false);



        logManager.addLog(
                "所有服务停止"
        );


    }



    public boolean isRunning(){


        return dnsServer.isRunning()
                && httpServer.isRunning();


    }


}
