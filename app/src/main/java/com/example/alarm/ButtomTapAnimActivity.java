package com.example.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * 底部按钮切换动画
 * Intent intentreboot = new Intent();
 *                 intentreboot.setAction("android.intent.action.set.time");
 *                 intentreboot.putExtra("time",new Date().getTime());
 *                 this.sendBroadcast(intentreboot);
 */
public class ButtomTapAnimActivity extends AppCompatActivity {
    private static String TAG = ButtomTapAnimActivity.class.getSimpleName();
    private boolean mReceiverTag = false;
    private UpdateMeetinglistReceiver meetinglistreceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttomtapanim);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mReceiverTag){
            mReceiverTag = true;
            meetinglistreceiver = new UpdateMeetinglistReceiver();
            IntentFilter downloadfilter = new IntentFilter();
            downloadfilter.addAction(Constants.ACTION_END_STANDBY);
            registerReceiver(meetinglistreceiver, downloadfilter);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mReceiverTag){
            mReceiverTag = false;
            unregisterReceiver(meetinglistreceiver);
        }
    }
    private class UpdateMeetinglistReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG,"action " + intent.getAction() + " time = " + intent.getLongExtra("time",0));
        }
    }
}
