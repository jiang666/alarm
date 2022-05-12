package com.example.alarm.viewpager;

import android.support.v4.app.Fragment;

/**
 * Created by bravian on 0017.
 */
public class FragmentFactory {
    /**
     * 创建Fragment
     * @param position
     * @return
     */
    public static Fragment createFragment(int position){

        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new DataqueryFragment();
                break;
            case 1:
                fragment=new SystemFragment();
                break;
            case 2:
                fragment=new WarningDataFragment();
                break;
        }

        return  fragment;
    }
}
