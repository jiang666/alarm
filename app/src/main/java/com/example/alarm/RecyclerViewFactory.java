package com.example.alarm;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.alarm.utils.UIUtils;

/**
 * Created by itheima.
 * 创建RecyclerView工厂
 */
public class RecyclerViewFactory {

    public static RecyclerView createVertical(){
        RecyclerView recyclerView = new RecyclerView(UIUtils.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(UIUtils.getContext(), LinearLayoutManager.VERTICAL, false));
        return recyclerView;
    }
}
