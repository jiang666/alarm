package com.example.alarm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * java 调用js
 */

public class NotificationActivity extends AppCompatActivity {


    @BindView(R.id.main_edit)
    TextView mainEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeNotice();
            }
        });
    }

    @SuppressLint("NewApi")
    private void takeNotice() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent push = new Intent(NotificationActivity.this, RecycleViewActivity.class);//新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
        //Intent push =new Intent(MainActivity.this,JavaCallJSActivity.class);//新建一个显式意图，第一个参数 Context 的解释是用于获得 package name，以便找到第二个参数 Class 的位置
        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
        //本程序用来响应点击通知的打开应用,第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
        PendingIntent contentIntent = PendingIntent.getActivity(NotificationActivity.this, 0, push, 0);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name = "bunnytouch";
//         用户可以看到的通知渠道的描述
        String description = "bunnytouch notification";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
        mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(false);
        mChannel.setVibrationPattern(new long[]{0});
//         最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel);

        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("").setContentText("灯控服务开启")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setChannelId(CHANNEL_ID)
                .setContentIntent(contentIntent)
                .build();
        mNotificationManager.notify(0, notification);
        //start(1, notification);
    }
}
