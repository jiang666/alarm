package com.example.alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.evenbus.EvenbusActivity;
import com.example.alarm.widget.RefreshRecyclerView;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jianglei on 2018/1/14.
 */

public class RecycleViewActivity extends Activity {
    private static final String TAG = "RecycleViewActivity";

    @BindView(R.id.bt_updata)
    Button btUpdata;
    @BindView(R.id.rv_test)
    RecyclerView rvTest;
    List<String> list = new ArrayList<>();
    @BindView(R.id.tv_show)
    TextView tvShow;
    private TestAdapter testAdapter;
    private int click = 0;
    Handler mHandler = new Handler();
    private int spanCount =6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        ButterKnife.bind(this);
        initData();
        testAdapter = new TestAdapter(this, list);
        testAdapter.setRowSize(spanCount);
        GridLayoutManager layoutManager = new GridLayoutManager(this,spanCount);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvTest.setLayoutManager(layoutManager);
        rvTest.setAdapter(testAdapter);
        btUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*click = click + 1;
                if (click % 2 == 0) {
                    list.clear();
                    for (int i = click; i < 30; i++) {
                        list.add("ts " + i);
                    }
                } else {
                    initData();
                }
                testAdapter.notifyDataSetChanged();*/
                for (int i = 0; i < list.size(); i++) {
                    //if("Clock".equals(list.get(i))){
                    if("item30".equals(list.get(i))){
                        testAdapter.setOnItem(i);
                        testAdapter.notifyDataSetChanged();
                        return;
                    }
                }
            }
        });
        testAdapter.setOnItemClickListener(new TestAdapter.onRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(int position) {
                intoItem(position);
                //点击条目变颜色
                testAdapter.setOnItem(position);
                tvShow.setText(list.get(position));
                testAdapter.notifyDataSetChanged();
                Toast.makeText(RecycleViewActivity.this, " 点击 " + position, Toast.LENGTH_LONG).show();
            }
        });
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String time = "11:45:55";
        SimpleDateFormat YMDformat = new SimpleDateFormat("yyyy:mm:dd");
        String YMDtime = "2021:10:10";
        try {
            Date date = format.parse(time);
            Calendar calendar = dateToCalendar(date);
            Date ymddate = YMDformat.parse(YMDtime);
            Calendar ymdcalendar = dateToCalendar(ymddate);
            Calendar operationC = Calendar.getInstance();
            Log.e(TAG, "Format To times:" + date.getTime() + "  " + calendar.toString() + "\n"
                    + ymddate.getTime() + "  " + ymdcalendar.toString() + "\n"
                    + operationC.toString());
        } catch (ParseException parseException) {

        }
        /*Format To times:13555000  java.util.GregorianCalendar[time=13555000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=1970,MONTH=0,WEEK_OF_YEAR=1,WEEK_OF_MONTH=1,DAY_OF_MONTH=1,DAY_OF_YEAR=1,DAY_OF_WEEK=5,DAY_OF_WEEK_IN_MONTH=1,AM_PM=0,HOUR=11,HOUR_OF_DAY=11,MINUTE=45,SECOND=55,MILLISECOND=0,ZONE_OFFSET=28800000,DST_OFFSET=0]
        1610208600000  java.util.GregorianCalendar[time=1610208600000,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=0,WEEK_OF_YEAR=3,WEEK_OF_MONTH=3,DAY_OF_MONTH=10,DAY_OF_YEAR=10,DAY_OF_WEEK=1,DAY_OF_WEEK_IN_MONTH=2,AM_PM=0,HOUR=0,HOUR_OF_DAY=0,MINUTE=10,SECOND=0,MILLISECOND=0,ZONE_OFFSET=28800000,DST_OFFSET=0]
        java.util.GregorianCalendar[time=1639110378257,areFieldsSet=true,areAllFieldsSet=true,lenient=true,zone=libcore.util.ZoneInfo[id="Asia/Shanghai",mRawOffset=28800000,mEarliestRawOffset=28800000,mUseDst=false,mDstSavings=0,transitions=16],firstDayOfWeek=1,minimalDaysInFirstWeek=1,ERA=1,YEAR=2021,MONTH=11,WEEK_OF_YEAR=50,WEEK_OF_MONTH=2,DAY_OF_MONTH=10,DAY_OF_YEAR=344,DAY_OF_WEEK=6,DAY_OF_WEEK_IN_MONTH=2,AM_PM=1,HOUR=0,HOUR_OF_DAY=12,MINUTE=26,SECOND=18,MILLISECOND=257,ZONE_OFFSET=28800000,DST_OFFSET=0]*/
        Date currentTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(currentTime);
        calendar.add(calendar.DATE,1);
        String time1 = sdf.format(calendar.getTime());
        Log.e("======","time1 = " + time1);
    }

    public static Calendar dateToCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    private void initData() {
        //S型数据  条目个数必须是24 倍数
        for (int i = 0; i < 31; i++) {
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
                case 22:
                    list.add("下拉刷新");
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
        int aa = listsize%spanCount;
        int add = spanCount-aa;
        for (int i = 0; i < add; i++) {
            list.add(listsize-aa+i,"null");
        }
        Log.e("=======","list = " + new Gson().toJson(list));
    }

    private void intoItem(int position) {
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
            case "camera":
                intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
            case "滚动文字":
                intent = new Intent(this, ScrollTextActivity.class);
                startActivity(intent);
                break;
            case "推荐":
                intent = new Intent(this, StellarActivity.class);
                startActivity(intent);
                break;
            case "属性动画":
                intent = new Intent(this, DetailsActivity.class);
                startActivity(intent);
                break;
            case "流布局":
                intent = new Intent(this, FlowTestActivity.class);
                startActivity(intent);
                break;
            case "viewpager":
                intent = new Intent(this, FragmentActivity.class);
                startActivity(intent);
                break;
            case "kotlin":
                intent = new Intent(this, KotlinActivity.class);
                startActivity(intent);
                break;
            case "视频截屏":
                intent = new Intent(this, VideoScreenshotsActivity.class);
                startActivity(intent);
                break;
            case "图案解锁":
                intent = new Intent(this, ImageUnlockActivity.class);
                startActivity(intent);
                break;
            case "测试":
                intent = new Intent(this, TestActivity.class);
                startActivity(intent);
                break;
            case "图片移动":
                intent = new Intent(this, ImageMoveActivity.class);
                startActivity(intent);
                break;
            case "顶部移动":
                intent = new Intent(this, TopMoveActivity.class);
                startActivity(intent);
                break;
            case "evenbus使用":
                intent = new Intent(this, EvenbusActivity.class);
                startActivity(intent);
                break;
            case "底部切换动画":
                intent = new Intent(this, ButtomTapAnimActivity.class);
                startActivity(intent);
                break;
            case "串口读取":
                intent = new Intent(this, SerialPortActivity.class);
                startActivity(intent);
                break;
            case "下拉刷新":
                intent = new Intent(this, RefreshRecycleViewActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
