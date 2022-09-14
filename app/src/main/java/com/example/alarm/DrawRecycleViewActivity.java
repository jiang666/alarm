package com.example.alarm;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.res.Configuration;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v13.view.DragStartHelper;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alarm.adapter.DrawTestAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.alarm.BaseApplication.getContext;

/**
 * https://github.com/googlearchive/android-DragAndDropAcrossApps
 * https://github.com/android/user-interface-samples
 * https://developer.android.google.cn/guide/topics/ui/drag-drop.html#additional_resources
 * 拖拽
 */

public class DrawRecycleViewActivity extends Activity {
    private static final String TAG = "RecycleViewActivity";

    List<String> list = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.delete)
    TextView delete;
    @BindView(R.id.iv_draw)
    ImageView ivDraw;
    private DrawTestAdapter testAdapter;
    private int spanCount = 6;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_recycleview);
        ButterKnife.bind(this);
        initData();
        testAdapter = new DrawTestAdapter(this, list);
        testAdapter.setRowSize(spanCount);
        //StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL);//瀑布流
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);//线性布局
        layoutManager = new GridLayoutManager(this, spanCount);//网格布局管理器
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(testAdapter);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ivDraw.startDragAndDrop(null, new View.DragShadowBuilder(ivDraw),ivDraw, View.DRAG_FLAG_OPAQUE);//步骤1
        } else {
            //View.DRAG_FLAG_OPAQUE 该属性 表示不使用半透明属性，可以点进源码查看，有对应好几种不同的Flag，可以使用自己想用的
            ivDraw.startDrag(null, new View.DragShadowBuilder(ivDraw), ivDraw, View.DRAG_FLAG_OPAQUE);//步骤1
        }*/
        ivDraw.setTag("00000000");
        setUpDraggableImage(ivDraw);
        ivDraw.setTag("00000000");
        ivDraw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e("======", "ivDraw onTouch ");
                        break;
                }

                return false;
            }
        });
        delete.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {

                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.e("======", "DragEvent.ACTION_DRAG_STARTED  开始 " + event.getClipDescription().getLabel().toString());
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.e("======", "DragEvent.ACTION_DRAG_ENTERED  进入");
                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.e("======", "DragEvent.ACTION_DRAG_LOCATION  位置");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.e("======", "DragEvent.ACTION_DRAG_EXITED  退出");
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.e("======", "DragEvent.ACTION_DROP  下降");
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.e("======", "DragEvent.ACTION_DRAG_ENDED  结束");
                        return true;

                    default:
                        break;
                }
                return true;
            }
        });
    }
    private void setUpDraggableImage(ImageView imageView) {

        // Set up a listener that starts the drag and drop event with flags and extra data.
        DragStartHelper.OnDragStartListener listener = new DragStartHelper.OnDragStartListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onDragStart(View view, final DragStartHelper helper) {
                Log.d(TAG, "Drag start event received from helper.");

                // Use a DragShadowBuilder
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view) {
                    @Override
                    public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
                        super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
                        // Notify the DragStartHelper of point where the view was touched.

                        helper.getTouchPosition(shadowTouchPoint);
                        Log.d(TAG, "View was touched at: " + shadowTouchPoint);
                    }
                };

                // Set up the flags for the drag event.
                // Enable drag and drop across apps (global)
                // and require read permissions for this URI.
                //int flags = View.DRAG_FLAG_GLOBAL | View.DRAG_FLAG_GLOBAL_URI_READ;
                int flags = View.DRAG_FLAG_OPAQUE;
                // Add an optional clip description that that contains an extra String that is
                // read out by the target app.

                // The ClipData object describes the object that is being dragged and dropped.
                //final ClipData clipData = ClipData.newPlainText("666","6666");
                ClipData.Item item = new ClipData.Item((CharSequence) imageView.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(imageView.getTag().toString(), mimeTypes, item);
                Log.d(TAG, "Created ClipDescription. Starting drag and drop.");
                // Start the drag and drop event.
                return view.startDragAndDrop(dragData, shadowBuilder, imageView, flags);

            }

        };

        // Use the DragStartHelper to detect drag and drop events and use the OnDragStartListener
        // defined above to start the event when it has been detected.
        DragStartHelper helper = new DragStartHelper(imageView, listener);
        helper.attach();
        Log.d(TAG, "DragStartHelper attached to view.");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "newConfig orientation " + newConfig.orientation);
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
                default:
                    list.add("item" + i);
                    break;
            }
        }
        /**
         * 解决最后一列向上又数据未满
         */
        int listsize = list.size();
        int aa = listsize % spanCount;
        int add = spanCount - aa;
        for (int i = 0; i < add; i++) {
            list.add(listsize - aa + i, "null");
        }
        Log.e("=======", "list = " + new Gson().toJson(list));
    }
}
