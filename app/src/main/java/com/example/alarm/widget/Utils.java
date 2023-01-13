package com.example.alarm.widget;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.util.List;

/* loaded from: classes2.dex */
public class Utils {
    public static int[] lSideButtonPos = {0, 0, 0, 0};
    public static int[] rSideButtonPos = {0, 0, 0, 0};
    public static int[] floatViewPos = {0, 0, 0, 0};

    public static void setNonThroughRegion(Context context, int i, int i2, int i3, int i4, int i5) {
        deleteNonThroughRegion(context, i);
        Intent intent = new Intent(Constant.ACTION_SET_NON_THROUGH_REGION);
        intent.putExtra(Constant.REGION_ID, i);
        intent.putExtra(Constant.X1_VALUE, i2);
        intent.putExtra(Constant.Y1_VALUE, i3);
        intent.putExtra(Constant.X2_VALUE, i4);
        intent.putExtra(Constant.Y2_VALUE, i5);
        if (i == 1) {
            int[] iArr = lSideButtonPos;
            iArr[0] = i2;
            iArr[1] = i3;
            iArr[2] = i4;
            iArr[3] = i5;
        } else if (i == 2) {
            int[] iArr2 = rSideButtonPos;
            iArr2[0] = i2;
            iArr2[1] = i3;
            iArr2[2] = i4;
            iArr2[3] = i5;
        } else if (i == 3) {
            int[] iArr3 = floatViewPos;
            iArr3[0] = i2;
            iArr3[1] = i3;
            iArr3[2] = i4;
            iArr3[3] = i5;
        }
        if ("HiTvLauncher".equals("HiTvPlayer")) {
            context.sendBroadcast(intent);
        }
    }

    public static void deleteNonThroughRegion(Context context, int i) {
        Intent intent = new Intent(Constant.ACTION_DEL_NON_THROUGH_REGION);
        intent.putExtra(Constant.REGION_ID, i);
        context.sendBroadcast(intent);
    }

    public static boolean isTopPackage(Context context, String str) {
        String str2;
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(1);
        if (runningTasks == null) {
            str2 = null;
        } else {
            str2 = runningTasks.get(0).topActivity.getPackageName().toString();
        }
        if (str2 == null) {
            return false;
        }
        Log.d("zhh", " cmpNameTemp = " + str2);
        return str2.equals(str);
    }

    public static boolean isServiceRunning(String str, Context context) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService("activity")).getRunningServices(100)) {
            if (runningServiceInfo.service.getClassName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public static void startAppAction(Context context, String str) {
        Intent intent = new Intent(str);
        intent.setFlags(270532608);
        context.startActivity(intent);
    }

    public static void startAppPackageName(Context context, String str) {
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(str);
        if (launchIntentForPackage != null) {
            context.startActivity(launchIntentForPackage);
        }
    }

    public static void startAppComponentName(Context context, String str, String str2) {
        ComponentName componentName = new ComponentName(str, str2);
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.setComponent(componentName);
        intent.setFlags(270532608);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

   /* public static boolean isSignalStable(int i) {
        return TvSourceManager.getInstance().isSignalStable(i);
    }

    public static int getCurInputSource() {
        return TvSourceManager.getInstance().getCurInputSource();
    }

    public static void startTvPlyaer(int i) {
        TvSourceManager.getInstance().startTvPlyaer(i);
    }*/
}
