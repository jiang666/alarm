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
        testAdapter = new TestAdapter(this,list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTest.setLayoutManager(layoutManager);
        rvTest.setAdapter(testAdapter);
        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click = click +1;
                list.clear();
                for (int i = click; i < 30 ; i++) {
                    list.add("ts " + i);
                }
                testAdapter.notifyDataSetChanged();
            }
        });
        testAdapter.setOnItemClickListener(new TestAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(RecycleViewActivity.this," 点击 " + position,Toast.LENGTH_LONG).show();
            }
        });
    }
}
