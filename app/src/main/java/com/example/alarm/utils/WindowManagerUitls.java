package com.example.alarm.utils;
import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

import com.example.alarm.R;
import com.example.alarm.widget.SourceWindmanger;
import com.example.alarm.widget.RightIconWindowView;
import com.example.alarm.widget.RightOneWindmanger;

/* loaded from: classes2.dex */
public class WindowManagerUitls {
    public static final String ACTION_BOTTOM_MENU_HIDE = "com.kjd.action.BOTTOM_MENU_HIDE";
    public static final String ACTION_BOTTOM_MENU_SHOW = "com.kjd.action.BOTTOM_MENU_SHOW";
    public static boolean isMenuShow = false;
    public static boolean isSourceShow = false;
    //private static BottomWindowManager mBottomWindmanger;
    private static WindowManager.LayoutParams mBottomWindmangerParams;
    private static WindowManager.LayoutParams mLeftIconWindowParams;
    //private static LeftIconWindmanger mLeftIconWindowView;
    private static WindowManager.LayoutParams mLeftOneWindowParams;
    //private static LeftOneWindmanger mLeftOneWindowView;
    //private static MenusWindowView mMenusWindowView;
    private static WindowManager.LayoutParams mMenusWindowViewParams;
    private static WindowManager.LayoutParams mRightIconWindowParams;
    private static RightIconWindowView mRightIconWindowView;
    private static WindowManager.LayoutParams mRightOneWindowParams;
    private static RightOneWindmanger mRightOneWindowView;
    //private static SeekBarWindmanger mSeekBarWindmanger;
    private static WindowManager.LayoutParams mSeekBarWindowParams;
    private static SourceWindmanger mSourceWindmanger;
    private static WindowManager.LayoutParams mSourceWindmangerParams;
    //private static TimerWindmanger mTimerWindmanger;
    private static WindowManager.LayoutParams mTimerWindmangerParams;



    public static void createRightIconWindowView(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        if (mRightIconWindowView == null) {
            mRightIconWindowView = new RightIconWindowView(context);
            if (mRightIconWindowParams == null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                mRightIconWindowParams = layoutParams;
                //layoutParams.type = 2003;
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                mRightIconWindowParams.format = 1;
                mRightIconWindowParams.flags = 8651304;
                mRightIconWindowParams.gravity = 53;
                mRightIconWindowParams.width = RightIconWindowView.viewWidth;
                mRightIconWindowParams.height = RightIconWindowView.viewHeight;
                mRightIconWindowParams.x = 0;
                int i = (RightIconWindowView.viewHeight - RightOneWindmanger.viewHeight) / 2;
                mRightIconWindowParams.y = RightOneWindmanger.paramY - i;
                if (mRightIconWindowParams.y + RightIconWindowView.viewHeight > height) {
                    mRightIconWindowParams.y = height - RightIconWindowView.viewHeight;
                }
                if (mRightIconWindowParams.y < 0) {
                    mRightIconWindowParams.y = 0;
                }
                RightOneWindmanger.paramY = mRightIconWindowParams.y + i;
                mRightIconWindowParams.windowAnimations = R.style.AnimationFade;
            }
            mRightIconWindowView.setParams(mRightIconWindowParams);
            windowManager.addView(mRightIconWindowView, mRightIconWindowParams);
        }
    }

