package com.example.alarm.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
//import android.os.SystemProperties;
import android.util.Log;
import android.widget.Toast;

import com.example.alarm.R;
import com.example.alarm.utils.WindowManagerUitls;
import com.example.alarm.widget.Constant;
import com.example.alarm.widget.Utils;

/* loaded from: classes2.dex */
public class SideService extends Service {
    protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Log.e("===========",intent.getAction());
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_OPEN_SIDE_ICON)) {
                try {
                    //if (SystemProperties.get(Constant.SysProp.SIDE_MENU, "true").equals("true")) {
                        WindowManagerUitls.createRightOneWindowView(SideService.this);
                    //}
                } catch (Exception e) {
                }
            }

            if (action.equals(Constant.ACTION_REVIEW_SIDE_ICON)) {
                WindowManagerUitls.reviewIconWindowView(context);
            }
            if (action.equals(Constant.ACTION_OPEN_SIDE_ICON)) {
                WindowManagerUitls.removeRightOneWindowView(context);
                WindowManagerUitls.createRightOneWindowView(context);
            }
            if (action.equals(Constant.ACTION_CLOSE_SIDE_ICON)) {
                WindowManagerUitls.removeRightOneWindowView(context);
            }
            if (Constant.ACTION_KEYCODE_SHOT.equals(action)) {
                String obj = intent.getExtras().get("screenshot").toString();
                SideService sideService = SideService.this;
                sideService.ToastTime(context.getResources().getString(R.string.cela_menu_cut) + context.getResources().getString(R.string.annotation_save) + "" + obj);
            }
            if (Constant.ACTION_TVPLAYER_ONRESUME.equals(action)) {
                Utils.setNonThroughRegion(context, 1, Utils.lSideButtonPos[0], Utils.lSideButtonPos[1], Utils.lSideButtonPos[2], Utils.lSideButtonPos[3]);
                Utils.setNonThroughRegion(context, 2, Utils.rSideButtonPos[0], Utils.rSideButtonPos[1], Utils.rSideButtonPos[2], Utils.rSideButtonPos[3]);
                Utils.setNonThroughRegion(context, 3, Utils.floatViewPos[0], Utils.floatViewPos[1], Utils.floatViewPos[2], Utils.floatViewPos[3]);
            }
            if (Constant.ACTION_TVPLAYER_ONPAUSE.equals(action) || Constant.ACTION_TVPLAYER_ONSTOP.equals(action)) {
                Utils.deleteNonThroughRegion(context, 1);
                Utils.deleteNonThroughRegion(context, 2);
                Utils.deleteNonThroughRegion(context, 3);
            }
            if (Constant.ACTION_FLOAT_VIEW_POSITION.equals(action)) {
                Utils.setNonThroughRegion(context, 3, intent.getIntExtra(Constant.X1_VALUE, 0), intent.getIntExtra(Constant.Y1_VALUE, 0), intent.getIntExtra(Constant.X2_VALUE, 0), intent.getIntExtra(Constant.Y2_VALUE, 0));
            }
        }
    };

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override // android.app.Service
    public void onCreate() {
        try {
            //if (SystemProperties.get(Constant.SysProp.SIDE_MENU, "true").equals("true")) {
                WindowManagerUitls.createRightOneWindowView(this);
            //}
        } catch (Exception e) {
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_OPEN_SIDE_ICON);
        intentFilter.addAction(Constant.ACTION_CLOSE_SIDE_ICON);
        intentFilter.addAction(Constant.ACTION_OPEN_SOURCE);
        intentFilter.addAction(Constant.ACTION_KEYCODE_SHOT);
        intentFilter.addAction(Constant.ACTION_TVPLAYER_ONRESUME);
        intentFilter.addAction(Constant.ACTION_TVPLAYER_ONPAUSE);
        intentFilter.addAction(Constant.ACTION_TVPLAYER_ONSTOP);
        intentFilter.addAction(Constant.ACTION_FLOAT_VIEW_POSITION);
        intentFilter.addAction(Constant.ACTION_REVIEW_SIDE_ICON);
        intentFilter.addAction(Constant.ACTION_OPEN_SIDE_MENU);
        intentFilter.addAction(Constant.ACTION_GESTURE_SLIDE_BOTTOM);
        intentFilter.addAction(Constant.ACTION_CLOSE_BOTTOM_MENU);
        registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void ToastTime(String str) {
        final Toast makeText = Toast.makeText(this, str, Toast.LENGTH_LONG);
        makeText.setGravity(17, 0, 0);
        makeText.show();
        new Handler().postDelayed(new Runnable() { // from class: com.src.ui.kjdsidepull.service.SideService.2
            @Override // java.lang.Runnable
            public void run() {
                makeText.cancel();
            }
        }, 3000L);
    }
}
