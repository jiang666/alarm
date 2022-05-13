package com.example.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alarm.adapter.RefreshTestAdapter;
import com.example.alarm.widget.RefreshRecyclerView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RefreshRecycleViewActivity 下拉刷新
 */

public class RefreshRecycleViewActivity extends Activity {
    private static final String TAG = "RefreshRecycleViewActivity";

    @BindView(R.id.bt_updata)
    Button btUpdata;
    @BindView(R.id.rv_test)
    RefreshRecyclerView rvTest;
    List<String> list = new ArrayList<>();
    @BindView(R.id.tv_show)
    TextView tvShow;
    private RefreshTestAdapter testAdapter;
    private int click = 0;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_recycleview);
        ButterKnife.bind(this);
        initData();
        testAdapter = new RefreshTestAdapter(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTest.setLayoutManager(layoutManager);
        rvTest.setAdapter(testAdapter);
        rvTest.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("=======","onRefresh");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvTest.hideHeaderView(true);
                    }
                },3000);
            }
        });
        rvTest.setOnLoadMoreListener(new RefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("=======","onLoadMore");
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rvTest.hideFooterView();
                    }
                },3000);
            }
        });
        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click = click + 1;
                if (click % 2 == 0) {
                    list.clear();
                    for (int i = click; i < 30; i++) {
                        list.add("ts " + i);
                    }
                } else {
                    initData();
                }

                testAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initData() {
        //S型数据  条目个数必须是24 倍数
        for (int i = 0; i < 24; i++) {
            switch (i) {
                case 0:
                    list.add("calljs");
                    break;
                case 1:
                    list.add("Clock");
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
                    list.add("顶部移动");
                    break;
                case 19:
                    list.add("evenbus使用");
                    break;
                case 20:
                    list.add("底部切换动画");
                    break;
                case 21:
                    list.add("串口读取");
                    break;
                default:
                    list.add("item" + i);
                    break;
            }
        }
    }
}
