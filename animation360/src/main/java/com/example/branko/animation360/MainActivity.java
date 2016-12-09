package com.example.branko.animation360;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.branko.animation360.service.MyFloatService;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private static final int REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn_hello);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPermissionOpen();
            }
        });
    }


    public void startService() {
        Intent intent = new Intent(this, MyFloatService.class);
        startService(intent);
        finish();
    }


    public void isPermissionOpen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 判断是否有SYSTEM_ALERT_WINDOW权限
            if (!Settings.canDrawOverlays(this)) {
                // 申请SYSTEM_ALERT_WINDOW权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                // REQUEST_CODE2是本次申请的请求码
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                startService();
            }
        } else {
            startService();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 判断是否有SYSTEM_ALERT_WINDOW权限
                if (Settings.canDrawOverlays(this)) {
                    Log.i("zhuya", "laile");
                    startService();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
