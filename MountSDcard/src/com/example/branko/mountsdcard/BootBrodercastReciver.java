package com.example.branko.mountsdcard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBrodercastReciver extends BroadcastReceiver {
    static final String BOOTACTION="android.intent.action.BOOT_COMPLETED";
    static final String MOUNTSDCARD="android.intent.action.MEDIA_MOUNTED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        if (intent.getAction().equals(BOOTACTION)) {
            Log.i("zhuya","recived"+intent.getAction().equals(MOUNTSDCARD));
            Log.i("zhuya","recived"+intent.getAction().equals(BOOTACTION));
            Intent isMonutSdcard = new Intent(context, MainActivity.class);
            isMonutSdcard.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(intent.getAction().equals(MOUNTSDCARD)){
                context.startActivity(isMonutSdcard);
            }
        }
    }
}
