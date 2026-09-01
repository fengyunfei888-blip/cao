package com.cardev.deploy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {


    private AppController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        controller = new AppController();


        TextView status =
                findViewById(R.id.deviceStatus);


        Button scanButton =
                findViewById(R.id.scanButton);


        Button connectButton =
                findViewById(R.id.connectButton);



        scanButton.setOnClickListener(v -> {


            controller.startService();


            status.setText(
                    controller.getStatus()
            );


        });



        connectButton.setOnClickListener(v -> {


            status.setText(
                    "等待车机连接..."
            );


        });


    }

}