    public static void removeRightIconWindowView(Context context) {
        if (mRightIconWindowView != null) {
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).removeView(mRightIconWindowView);
            mRightIconWindowView = null;
        }
        if (mRightIconWindowParams != null) {
            mRightIconWindowParams = null;
        }
    }

    public static void reviewIconWindowView(Context context) {
        Log.d("draw", "reviewIconWindowView ");
        if (mRightOneWindowView != null) {
            Log.d("draw", "reviewIconWindowView ==" + mRightOneWindowView.getAlpha());
            if (mRightOneWindowView.getAlpha() != 0.98f) {
                mRightOneWindowView.setAlpha(0.98f);
            } else {
                mRightOneWindowView.setAlpha(1.0f);
            }
        }
    }




    public static void createRightOneWindowView(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getWidth();
        windowManager.getDefaultDisplay().getHeight();
        if (mRightOneWindowView == null) {
            mRightOneWindowView = new RightOneWindmanger(context);
            if (mRightOneWindowParams == null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                mRightOneWindowParams = layoutParams;
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                //layoutParams.type = 2003;
                mRightOneWindowParams.format = 1;
                mRightOneWindowParams.flags = 8651304;
                mRightOneWindowParams.gravity = 53;
                mRightOneWindowParams.width = RightOneWindmanger.viewWidth;
                mRightOneWindowParams.height = RightOneWindmanger.viewHeight;
                mRightOneWindowParams.x = 0;
                mRightOneWindowParams.y = RightOneWindmanger.paramY;
            }
            mRightOneWindowView.setParams(mRightOneWindowParams);
            windowManager.addView(mRightOneWindowView, mRightOneWindowParams);
        }
    }

    public static void removeRightOneWindowView(Context context) {
        if (mRightOneWindowView != null) {
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).removeView(mRightOneWindowView);
            mRightOneWindowView = null;
        }
        if (mRightOneWindowParams != null) {
            mRightOneWindowParams = null;
        }
    }

    public static void createSourceWindmanger(Context context, int i, int i2, int i3, boolean z) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getWidth();
        windowManager.getDefaultDisplay().getHeight();
        if (mSourceWindmanger == null) {
            mSourceWindmanger = new SourceWindmanger(context);
            if (mSourceWindmangerParams == null) {
                isSourceShow = true;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                mSourceWindmangerParams = layoutParams;
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                //layoutParams.type = 2003;
                mSourceWindmangerParams.format = 1;
                mSourceWindmangerParams.flags = 8782368;
                if (z) {
                    mSourceWindmangerParams.gravity = 19;
                } else {
                    mSourceWindmangerParams.gravity = 21;
                }
                mSourceWindmangerParams.width = SourceWindmanger.viewWidth;
                mSourceWindmangerParams.height = SourceWindmanger.viewHeight;
                mSourceWindmangerParams.windowAnimations = R.style.AnimationFade;
                mSourceWindmangerParams.x = i;
                mSourceWindmangerParams.y = 0;
            }
            mSourceWindmanger.setParams(mSourceWindmangerParams);
            windowManager.addView(mSourceWindmanger, mSourceWindmangerParams);
        }
    }

    public static void createBottomSourceWindmanger(Context context, int i, int i2) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getWidth();
        windowManager.getDefaultDisplay().getHeight();
        if (mSourceWindmanger == null) {
            mSourceWindmanger = new SourceWindmanger(context);
            if (mSourceWindmangerParams == null) {
                isSourceShow = true;
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                mSourceWindmangerParams = layoutParams;
                layoutParams.type = 2003;
                mSourceWindmangerParams.format = 1;
                mSourceWindmangerParams.flags = 8782368;
                mSourceWindmangerParams.gravity = 81;
                mSourceWindmangerParams.width = SourceWindmanger.viewWidth;
                mSourceWindmangerParams.height = SourceWindmanger.viewHeight;
                mSourceWindmangerParams.windowAnimations = R.style.AnimationFade;
                mSourceWindmangerParams.x = 0;
                mSourceWindmangerParams.y = i2;
            }
            mSourceWindmanger.setParams(mSourceWindmangerParams);
            windowManager.addView(mSourceWindmanger, mSourceWindmangerParams);
        }
    }

    public static void removeSourceWindmanger(Context context) {
        if (mSourceWindmanger != null) {
            isSourceShow = false;
            ((WindowManager) context.getSystemService(Service.WINDOW_SERVICE)).removeView(mSourceWindmanger);
            mSourceWindmanger = null;
        }
        if (mSourceWindmangerParams != null) {
            mSourceWindmangerParams = null;
        }
    }




}