package com.example.alarm;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jianglei on 2018/1/14.
 */

public class AlarmService extends IntentService {
    private static final String TAG = AlarmService.class.getSimpleName();

    public AlarmService() {
        super(TAG);
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // TODO Auto-generated method stub
        Log.d("MyTag", intent.getStringExtra("msg"));
        //String msg = intent.getStringExtra("msg");
        //Toast.makeText(AlarmService.this,msg,Toast.LENGTH_SHORT).show();
    }

}
