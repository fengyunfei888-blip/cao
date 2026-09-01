package com.cardev.deploy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        TextView status = findViewById(R.id.deviceStatus);


        Button scanButton = findViewById(R.id.scanButton);

        Button connectButton = findViewById(R.id.connectButton);

        Button selectApkButton = findViewById(R.id.selectApkButton);

        Button installButton = findViewById(R.id.installButton);



        scanButton.setOnClickListener(v -> {

            status.setText("设备状态：正在扫描...");

        });



        connectButton.setOnClickListener(v -> {

            status.setText("设备状态：连接中...");

        });



        selectApkButton.setOnClickListener(v -> {

            status.setText("请选择APK文件");

        });



        installButton.setOnClickListener(v -> {

            status.setText("正在安装应用...");

        });

    }
}
