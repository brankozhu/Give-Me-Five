package com.example.branko.mountsdcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart=(Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("zhuya","start");
                PowerManager pm = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
                pm.reboot(null);
            }
        });

    }



}
