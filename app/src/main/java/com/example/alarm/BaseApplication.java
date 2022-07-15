package com.example.alarm;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.alarm.utils.FloatWindowManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.sdk.QbSdk;

/**
 * 自定义Application
 * 
 * @author Kevin
 * 
 */
public class BaseApplication extends Application {

	private static Context context;
	private static int mainThreadId;
	private static Handler handler;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		mainThreadId = android.os.Process.myTid();// 获取当前主线程id
		handler = new Handler();
		FloatWindowManager.getInstance().requestPermission(this);
		FloatWindowManager.getInstance().initManager(this);
		Logger.clearLogAdapters();
		FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
				.tag("alarm")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
				.methodCount(6)
				.methodOffset(6)
				.build();

		Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
			@Override
			public boolean isLoggable(int priority, String tag) {
				return true;
			}
		});
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				Log.e("app", " onViewInitFinished is " + arg0);


			}

			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
				Log.e("app", " onCoreInitFinished ");
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(), cb);
		Log.e("app", " init ");
	}

	public static Context getContext() {
		return context;
	}

	public static int getMainThreadId() {
		return mainThreadId;
	}

	public static Handler getHandler() {
		return handler;
	}
}
