package com.cardev.deploy;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout root = new LinearLayout(this);

        root.setOrientation(LinearLayout.VERTICAL);

        root.setPadding(40,40,40,40);

        root.setGravity(Gravity.CENTER_HORIZONTAL);


        TextView title = new TextView(this);

        title.setText("CarDeployAssistant\n\n车机应用部署助手");

        title.setTextSize(26);

        title.setTextColor(Color.BLACK);

        title.setGravity(Gravity.CENTER);


        root.addView(title);



        TextView status = new TextView(this);

        status.setText("\n设备状态：未连接");

        status.setTextSize(18);


        root.addView(status);



        Button scan = new Button(this);

        scan.setText("扫描设备");

        root.addView(scan);



        Button connect = new Button(this);

        connect.setText("连接车机");

        root.addView(connect);



        Button select = new Button(this);

        select.setText("选择APK");

        root.addView(select);



        Button install = new Button(this);

        install.setText("安装应用");

        root.addView(install);



        setContentView(root);

    }
}
