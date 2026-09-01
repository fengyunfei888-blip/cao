package com.cardev.deploy;


public class ApkDownloadHandler {


    private String apkPath = "";


    public void setApkPath(String path){

        apkPath = path;

    }



    public String getApkPath(){

        return apkPath;

    }



    public boolean hasApk(){


        return apkPath != null 
                && !apkPath.isEmpty();


    }



    public String getDownloadName(){


        if(!hasApk()){

            return "暂无APK";

        }


        int index = apkPath.lastIndexOf("/");


        if(index >= 0){

            return apkPath.substring(index + 1);

        }


        return apkPath;


    }



}
