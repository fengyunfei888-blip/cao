package com.cardev.deploy;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this);

        tv.setText(
            "车机应用部署助手\n\n初始化完成"
        );

        setContentView(tv);
    }
}
