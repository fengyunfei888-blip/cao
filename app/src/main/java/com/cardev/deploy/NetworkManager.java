package com.cardev.deploy;


public class NetworkManager {


    public String getLocalIp(){

        // 后续替换成真实网络获取

        return "192.168.43.1";

    }


    public boolean isNetworkAvailable(){

        return true;

    }


    public String getServerAddress(){

        return "http://" 
                + getLocalIp()
                + ":8080";

    }


}
