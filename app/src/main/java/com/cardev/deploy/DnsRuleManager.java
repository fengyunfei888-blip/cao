package com.cardev.deploy;


import java.util.HashMap;


public class DnsRuleManager {


    private HashMap<String,String> rules;



    public DnsRuleManager(){

        rules = new HashMap<>();

    }



    public void addRule(String domain, String ip){

        rules.put(domain, ip);

    }



    public String getIp(String domain){


        if(rules.containsKey(domain)){


            return rules.get(domain);


        }


        return null;

    }



    public void removeRule(String domain){


        rules.remove(domain);


    }



    public void clear(){


        rules.clear();


    }


}
