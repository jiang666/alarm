package com.example.alarm.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.alarm.R;
import com.example.alarm.bean.UsbBean;
import com.example.alarm.utils.TypeUtil;


public abstract class BaseFragment<M extends BaseModel, P extends BasePresenter> extends Fragment {

    public M mModel;

    public P mPresenter;

    protected View layout;
    public NextTestingListener mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseView = inflater.inflate(R.layout.base_fragmet, container, false);
        FrameLayout child = (FrameLayout) baseView.findViewById(R.id.child_frame);
        if (layout == null) {
            layout = inflater.inflate(getLayoutId(), container, false);
        }
        if (layout != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            child.addView(layout, params);
        }
        //初始化presenter
        mModel = TypeUtil.getObject(this, 0);
        mPresenter = TypeUtil.getObject(this, 1);
        initPresenter();
        //初始化View
        initView();
        return baseView;
    }


    protected abstract int getLayoutId();

    protected abstract void initPresenter();

    protected abstract void initView();

    public interface NextTestingListener {
        void nextTesting(UsbBean usbBean);

        void lastTesting(UsbBean usbBean);
    }

    public void setNextTestingListener(NextTestingListener nextTestingListener) {
        this.mNext = nextTestingListener;
    }
}
