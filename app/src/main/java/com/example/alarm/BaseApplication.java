package com.example.alarm;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.alarm.utils.FloatWindowManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

import java.util.HashMap;

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
		//initTBS();
	}
	private boolean isDownTbsSuccess = false;//TBS X5插件是否下载成功
	private int downTbsCount = 0;//尝试下载次数
	/**
	 * 腾讯TBS初始化流程
	 */
	public void initTBS() {
		/********************************TBS服务器下发版本开始*********************************/
		if (QbSdk.getTbsVersion(getApplicationContext()) == 0) {//获取不到版本号，说明插件没有加载成功，重新跑流程
			//判断是否是x5内核未下载成功，存在缓存 重置化sdk，这样就清除缓存继续下载了
			QbSdk.reset(getApplicationContext());
			HashMap map = new HashMap();
			map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
			map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
			QbSdk.initTbsSettings(map);
			/* 设置允许移动网络下进行内核下载。默认不下载，会导致部分一直用移动网络的用户无法使用x5内核 */
			QbSdk.setDownloadWithoutWifi(true);

			/* SDK内核初始化周期回调，包括 下载、安装、加载 */
			QbSdk.setTbsListener(new TbsListener() {
				/**
				 * @param progress 110: 表示当前服务器认为该环境下不需要下载
				 */
				@Override
				public void onDownloadFinish(int progress) {
					Log.d("BaseApplication", "onDownloadFinished: " + progress);
					//下载结束时的状态，下载成功时errorCode为100,其他均为失败，外部不需要关注具体的失败原因
					if (isDownTbsSuccess) {
						return;
					}
					if (progress < 100) {
						return;
					}
					if (progress != 100) {//回调里面还是没有成功，那就再次尝试下载
						if (downTbsCount < 10) {//尝试下载10次，失败就开始从自己的服务器端下载
							downTbs();
						} else {//加载从自己的服务器端下载的x5内核
                            /*FileCopyAssetToSD.getInstance(getApplicationContext()).copyAssetsToSD("apk" , FileCopyAssetToSD.getDiskCacheDir(getApplicationContext()).toString()).setFileOperateCallback(new FileCopyAssetToSD.FileOperateCallback() {
                                @Override
                                public void onSuccess() {
                                    String filePath = FileCopyAssetToSD.getDiskCacheDir(getApplicationContext()).toString() + File.separator + "x5.apk";
                                    initLocalTbsCore(filePath);
                                    Toast.makeText(getApplicationContext() , "复制成功" , Toast.LENGTH_LONG).show();
                                    Log.i(TAG,"复制成功:"+filePath);

                                }

                                @Override
                                public void onFailed(String error) {

                                }
                            });*/
						}
						downTbsCount++;
					}
					if (progress == 100) {
						isDownTbsSuccess = true;
					}
				}

				/**initX5Environment
				 * @param stateCode 200、232安装成功
				 */
				@Override
				public void onInstallFinish(int stateCode) {
					Log.d("BaseApplication", "onInstallFinished: " + stateCode);
				}

				/**
				 * 首次安装应用，会触发内核下载，此时会有内核下载的进度回调。
				 * @param progress 0 - 100
				 */
				@Override
				public void onDownloadProgress(int progress) {
					Log.d("BaseApplication", "Core Downloading: " + progress);
				}
			});

			/* 此过程包括X5内核的下载、预初始化，接入方不需要接管处理x5的初始化流程，希望无感接入 */
			QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
				@Override
				public void onCoreInitFinished() {
					// 内核初始化完成，可能为系统内核，也可能为系统内核
				}

				/**
				 * 预初始化结束
				 * 由于X5内核体积较大，需要依赖wifi网络下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
				 * 内核下发请求发起有24小时间隔，卸载重装、调整系统时间24小时后都可重置
				 * @param isX5 是否使用X5内核
				 */
				@Override
				public void onViewInitFinished(boolean isX5) {
					Log.d("BaseApplication", "onViewInitFinished: " + isX5);
				}
			});
		}else {
			Toast.makeText(getApplicationContext() , "TbsVersion = " + QbSdk.getTbsVersion(getApplicationContext()) , Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 下载TBS插件 通过腾讯端下发
	 */
	public void downTbs() {
		//判断是否是x5内核未下载成功，存在缓存 重置化sdk，这样就清除缓存继续下载了
		QbSdk.reset(getApplicationContext());
		//开始下载x5内核
		TbsDownloader.startDownload(getApplicationContext());
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
