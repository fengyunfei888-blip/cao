package com.cardev.deploy;


import java.util.HashMap;


public class DnsRuleConfig {


    private HashMap<String,String> rules;


    public DnsRuleConfig(){


        rules = new HashMap<>();


        // 默认测试规则

        rules.put(
                "update.car.local",
                "192.168.43.1"
        );


    }



    public void addRule(
            String domain,
            String ip
    ){

        rules.put(domain, ip);

    }



    public String getIp(
            String domain
    ){

        return rules.get(domain);

    }



    public HashMap<String,String> getRules(){

        return rules;

    }


}
