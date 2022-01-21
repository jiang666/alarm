package com.example.alarm.viewpager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 系统配置
 */
public class SystemFragment extends Fragment{

    private static final int DEVICESINFO_FRAGMENT = 0;
    private static final int VIDEOINFO_FRAGMENT = 1;
    private static final int CONTROLINFO_FRAGMENT = 2;
    private static final int HISTORYINFO_FRAGMENT = 3;
    private static final int ALARMINFO_FRAGMENT = 4;
    private static final int SAFEINFO_FRAGMENT = 5;
    private static final int VIDEO_RECORD_FRAGMENT = 6;
    @BindView(R.id.tv_setdevicesinfo)
    TextView tvSetdevicesinfo;
    @BindView(R.id.tv_setvideoinfo)
    TextView tvSetvideoinfo;
    @BindView(R.id.tv_setcontrolinfo)
    TextView tvSetcontrolinfo;
    @BindView(R.id.tv_sethistory)
    TextView tvSethistory;
    @BindView(R.id.tv_setalarminfo)
    TextView tvSetalarminfo;
    @BindView(R.id.tv_setsafeinfo)
    TextView tvSetsafeinfo;
    @BindView(R.id.ll_switch)
    LinearLayout llSwitch;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    @BindView(R.id.tv_setvideorecord)
    TextView tvSetvideorecord;
    private View mRootView;
    private Unbinder unbinder;


    public SystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SystemFragment.
     */
    public static SystemFragment newInstance(String cardnum1) {
        SystemFragment fragment = new SystemFragment();
        Bundle args = new Bundle();
        args.putString("cardnum", cardnum1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.system_set_layout, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
