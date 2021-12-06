package com.example.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static java.lang.Thread.sleep;

/**
 * 定时器
 */
public class AlermActivity extends AppCompatActivity {
    private PendingIntent pi;
    private static String TAG = AlermActivity.class.getSimpleName();
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Intent intent = new Intent(MainActivity.this,AlarmService.class);
        intent.putExtra("msg","你该打酱油了");
        PendingIntent pi = PendingIntent.getService(MainActivity.this,0,intent,0);
        Log.d("MyTag", "======");
//AlarmManager对象,注意这里并不是new一个对象，Alarmmanager为系统级服务
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

//设置闹钟从当前时间开始，每隔5s执行一次PendingIntent对象pi，注意第一个参数与第二个参数的关系
// 5秒后通过PendingIntent pi对象发送广播
        am.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),10*1000,pi);*/

        // ①获取AlarmManager对象:
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
        Intent intent = new Intent(AlermActivity.this, ClockActivity.class);
        pi = PendingIntent.getActivity(AlermActivity.this, 0, intent, 0);
        Calendar currentTime = Calendar.getInstance();
        new TimePickerDialog(this, 0, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //设置当前时间
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                // 根据用户选择的时间来设置Calendar对象
                c.set(Calendar.HOUR, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                Log.e("=====time====",c.getTimeInMillis()+"");
                // ②设置AlarmManager在Calendar对应的时间启动Activity
                alarmManager.set(AlarmManager.RTC_WAKEUP,
                        c.getTimeInMillis(), pi);
                // 提示闹钟设置完毕:
                Toast.makeText(AlermActivity.this, "闹钟设置完毕",
                        Toast.LENGTH_SHORT).show();
            }
        },currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
    }

}
