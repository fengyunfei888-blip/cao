package com.cardev.deploy;


public class DnsServer {


    private boolean running = false;


    public void start() {

        running = true;

    }


    public void stop() {

        running = false;

    }


    public boolean isRunning() {

        return running;

    }


    public String resolve(String domain) {


        if(domain == null){

            return null;

        }


        // 后续替换为真实DNS规则

        if(domain.equals("update.car.local")){


            return "192.168.43.1";


        }


        return null;

    }


}
