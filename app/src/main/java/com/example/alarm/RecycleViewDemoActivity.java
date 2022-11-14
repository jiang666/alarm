package com.example.alarm;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.adapter.TestDemoAdapter;
import com.example.alarm.evenbus.EvenbusActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianglei on 2018/1/14.
 */

public class RecycleViewDemoActivity extends Activity {
    private static final String TAG = "RecycleViewActivity";

    @BindView(R.id.bt_updata)
    Button btUpdata;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    List<String> list = new ArrayList<>();
    @BindView(R.id.tv_show)
    TextView tvShow;
    private TestDemoAdapter testAdapter;
    private int click = 0;
    Handler mHandler = new Handler();
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview_demo);
        ButterKnife.bind(this);
        initData();
        testAdapter = new TestDemoAdapter(this, list);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);//瀑布流
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局
        layoutManager = new GridLayoutManager(this,6);//网格布局管理器
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTest.setLayoutManager(layoutManager);
        rvTest.setAdapter(testAdapter);
        testAdapter.setOnItemClickListener(new TestDemoAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //点击条目变颜色
                testAdapter.setOnItem(position);
                tvShow.setText(list.get(position) + position);
                testAdapter.notifyDataSetChanged();
                Toast.makeText(RecycleViewDemoActivity.this, " 点击 " + position, Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG,"newConfig orientation " + newConfig.orientation);
    }

    private void initData() {

        //S型数据  条目个数必须是24 倍数
        for (int i = 0; i < 34; i++) {
            switch (i) {
                case 0:
                    list.add("calljs");
                    break;
                case 1:
                    list.add("clock");
                    break;
                case 2:
                    list.add("main");
                    break;
                case 3:
                    list.add("xml");
                    break;
                case 4:
                    list.add("notification");
                    break;
                case 5:
                    list.add("alerm");
                    break;
                case 6:
                    list.add("anim");
                    break;
                case 7:
                    list.add("camera");
                    break;
                case 8:
                    list.add("滚动文字");
                    break;
                case 9:
                    list.add("推荐");
                    break;
                case 10:
                    list.add("流布局");
                    break;
                case 11:
                    list.add("viewpager");
                    break;
                case 12:
                    list.add("属性动画");
                    break;
                case 13:
                    list.add("kotlin");
                    break;
                case 14:
                    list.add("视频截屏");
                    break;
                case 15:
                    list.add("图案解锁");
                    break;
                case 16:
                    list.add("测试");
                    break;
                case 17:
                    list.add("图片移动");
                    break;
                case 18:
                    list.add("蓝牙服务端");
                    break;
                case 19:
                    list.add("蓝牙接收端");
                    break;
                case 20:
                    list.add("顶部移动");
                    break;
                case 21:
                    list.add("evenbus使用");
                    break;
                case 22:
                    list.add("底部切换动画");
                    break;
                case 23:
                    list.add("串口读取");
                    break;
                case 24:
                    list.add("下拉刷新");
                    break;
                case 25:
                    list.add("socket");
                    break;
                case 26:
                    list.add("流式布局");
                    break;
                case 27:
                    list.add("图片左右滑动");
                    break;
                case 28:
                    list.add("绘制图形");
                    break;
                case 29:
                    list.add("拖拽合并");
                    break;
                case 30:
                    list.add("手指滑动");
                    break;
                case 31:
                    list.add("PPT");
                    break;
                case 32:
                    list.add("圆弧图片");
                    break;
                case 33:
                    list.add("DrawRecycleViewActivity");
                    break;
                default:
                    list.add("item" + i);
                    break;
            }
        }
        Log.e("=======","list = " + new Gson().toJson(list));
    }
}
