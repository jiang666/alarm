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

import com.example.alarm.adapter.SoundSpaceRecyclerAdapter;
import com.example.alarm.bean.MergeViewBean;
import com.example.alarm.utils.DeviceManager;
import com.example.alarm.utils.UIUtils;
import com.example.alarm.widget.LeftRightLayoutManager;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 拖拽合并
 */

public class MergeViewActivity extends Activity {
    private static final String TAG = "MergeViewActivity";

    @BindView(R.id.bt_updata)
    Button btUpdata;
    @BindView(R.id.rv_test)
    RecyclerView listView;
    List<String> list = new ArrayList<>();
    @BindView(R.id.tv_show)
    TextView tvShow;
    private TestAdapter testAdapter;
    private int click = 0;
    Handler mHandler = new Handler();
    private int spanCount =6;
    private int currentScrollY = 0;
    private LeftRightLayoutManager leftRightLayoutManager;
    private List<MergeViewBean> nodes; // speakers and groups
    private SoundSpaceRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mergeview);
        ButterKnife.bind(this);
        nodes = new ArrayList<>();
        // listView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        // listView.setLayoutManager(new LinearLayoutManager());
        leftRightLayoutManager = new LeftRightLayoutManager(this);
        listView.setLayoutManager(leftRightLayoutManager);
        listView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int pos = leftRightLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (pos == 0) {
                    currentScrollY = 0;
                }
                currentScrollY += dy;
                if (UIUtils.px2Dip(currentScrollY) > 50) {
                    //fab.hide(true);
                } else {
                    //fab.show(true);
                }
            }
        });
        nodes.clear();
        nodes = DeviceManager.getInstance().getGroupsAndSpeakers();
        mAdapter = new SoundSpaceRecyclerAdapter(this, nodes, listView);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG,"newConfig orientation " + newConfig.orientation);
    }

}
