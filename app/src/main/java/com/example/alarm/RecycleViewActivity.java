package com.example.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianglei on 2018/1/14.
 */

public class RecycleViewActivity extends Activity {

    @BindView(R.id.bt_updata)
    Button btUpdata;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    List<String> list = new ArrayList<>();
    private TestAdapter testAdapter;
    private int click = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        ButterKnife.bind(this);
        initData();
        testAdapter = new TestAdapter(this,list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTest.setLayoutManager(layoutManager);
        rvTest.setAdapter(testAdapter);
        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                click = click +1;
                if(click % 2 ==0){
                    list.clear();
                    for (int i = click; i < 30 ; i++) {
                        list.add("ts " + i);
                    }
                }else {
                    initData();
                }

                testAdapter.notifyDataSetChanged();
            }
        });
        testAdapter.setOnItemClickListener(new TestAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intoItem(position);
                Toast.makeText(RecycleViewActivity.this," 点击 " + position,Toast.LENGTH_LONG).show();
            }
        });
    }
    private void initData() {

        for ( int i=0; i < 10; i++) {
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
                default:
                    list.add("item" + i);
                    break;
            }
        }
    }
    private void intoItem(int position){
        String item = (String) list.get(position);
        Intent intent = null;
        switch (item) {
            case "calljs":
                intent = new Intent(this, JavaCallJSActivity.class);
                startActivity(intent);
                break;
            case "clock":
                intent = new Intent(this, ClockActivity.class);
                startActivity(intent);
                break;
            case "main":
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case "xml":
                intent = new Intent(this, XMLparseActivity.class);
                startActivity(intent);
                break;
            case "notification":
                intent = new Intent(this, NotificationActivity.class);
                startActivity(intent);
                break;
            case "alerm":
                intent = new Intent(this, AlermActivity.class);
                startActivity(intent);
                break;
            case "anim":
                intent = new Intent(this, AnimationActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
