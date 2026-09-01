package com.cardev.deploy;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;


public class PermissionManager {


    private Activity activity;



    public PermissionManager(Activity activity){

        this.activity = activity;

    }




    public boolean hasNetworkPermission(){


        return activity.checkSelfPermission(
                Manifest.permission.INTERNET
        )
        == PackageManager.PERMISSION_GRANTED;


    }




    public void requestBasicPermissions(){


        if(android.os.Build.VERSION.SDK_INT >= 23){


            activity.requestPermissions(

                    new String[]{

                            Manifest.permission.INTERNET,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.ACCESS_WIFI_STATE

                    },

                    100

            );


        }


    }


}
