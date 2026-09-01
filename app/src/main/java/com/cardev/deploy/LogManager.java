package com.cardev.deploy;


import java.util.ArrayList;


public class LogManager {


    private ArrayList<String> logs;


    public LogManager(){

        logs = new ArrayList<>();

    }


    public void addLog(String message){

        logs.add(message);

    }


    public String getLogs(){

        StringBuilder result = new StringBuilder();


        for(String log : logs){

            result.append(log)
                    .append("\n");

        }


        return result.toString();

    }


    public void clear(){

        logs.clear();

    }

}
