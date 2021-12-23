package com.example.alarm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alarm.viewpager.DataqueryFragment;
import com.example.alarm.viewpager.SystemFragment;
import com.example.alarm.viewpager.WarningDataFragment;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * FragmentActivity
 */
public class FragmentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REALDATA_FRAGMENT = 4;
    private static final int DATA_FRAGMENT = 1;
    private static final int VIDEO_FRAGMENT = 3;
    private static final int ALARM_FRAGMENT = 2;
    private static final int SYSTEM_FRAGMENT = 0;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.tv_activation)
    TextView tvActivation;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_real_time_data)
    TextView tvRealTimeData;
    @BindView(R.id.view_one)
    View viewOne;
    @BindView(R.id.tv_data)
    TextView tvData;
    @BindView(R.id.view_two)
    View viewTwo;
    @BindView(R.id.tv_alarm)
    TextView tvAlarm;
    @BindView(R.id.view_three)
    View viewThree;
    @BindView(R.id.tv_vodeo)
    TextView tvVodeo;
    @BindView(R.id.view_four)
    View viewFour;
    @BindView(R.id.tv_system)
    TextView tvSystem;
    @BindView(R.id.ll_tip)
    PercentLinearLayout llTip;
    @BindView(R.id.fl_fragment)
    ViewPager viewPager;
    @BindView(R.id.tv_alarm1_typename)
    TextView tvAlarm1Typename;
    @BindView(R.id.tv_alarm1_time)
    TextView tvAlarm1Time;
    @BindView(R.id.tv_alarm1_index)
    TextView tvAlarm1Index;
    @BindView(R.id.tv_alarm2_typename)
    TextView tvAlarm2Typename;
    @BindView(R.id.tv_alarm2_time)
    TextView tvAlarm2Time;
    @BindView(R.id.tv_alarm2_index)
    TextView tvAlarm2Index;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private List<Fragment> mlist;
    private TabFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ButterKnife.bind(this);
        tvSystem.setOnClickListener(this);
        tvAlarm.setOnClickListener(this);
        tvData.setOnClickListener(this);
        //绑定点击事件
        viewPager.setOnPageChangeListener(new MyPagerChangeListener()) ;
        //把Fragment添加到List集合里面
        mlist = new ArrayList<>();
        mlist.add(new DataqueryFragment() );
        mlist.add(new WarningDataFragment() );
        mlist.add(new SystemFragment() );
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), mlist);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_vodeo:
                //startActivity(new Intent(getApplicationContext(), VideoMonitoringActivity.class));
                //openFragment(VIDEO_FRAGMENT);
                viewPager.setCurrentItem(VIDEO_FRAGMENT);
                break;
            case R.id.tv_system:
                viewPager.setCurrentItem(SYSTEM_FRAGMENT);
                break;
            case R.id.tv_data:
                viewPager.setCurrentItem(DATA_FRAGMENT);
                break;
            case R.id.tv_alarm:
                /*try {
                    Thread.sleep(1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                viewPager.setCurrentItem(ALARM_FRAGMENT);
                break;
            case R.id.tv_real_time_data:
                viewPager.setCurrentItem(REALDATA_FRAGMENT);
                break;
        }
    }
    /**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *
     */
    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    openFragment(0);
                    break;
                case 1:
                    openFragment(1);
                    break;
                case 2:
                    openFragment(2);
                    break;
                case 3:
                    openFragment(3);
                    break;
                case 4:
                    openFragment(4);
                    break;
            }

        }

    }
    private void openFragment(int page) {
        //FragmentManager FMs = getSupportFragmentManager();
        //FragmentTransaction MfragmentTransactions = FMs.beginTransaction();
        tvData.setTextColor(getResources().getColor(R.color.black));
        tvAlarm.setTextColor(getResources().getColor(R.color.black));
        tvRealTimeData.setTextColor(getResources().getColor(R.color.black));
        tvSystem.setTextColor(getResources().getColor(R.color.black));
        tvVodeo.setTextColor(getResources().getColor(R.color.black));
        viewOne.setBackgroundColor(getResources().getColor(R.color.black));
        viewTwo.setBackgroundColor(getResources().getColor(R.color.black));
        viewThree.setBackgroundColor(getResources().getColor(R.color.black));
        viewFour.setBackgroundColor(getResources().getColor(R.color.black));
        switch (page) {
            case REALDATA_FRAGMENT:
                viewOne.setBackgroundColor(getResources().getColor(R.color.white));
                tvRealTimeData.setTextColor(getResources().getColor(R.color.white));
                //RealDataFragment messageFragment = RealDataFragment.newInstance("");
                //MfragmentTransactions.replace(R.id.fl_fragment, messageFragment);
                break;
            case SYSTEM_FRAGMENT:
                tvSystem.setTextColor(getResources().getColor(R.color.white));
                //SystemFragment systemFragment = SystemFragment.newInstance("");
                //MfragmentTransactions.replace(R.id.fl_fragment, systemFragment);
                break;
            case DATA_FRAGMENT:
                viewTwo.setBackgroundColor(getResources().getColor(R.color.white));
                //DataqueryFragment dataqueryFragment = DataqueryFragment.newInstance("");
                //MfragmentTransactions.replace(R.id.fl_fragment, dataqueryFragment);
                tvData.setTextColor(getResources().getColor(R.color.white));
                break;
            case ALARM_FRAGMENT:
                viewThree.setBackgroundColor(getResources().getColor(R.color.white));
                //WarningDataFragment warningDataFragment = WarningDataFragment.newInstance("");
                //MfragmentTransactions.replace(R.id.fl_fragment, warningDataFragment);
                tvAlarm.setTextColor(getResources().getColor(R.color.white));
                break;
            case VIDEO_FRAGMENT:
                viewFour.setBackgroundColor(getResources().getColor(R.color.white));
                //VideoRealFragment videoRealFragment = VideoRealFragment.newInstance("");
                //MfragmentTransactions.replace(R.id.fl_fragment, videoRealFragment);
                tvVodeo.setTextColor(getResources().getColor(R.color.white));
                break;
        }
        //MfragmentTransactions.commit();
    }
}
