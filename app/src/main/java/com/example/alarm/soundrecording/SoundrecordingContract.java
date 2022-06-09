package com.example.alarm.soundrecording;


import android.content.Context;

import com.example.alarm.base.BaseModel;
import com.example.alarm.base.BasePresenter;
import com.example.alarm.base.BaseView;
import com.example.alarm.bean.UsbBean;

import io.reactivex.Flowable;


/**
 * fqc,hdmi测试联系类
 */

public interface SoundrecordingContract {
    //定义presenter
    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getUSBData(Context context);
    }

    interface Model extends BaseModel {
        Flowable<UsbBean> getUSBData(Context context);
    }

    interface View extends BaseView {
        void showUSBData(UsbBean usbBean);
    }

}
