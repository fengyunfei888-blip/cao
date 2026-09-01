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


}
