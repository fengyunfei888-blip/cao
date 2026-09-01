package com.cardev.deploy;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends Activity {


    private AppController controller;


    private TextView status;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);



        controller = new AppController();



        status = findViewById(R.id.deviceStatus);



        Button scanButton =
                findViewById(R.id.scanButton);



        Button connectButton =
                findViewById(R.id.connectButton);



        Button selectApkButton =
                findViewById(R.id.selectApkButton);



        Button installButton =
                findViewById(R.id.installButton);





        scanButton.setOnClickListener(v -> {


            controller.start();


            status.setText(
                    controller.getStatus()
                    +
                    "\n\n"
                    +
                    controller.getLogs()
            );


        });





        connectButton.setOnClickListener(v -> {


            String address =
                    controller
                    .getHotspotManager()
                    .getWebAddress();



            status.setText(

                    "车机访问地址：\n"
                    +
                    address

            );


        });





        selectApkButton.setOnClickListener(v -> {


            status.setText(

                    "APK选择功能准备中"

            );


        });





        installButton.setOnClickListener(v -> {


            status.setText(

                    "部署服务运行中"

            );


        });


    }


}
