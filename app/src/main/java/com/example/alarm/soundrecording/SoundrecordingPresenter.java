package com.example.alarm.soundrecording;


import android.content.Context;

import com.example.alarm.bean.UsbBean;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

/**
 * 录音测试presenter
 */

public class SoundrecordingPresenter extends SoundrecordingContract.Presenter {
    @Override
    public void getUSBData(Context context) {
        mModel.getUSBData(context).subscribe(new FlowableSubscriber<UsbBean>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(UsbBean singleTestBean) {
                mView.showUSBData(singleTestBean);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
