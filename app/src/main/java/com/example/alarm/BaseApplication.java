package com.example.alarm;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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
