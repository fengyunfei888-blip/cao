package com.cardev.deploy;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private static final int PICK_APK = 1001;

    private AppController controller;
    private TextView targetDomainText;
    private TextView ipText;
    private TextView serviceText;
    private TextView apkText;
    private TextView logText;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable refresher = new Runnable() {
        @Override
        public void run() {
            refreshUi();
            handler.postDelayed(this, 800);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new AppController();

        targetDomainText = findViewById(R.id.targetDomainText);
        ipText = findViewById(R.id.ipText);
        serviceText = findViewById(R.id.serviceText);
        apkText = findViewById(R.id.apkText);
        logText = findViewById(R.id.logText);

        Button selectApkButton = findViewById(R.id.selectApkButton);
        Button startButton = findViewById(R.id.startButton);
        Button stopButton = findViewById(R.id.stopButton);

        selectApkButton.setOnClickListener(v -> chooseApk());
        startButton.setOnClickListener(v -> {
            controller.start();
            refreshUi();
        });
        stopButton.setOnClickListener(v -> {
            controller.stop();
            refreshUi();
        });

        refreshUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(refresher);
    }

    @Override
    protected void onPause() {
        handler.removeCallbacks(refresher);
        super.onPause();
    }

    private void chooseApk() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/vnd.android.package-archive");
        startActivityForResult(intent, PICK_APK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_APK && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                controller.getApkManager().setApkPath(uri.toString());
                controller.getApkDownloadHandler().setApkPath(uri.toString());
                apkText.setText("已选择：" + displayName(uri));
            }
        }
    }

    private String displayName(Uri uri) {
        String name = null;
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (index >= 0) name = cursor.getString(index);
            }
        } catch (Exception ignored) {
        } finally {
            if (cursor != null) cursor.close();
        }
        return name == null ? "APK文件" : name;
    }

    private void refreshUi() {
        String ip = controller.getLocalIp();
        ipText.setText("手机热点地址\n" + ip + "\n" + controller.getWebAddress());

        String domain = controller.getLatestDomain();
        targetDomainText.setText(
                domain == null || domain.isEmpty()
                        ? "等待自动检测域名"
                        : domain
        );

        serviceText.setText(controller.getStatus() + "\nDNS监听端口：" + controller.getDnsPort());

        String logs = controller.getLogs();
        logText.setText(logs == null || logs.isEmpty() ? "等待启动服务..." : logs);
    }
}
