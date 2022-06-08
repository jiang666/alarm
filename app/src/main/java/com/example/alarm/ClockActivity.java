package com.example.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.alarm.widget.ClockView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 闹钟
 */

public class ClockActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private ClockView clockView;
    private TextView state;
    //handler
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            drawClock(zoneande);
        }
    };
    private String zoneande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        clockView = (ClockView) findViewById(R.id.clock_view);
        state = (TextView) findViewById(R.id.state);
        //String str = "GMT+9|日本";
        String str = "UTC+9|日本";
        String[] zoneandename = str.split("\\|");
        //clockView.setTimezone(zoneandename[0]);
        zoneande = zoneandename[0];
        drawClock(zoneande);
        state.setText(zoneandename[1]);
        mediaPlayer = MediaPlayer.create(this, R.raw.a235);
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this).setTitle("闹钟").setMessage("小猪小猪快起床")
        .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                //ClockActivity.this.finish();
            }
        }).show();
        //使用|作为分隔符,其余特殊字符同理
        //String[] split = str.split("\\|");
        String name = "000-0000";
        name = name.split("-")[0];
        //pagerMap.put(name, 000000000);

        String nnn = "000000";
        nnn = nnn.split("-")[0];
        //pagerMap.put(nnn, 1111111);

        Log.e("=====", " name " + name + " nnn = " + nnn);

    }
    private void drawClock(String timezone){
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat d_f = new SimpleDateFormat(format);//设置日期格式
        d_f.setTimeZone(TimeZone.getTimeZone(timezone));  //设置时区，+08是北京时间
        String date = d_f.format(new Date());
        Log.e("=========","data  " + date);
        Date zonedate = new Date();
        try {
            zonedate = stringToDate(date,format);
        }catch (
                ParseException e){

        }
        clockView.setTimezoneData(zonedate);
        mHandler.sendEmptyMessageDelayed(1, 1000);
        Log.e("=========","偏差 " + (zonedate.getTime() - new Date().getTime())/60000);
    }
    private Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = formatter.parse(strTime);
        return date;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
    }
}
