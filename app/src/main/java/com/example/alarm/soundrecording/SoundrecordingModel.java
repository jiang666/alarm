package com.example.alarm.soundrecording;

import android.content.Context;

import com.example.alarm.bean.UsbBean;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * fqc,hdmi测试 model类
 */

public class SoundrecordingModel implements SoundrecordingContract.Model {
    private static final String TAG = SoundrecordingModel.class.getSimpleName();

    @Override
    public Flowable<UsbBean> getUSBData(final Context context) {
        Flowable<UsbBean> flowAble = new Flowable<UsbBean>() {
            @Override
            protected void subscribeActual(Subscriber<? super UsbBean> s) {
               /* UsbBean usbBean = SpUtil.getObject(context, UsbBean.class);
                s.onNext(usbBean);*/
            }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return flowAble;
    }
}
