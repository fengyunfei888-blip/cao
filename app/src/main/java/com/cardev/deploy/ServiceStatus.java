package com.cardev.deploy;


public class ServiceStatus {


    private boolean dnsRunning;

    private boolean webRunning;

    private boolean networkReady;

    private String apkName;



    public void setDnsRunning(boolean status){

        dnsRunning = status;

    }



    public void setWebRunning(boolean status){

        webRunning = status;

    }



    public void setNetworkReady(boolean status){

        networkReady = status;

    }



    public void setApkName(String name){

        apkName = name;

    }



    public String getStatus(){


        return

        "DNS服务："
        + (dnsRunning ? "运行中" : "停止")
        + "\n\n"

        + "网页服务："
        + (webRunning ? "运行中" : "停止")
        + "\n\n"

        + "网络："
        + (networkReady ? "正常" : "未连接")
        + "\n\n"

        + "APK："
        + (apkName == null ? "未选择" : apkName);


    }


}
