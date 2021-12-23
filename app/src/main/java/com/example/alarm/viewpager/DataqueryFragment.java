package com.example.alarm.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
 * 数据查询
 */
public class DataqueryFragment extends Fragment {
    private static final String TAG = DataqueryFragment.class.getSimpleName();
    @BindView(R.id.id_timetablecontent)
    FrameLayout idTimetablecontent;
    @BindView(R.id.tv_remotedata)
    TextView tvRemotedata;
    @BindView(R.id.tv_telemetrydata)
    TextView tvTelemetrydata;
    @BindView(R.id.tv_telemetryrealline)
    TextView tvTelemetryrealline;
    @BindView(R.id.tv_telemetryhistory)
    TextView tvTelemetryhistory;
    @BindView(R.id.ll_switch)
    LinearLayout llSwitch;
    @BindView(R.id.tv_pagetype)
    TextView tvPagetype;
    private View mRootView;
    private Unbinder unbinder;
    private static final int REMOTE_FRAGMENT = 0;
    private static final int TELEMETRY_FRAGMENT = 1;
    private static final int TELEMETRYREAL_FRAGMENT = 2;
    private static final int TELEMETRYHISTORY_FRAGMENT = 3;


    public DataqueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SystemFragment.
     */
    public static DataqueryFragment newInstance(String cardnum1) {
        DataqueryFragment fragment = new DataqueryFragment();
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
        mRootView = inflater.inflate(R.layout.remote_data_layout, container, false);
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
