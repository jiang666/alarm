package com.example.alarm.evenbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alarm.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EvenbusActivity extends AppCompatActivity {

    private static final String TAG = EvenbusActivity.class.getSimpleName();
    @BindView(R.id.btn_try)
    Button btnTry;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evenbus);

        ButterKnife.bind(this);
        //注册
        EventBus.getDefault().register(this);

    }

    /**
     * 事件响应方法
     * 接收消息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {

        String msg = event.getMessgae();
        tv.setText(msg);
    }

    //绑定点击事件
    @OnClick(R.id.btn_try)
    public void openSecondActivity(View view) {
        Intent intent = new Intent(EvenbusActivity.this, EvenbusTActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }
}
