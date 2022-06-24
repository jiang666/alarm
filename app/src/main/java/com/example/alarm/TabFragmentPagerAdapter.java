package com.example.alarm;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.alarm.utils.UIUtils;

import java.util.List;

/**
 * Created by dx on 2018/9/18.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private FragmentManager mfragmentManager;
    private List<Fragment> mlist;


    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mlist = list;
        titles = UIUtils.getStrings(R.array.content_title);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mlist.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return mlist.size();//有几个页面
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}

