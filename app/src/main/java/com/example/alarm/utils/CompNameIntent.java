package com.example.alarm.utils;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Handler;
//import android.os.SystemProperties;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
//import androidx.core.os.EnvironmentCompat;
//import com.realtek.tv.PQ;
//import com.src.ui.kjdsidepull.R;
//import com.src.ui.kjdsidepull.utils.Constant;
import com.example.alarm.R;
import com.example.alarm.widget.Constant;

import java.lang.reflect.Method;

/* loaded from: classes2.dex */
public class CompNameIntent {
    private static final String TAG = "CompNameIntent";
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    private ConnectivityManager mConnectivityManager;
    Context mContext;
    WifiManager mWifiManager;
    AudioManager sAudioManager;
    private int apk_which = 0;
    //private PQ mPQ = new PQ();

    public CompNameIntent(Context context) {
        this.mContext = context;
        try {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService(Service.WIFI_SERVICE);
            this.sAudioManager = (AudioManager) this.mContext.getSystemService(Service.AUDIO_SERVICE);
            BluetoothManager bluetoothManager = (BluetoothManager) this.mContext.getSystemService(Service.BLUETOOTH_SERVICE);
            this.bluetoothManager = bluetoothManager;
            this.mBluetoothAdapter = bluetoothManager.getAdapter();
            this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService(Service.CONNECTIVITY_SERVICE);
        } catch (Exception e) {
        }
    }

    public boolean checkApkExist(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        try {
            this.mContext.getPackageManager().getApplicationInfo(str, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public void Jump(String str, String str2) {
        try {
            ComponentName componentName = new ComponentName(str, str2);
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setComponent(componentName);
            //intent.setFlags(270532608);
            this.mContext.startActivity(intent);
        } catch (Exception e) {
            ToastTime(this.mContext.getResources().getString(R.string.package_not_find_ERROR_1));
        }
    }

    void jumpMenu() {
        Intent intent = new Intent();
        intent.setAction("com.mstar.tv.tvplayer.ui.intent.action.MAINMENU");
        //intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    public void JumpValues(String str, String str2, String str3, int i) {
        try {
            ComponentName componentName = new ComponentName(str, str2);
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.putExtra(str3, i);
            intent.setComponent(componentName);
            //intent.setFlags(270532608);
            this.mContext.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public void WhereBoard() {
    }

    public void jumpMenu(String str) {
        try {
            Intent intent = new Intent();
            intent.setAction(str);
            //intent.setFlags(268435456);
            this.mContext.startActivity(intent);
        } catch (Exception e) {
        }
    }

    public void ToastTime(String str) {
        final Toast makeText = Toast.makeText(this.mContext, str, Toast.LENGTH_LONG);
        makeText.setGravity(17, 0, 0);
        makeText.show();
        new Handler().postDelayed(new Runnable() { // from class: com.src.ui.kjdsidepull.utils.CompNameIntent.1
            @Override // java.lang.Runnable
            public void run() {
                makeText.cancel();
            }
        }, 3000L);
    }

    public boolean isForeground(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE)).getRunningTasks(1)) {
            if (runningTaskInfo.topActivity.getShortClassName().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isRunPackage(Context context, String str) {
        if (context == null || TextUtils.isEmpty(str)) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo : ((ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE)).getRunningTasks(1)) {
            if (runningTaskInfo.topActivity.getPackageName().contains(str)) {
                return true;
            }
        }
        return false;
    }

    public String getProperty(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, EnvironmentCompat.MEDIA_UNKNOWN);
            } catch (Exception e) {
                e.printStackTrace();
                return str2;
            }
        } catch (Throwable th) {
            return str2;
        }
    }

    public void Instrumentation(final int i) {
        try {
            new Thread(new Runnable() { // from class: com.src.ui.kjdsidepull.utils.CompNameIntent.2
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        new Instrumentation().sendKeyDownUpSync(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void JumpHome() {
        Intent intent = new Intent("android.intent.action.MAIN");
        //intent.setFlags(268435456);
        intent.addCategory("android.intent.category.HOME");
        this.mContext.startActivity(intent);
    }

    public void Back() {
        Instrumentation(4);
    }

    public void Home() {
        Intent intent = new Intent("android.intent.action.MAIN");
        //intent.setFlags(268435456);
        intent.addCategory("android.intent.category.HOME");
        this.mContext.startActivity(intent);
    }

    public void Task() {
        Instrumentation(187);
    }

    public void Annotation() {
        Jump("com.kjd.draw", "com.kjd.draw.annotation.AnnotationActivity");
    }

    public int getStreamVolume() {
        try {
            return this.sAudioManager.getStreamVolume(3);
        } catch (Exception e) {
            return 0;
        }
    }

    public void setStreamVolume(int i) {
        try {
            this.sAudioManager.setStreamVolume(3, i, 0);
        } catch (Exception e) {
        }
    }

   /* public int getBacklight() {
        return this.mPQ.getBacklight();
    }

    public void setBacklight(int i) {
        this.mPQ.setBacklight(i);
    }*/

    /*public void JumpSetting(int i) {
        try {
            Intent launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(Constant.KJDSetting.PACKAGE);
            launchIntentForPackage.setFlags(268468224);
            launchIntentForPackage.putExtra(Constant.KJDSetting.TAG, i);
            this.mContext.startActivity(launchIntentForPackage);
        } catch (ActivityNotFoundException e) {
        }
    }*/

    public boolean isWIfiOpen() {
        return this.mWifiManager.isWifiEnabled();
    }

    public void OpenWIFI() {
        /*if (!this.mWifiManager.isWifiEnabled()) {
            if (!SystemProperties.get(Constant.SysProp.DUAL_WIFI).equals("true")) {
                this.mConnectivityManager.stopTethering(0);
            }
            this.mWifiManager.setWifiEnabled(true);
            isWiredNetOpen().booleanValue();
            return;
        }
        this.mWifiManager.setWifiEnabled(false);*/
    }

    public boolean isBluetoothOpen() {
        return this.mBluetoothAdapter.isEnabled();
    }

    public void OpenBluetooth() {
        if (isBluetoothOpen()) {
            this.mBluetoothAdapter.disable();
        } else {
            this.mBluetoothAdapter.enable();
        }
    }

    public Boolean isWiredNetOpen() {
        return true;
    }

    public void OpenWired_network() {
        if (!isWiredNetOpen().booleanValue() && this.mWifiManager.isWifiEnabled()) {
            this.mWifiManager.setWifiEnabled(false);
        }
    }

    public boolean isWIfiHotOpen() {
        try {
            Method method = this.mWifiManager.getClass().getMethod("isWifiApEnabled", new Class[0]);
            method.setAccessible(true);
            return ((Boolean) method.invoke(this.mWifiManager, new Object[0])).booleanValue();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

}
