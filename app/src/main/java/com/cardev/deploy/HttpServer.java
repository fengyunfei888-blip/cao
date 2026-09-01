package com.cardev.deploy;


public class HttpServer {


    private boolean running = false;


    public void start(){

        running = true;

    }


    public void stop(){

        running = false;

    }


    public boolean isRunning(){

        return running;

    }


    public String getUrl(){

        return "http://192.168.43.1:8080";

    }


    public String getHomePage(){


        return

        "<html>" +

        "<head>" +

        "<meta charset='utf-8'>" +

        "<title>车机应用部署助手</title>" +

        "</head>" +


        "<body>" +

        "<h2>车机应用部署助手</h2>" +

        "<p>发现安装包</p>" +

        "<button>下载安装</button>" +

        "</body>" +


        "</html>";

    }


}
