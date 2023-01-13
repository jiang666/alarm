package com.example.alarm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.alarm.service.SideService;
import com.example.alarm.widget.Utils;

/* loaded from: classes2.dex */
public class RebootReceiver extends BroadcastReceiver {
    private Context mContext;

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d("ui", ">>>>>>>>>>>>>..RebootReceiver");
            try {
                if (!Utils.isServiceRunning("com.example.alarm.service.SideService", context)) {
                    Log.d("ui", "start SideService");
                    Intent intent2 = new Intent(context, SideService.class);
                    //intent2.addFlags(268435456);
                    context.startService(intent2);
                }
            } catch (Exception e) {
                Log.d("e", ">>>>>SideService.class>>>>>>>>.." + e);
            }
        }
    }
}
