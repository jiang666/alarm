package com.example.alarm.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.alarm.BaseApplication;


/**
 * 工具类, 专门处理UI相关的逻辑
 * 
 * @author Kevin
 * 
 */
public class UIUtils {

	public static Context getContext() {
		return BaseApplication.getContext();
	}

	public static int getMainThreadId() {
		return BaseApplication.getMainThreadId();
	}

	public static Handler getHandler() {
		return BaseApplication.getHandler();
	}
	/**
	 * 得到Resource对象
	 */
	public static Resources getResources() {
		return getContext().getResources();
	}

	/**
	 * 得到String.xml中的字符串信息
	 */
	public static String getString(int resId) {
		return getResources().getString(resId);
	}


	/**
	 * 得到String.xml中的字符串数组信息
	 */
	public static String[] getStrings(int resId) {
		return getResources().getStringArray(resId);
	}

	/**
	 * 得到Color.xml中的颜色信息
	 */
	public static int getColor(int resId) {
		return getResources().getColor(resId);
	}
	/**
	 * 获取颜色状态集合
	 */
	public static ColorStateList getColorStateList(int id) {
		return getContext().getResources().getColorStateList(id);
	}

	/**
	 * 根据id获取尺寸
	 */
	public static int getDimen(int id) {
		return getContext().getResources().getDimensionPixelSize(id);
	}

	/**
	 * 根据id获取字符串数组
	 */
	public static String[] getStringArray(int id) {
		return getContext().getResources().getStringArray(id);
	}

	/**
	 * dp转px
	 */
	public static int dip2px(float dp) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return (int) (density * dp + 0.5);
	}

	/**
	 * px转dp
	 */
	public static float px2dip(float px) {
		float density = getContext().getResources().getDisplayMetrics().density;
		return px / density;
	}

	/**
	 * 加载布局文件
	 */
	public static View inflate(int layoutId) {
		return View.inflate(getContext(), layoutId, null);
	}

	/**
	 * 判断当前是否运行在主线程
	 * 
	 * @return
	 */
	public static boolean isRunOnUiThread() {
		return getMainThreadId() == android.os.Process.myTid();
	}

	/**
	 * 保证当前的操作运行在UI主线程
	 * 
	 * @param runnable
	 */
	public static void runOnUiThread(Runnable runnable) {
		if (isRunOnUiThread()) {
			runnable.run();
		} else {
			getHandler().post(runnable);
		}
	}

	/**
	 * 得到应用程序包名
	 *
	 * @return
	 */
	public static String getPackageName() {
		return getContext().getPackageName();
	}

	/**
	 * dip-->px
	 *
	 * @param dip
	 * @return
	 */
	public static int dip2Px(int dip) {
        /*
        1.  px/(ppi/160) = dp
        2.  px/dp = density
         */

		//取得当前手机px和dp的倍数关系
		float density = getResources().getDisplayMetrics().density;
        //Log.e("=============","density = " + density );
		int px = (int) (dip * density + .5f);
		return px;
	}

	public static int px2Dip(int px) {
		// 2.  px/dp = density

		//取得当前手机px和dp的倍数关系
		float density = getResources().getDisplayMetrics().density;

		int dip = (int) (px / density + .5f);
		return dip;
	}
}
