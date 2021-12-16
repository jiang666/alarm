package com.example.alarm;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.utils.DrawableUtils;
import com.example.alarm.utils.UIUtils;
import com.example.alarm.widget.FlowTwoLayout;
import com.example.alarm.widget.MyFlowLayout;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 属性动画 文本内容收缩和展开
 * Flow
 */

public class FlowTestActivity extends Activity {

    private ArrayList<String> mList;

    @BindView(R.id.ll_top)
    FrameLayout ll_top;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testflow);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("data " + i);
        }
        ScrollView scrollView = new ScrollView(UIUtils.getContext());

        int padding = UIUtils.dip2px(10);
        // 设置ScrollView边距
        scrollView.setPadding(padding, padding, padding, padding);
        // 初始化自定义控件
        MyFlowLayout flow = new MyFlowLayout(this);
        // 水平间距
        //flow.setHorizontalSpacing(UIUtils.dip2px(6));
        // 竖直间距
        //flow.setVerticalSpacing(UIUtils.dip2px(10));

        // 根据接口返回的数据个数,动态添加TextView
        for (final String str : mList) {
            TextView view = new TextView(this);
            view.setText(str);
            view.setTextColor(Color.WHITE);
            view.setGravity(Gravity.CENTER);
            view.setPadding(padding, padding, padding, padding);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

            // 设置随机文字颜色
            Random random = new Random();
            int r = 30 + random.nextInt(210);
            int g = 30 + random.nextInt(210);
            int b = 30 + random.nextInt(210);

            int color = 0xffcecece;// 按下后偏白的背景色

            // 根据默认颜色和按下颜色, 生成圆角矩形的状态选择器
            Drawable selector = DrawableUtils.getStateListDrawable(
                    Color.rgb(r, g, b), color, UIUtils.dip2px(6));

            // 给TextView设置背景
            view.setBackgroundDrawable(selector);

            // 必须设置点击事件, TextView按下后颜色才会变化
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(UIUtils.getContext(), str,
                            Toast.LENGTH_SHORT).show();
                }
            });

            // 给自定义控件添加view对象
            Log.e("=========","flow add " + str);
            flow.addView(view);
        }
        Log.e("=========","scroll add");
        scrollView.addView(flow);
        //setContentView(scrollView);
        ll_top.addView(scrollView);
    }

}
