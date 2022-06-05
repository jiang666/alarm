package com.example.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.alarm.widget.ClockView;

/**
 * 闹钟
 */

public class ClockActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private ClockView clockView;
    private TextView state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        clockView = (ClockView) findViewById(R.id.clock_view);
        state = (TextView) findViewById(R.id.state);
        String str = "GMT+9|日本";
        String[] zoneandename = str.split("\\|");
        clockView.setTimezone(zoneandename[0]);
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
}
