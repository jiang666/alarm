package com.example.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

/**
 * Created by jianglei on 2018/1/14.
 */

public class ClockActivity extends Activity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.a235);
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();
        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this).setTitle("闹钟").setMessage("小猪小猪快起床")
        .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();
    }
}
