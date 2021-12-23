package com.example.alarm.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alarm.R;

import java.util.Calendar;
import java.util.Collections;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 警告数据
 */
public class WarningDataFragment extends Fragment {
    private static final String TAG = WarningDataFragment.class.getSimpleName();
    @BindView(R.id.rv_personal_day)
    RecyclerView rvPersonalDay;
    @BindView(R.id.sp_datatype)
    Spinner spDatatype;
    @BindView(R.id.tv_starttime)
    TextView tvStarttime;
    @BindView(R.id.tv_endtime)
    TextView tvEndtime;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    private View mRootView;
    private Unbinder unbinder;

    public WarningDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SystemFragment.
     */
    public static WarningDataFragment newInstance(String cardnum1) {
        WarningDataFragment fragment = new WarningDataFragment();
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
        mRootView = inflater.inflate(R.layout.warningdata_layout, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
