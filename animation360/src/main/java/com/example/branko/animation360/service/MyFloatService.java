package com.example.branko.animation360.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.branko.animation360.engine.FloatViewManager;

public class MyFloatService extends Service {
    public MyFloatService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        FloatViewManager manager=FloatViewManager.getFloatViewManagerInstance(this);
        manager.showFloatCircleView();
        super.onCreate();
    }
}
