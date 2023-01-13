package com.example.alarm.widget;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
//import android.os.SystemProperties;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarm.R;
import com.example.alarm.utils.CompNameIntent;
import com.example.alarm.utils.WindowManagerUitls;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public class SourceWindmanger extends LinearLayout {
    private static final int DELAY_TIME = 5000;
    private static final int HIDE_SOURCE_WINDOW = 0;
    private static final int KEYQUEUE_SIZE = 4;
    private static int statusBarHeight;
    public static int viewHeight;
    public static int viewWidth;
    private ImageView android_img;
    private LinearLayout android_ll;
    private ImageView atv_img;
    private LinearLayout atv_ll;
    private ImageView av_img;
    private LinearLayout av_ll;
    private ImageView dtv_img;
    private LinearLayout dtv_ll;
    private ImageView fhdmi_img;
    private LinearLayout fhdmi_ll;
    private Handler handler;
    private ImageView hdmi1_img;
    private LinearLayout hdmi1_ll;
    private ImageView hdmi2_img;
    private LinearLayout hdmi2_ll;
    private int iconImageHeight;
    private int iconImageWidth;
    private boolean isclick;
    private CompNameIntent mCompNameIntent;
    private Context mContext;
    private ArrayList<Integer> mKeyQueue;
    private View.OnClickListener mOnClickListener;
    private WindowManager.LayoutParams mParams;
    private LinearLayout menu_source_linear;
    private ImageView ops_img;
    private LinearLayout ops_ll;
    private int selectSource;
    private TextView txt_android;
    private TextView txt_atv;
    private TextView txt_av;
    private TextView txt_dtv;
    private TextView txt_fhdmi;
    private TextView txt_hdmi1;
    private TextView txt_hdmi2;
    private TextView txt_ops;
    private TextView txt_typec;
    private TextView txt_vga;
    private TextView txt_ypbpr;
    private ImageView type_img;
    private LinearLayout type_ll;
    private float uxInView;
    private float uyInView;
    private ImageView vga_img;
    private LinearLayout vga_ll;
    private View view;
    private WindowManager windowManager;
    private float xDownInScreen;
    private float xInScreen;
    private float xInView;
    private float yDownInScreen;
    private float yInScreen;
    private float yInView;
    private ImageView ypbpr_img;
    private LinearLayout ypbpr_ll;
    private static final String MENUKEYCODES = String.valueOf(8) + String.valueOf(11) + String.valueOf(14) + String.valueOf(7);
    private static final String FACTORYKEYCODES = String.valueOf(9) + String.valueOf(12) + String.valueOf(15) + String.valueOf(7);
    private static final String SETTINGKEYCODES = String.valueOf(10) + String.valueOf(13) + String.valueOf(16) + String.valueOf(7);
    private static final String ANDROIDSETTINGKEYCODES = String.valueOf(8) + String.valueOf(10) + String.valueOf(12) + String.valueOf(14);
    private static final String ENGMENUKEYCODES = String.valueOf(9) + String.valueOf(11) + String.valueOf(13) + String.valueOf(15);
    private static final String SETPANEL2K = String.valueOf(8) + String.valueOf(10) + String.valueOf(8) + String.valueOf(9);
    private static final String SETPANEL4K = String.valueOf(8) + String.valueOf(10) + String.valueOf(8) + String.valueOf(11);

    public SourceWindmanger(Context context) {
        super(context);
        this.selectSource = 25;
        this.mOnClickListener = new View.OnClickListener() { // from class: com.src.ui.kjdsidepull.View.SourceWindmanger.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.android_ll /* 2131361871 */:
                        SourceWindmanger.this.mCompNameIntent.JumpHome();
                        break;
                    /*case R.id.atv_ll *//* 2131361875 *//*:
                        Utils.startTvPlyaer(0);
                        break;
                    case R.id.av_ll *//* 2131361877 *//*:
                        Utils.startTvPlyaer(2);
                        break;
                    case R.id.dtv_ll *//* 2131361955 *//*:
                        Utils.startTvPlyaer(1);
                        break;
                    case R.id.fhdmi_ll *//* 2131361967 *//*:
                        Utils.startTvPlyaer(7);
                        break;
                    case R.id.hdmi1_ll *//* 2131362001 *//*:
                        Utils.startTvPlyaer(5);
                        break;
                    case R.id.hdmi2_ll *//* 2131362003 *//*:
                        Utils.startTvPlyaer(6);
                        break;
                    case R.id.ops_ll *//* 2131362109 *//*:
                        Utils.startTvPlyaer(8);
                        break;
                    case R.id.type_ll *//* 2131362231 *//*:
                        Utils.startTvPlyaer(9);
                        break;
                    case R.id.vga_ll *//* 2131362237 *//*:
                        Utils.startTvPlyaer(4);
                        break;
                    case R.id.ypbpr_ll *//* 2131362247 *//*:
                        Utils.startTvPlyaer(3);
                        break;*/
                }
                SourceWindmanger.this.finish();
            }
        };
        this.mKeyQueue = new ArrayList<>();
        this.handler = new Handler() { // from class: com.src.ui.kjdsidepull.View.SourceWindmanger.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what == 0) {
                    int unused = SourceWindmanger.this.selectSource;
                    SourceWindmanger.this.finish();
                }
            }
        };
        this.mContext = context;
        this.windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.men_source, this);
        init();
    }

    public void init() {
        Log.i("zhh", "SourceWindmanger init");
        View findViewById = findViewById(R.id.menu_source_linear);
        this.view = findViewById;
        viewWidth = findViewById.getLayoutParams().width;
        viewHeight = this.view.getLayoutParams().height;
        this.menu_source_linear = (LinearLayout) findViewById(R.id.menu_source_linear);
        this.mCompNameIntent = new CompNameIntent(this.mContext);
        this.android_ll = (LinearLayout) findViewById(R.id.android_ll);
        this.hdmi1_ll = (LinearLayout) findViewById(R.id.hdmi1_ll);
        this.hdmi2_ll = (LinearLayout) findViewById(R.id.hdmi2_ll);
        this.ops_ll = (LinearLayout) findViewById(R.id.ops_ll);
        this.fhdmi_ll = (LinearLayout) findViewById(R.id.fhdmi_ll);
        this.type_ll = (LinearLayout) findViewById(R.id.type_ll);
        this.vga_ll = (LinearLayout) findViewById(R.id.vga_ll);
        this.av_ll = (LinearLayout) findViewById(R.id.av_ll);
        this.ypbpr_ll = (LinearLayout) findViewById(R.id.ypbpr_ll);
        this.atv_ll = (LinearLayout) findViewById(R.id.atv_ll);
        this.dtv_ll = (LinearLayout) findViewById(R.id.dtv_ll);
        this.hdmi1_img = (ImageView) findViewById(R.id.hdmi1_img);
        this.hdmi2_img = (ImageView) findViewById(R.id.hdmi2_img);
        this.ops_img = (ImageView) findViewById(R.id.ops_img);
        this.fhdmi_img = (ImageView) findViewById(R.id.fhdmi_img);
        this.vga_img = (ImageView) findViewById(R.id.vga_img);
        this.av_img = (ImageView) findViewById(R.id.av_img);
        this.ypbpr_img = (ImageView) findViewById(R.id.ypbpr_img);
        this.atv_img = (ImageView) findViewById(R.id.atv_img);
        this.dtv_img = (ImageView) findViewById(R.id.dtv_img);
        this.type_img = (ImageView) findViewById(R.id.type_img);
        this.txt_android = (TextView) findViewById(R.id.txt_android);
        this.txt_hdmi1 = (TextView) findViewById(R.id.txt_hdmi1);
        this.txt_hdmi2 = (TextView) findViewById(R.id.txt_hdmi2);
        this.txt_fhdmi = (TextView) findViewById(R.id.txt_fhdmi);
        this.txt_ops = (TextView) findViewById(R.id.txt_ops);
        this.txt_av = (TextView) findViewById(R.id.txt_av);
        this.txt_ypbpr = (TextView) findViewById(R.id.txt_ypbpr);
        this.txt_vga = (TextView) findViewById(R.id.txt_vga);
        this.txt_atv = (TextView) findViewById(R.id.txt_atv);
        this.txt_dtv = (TextView) findViewById(R.id.txt_dtv);
        this.txt_typec = (TextView) findViewById(R.id.txt_typec);
        /*this.txt_android.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(11));
        this.txt_hdmi1.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(5));
        this.txt_hdmi2.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(6));
        this.txt_fhdmi.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(7));
        this.txt_ops.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(8));
        this.txt_av.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(2));
        this.txt_ypbpr.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(3));
        this.txt_vga.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(4));
        this.txt_atv.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(0));
        this.txt_dtv.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(1));
        this.txt_typec.setText(TvSourceManager.getInstance().getDBUtils(this.mContext).getInputSourceName(9));*/
        this.android_ll.setOnClickListener(this.mOnClickListener);
        this.hdmi1_ll.setOnClickListener(this.mOnClickListener);
        this.hdmi2_ll.setOnClickListener(this.mOnClickListener);
        this.type_ll.setOnClickListener(this.mOnClickListener);
        this.ops_ll.setOnClickListener(this.mOnClickListener);
        this.fhdmi_ll.setOnClickListener(this.mOnClickListener);
        this.vga_ll.setOnClickListener(this.mOnClickListener);
        this.av_ll.setOnClickListener(this.mOnClickListener);
        this.ypbpr_ll.setOnClickListener(this.mOnClickListener);
        this.atv_ll.setOnClickListener(this.mOnClickListener);
        this.dtv_ll.setOnClickListener(this.mOnClickListener);
/*        if (Utils.isSignalStable(5)) {
            this.hdmi1_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.hdmi1_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(6)) {
            this.hdmi2_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.hdmi2_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(7)) {
            this.fhdmi_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.fhdmi_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(8)) {
            this.ops_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.ops_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(9)) {
            this.type_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.type_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(4)) {
            this.vga_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.vga_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(2)) {
            this.av_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.av_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(3)) {
            this.ypbpr_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.ypbpr_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(0)) {
            this.atv_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.atv_img.setBackgroundResource(R.mipmap.disconnect);
        }
        if (Utils.isSignalStable(1)) {
            this.dtv_img.setBackgroundResource(R.mipmap.connected);
        } else {
            this.dtv_img.setBackgroundResource(R.mipmap.disconnect);
        }
        int curInputSource = Utils.getCurInputSource();
        if (curInputSource == 11) {
            this.android_ll.setFocusable(true);
            this.android_ll.requestFocus();
        } else if (curInputSource == 5) {
            this.hdmi1_ll.setFocusable(true);
            this.hdmi1_ll.requestFocus();
        } else if (curInputSource == 6) {
            this.hdmi2_ll.setFocusable(true);
            this.hdmi2_ll.requestFocus();
        } else if (curInputSource == 7) {
            this.fhdmi_ll.setFocusable(true);
            this.fhdmi_ll.requestFocus();
        } else if (curInputSource == 9) {
            this.type_ll.setFocusable(true);
            this.type_ll.requestFocus();
        } else if (curInputSource == 8) {
            this.ops_ll.setFocusable(true);
            this.ops_ll.requestFocus();
        } else if (curInputSource == 4) {
            this.vga_ll.setFocusable(true);
            this.vga_ll.requestFocus();
        } else if (curInputSource == 2) {
            this.av_ll.setFocusable(true);
            this.av_ll.requestFocus();
        } else if (curInputSource == 3) {
            this.ypbpr_ll.setFocusable(true);
            this.ypbpr_ll.requestFocus();
        } else if (curInputSource == 0) {
            this.atv_ll.setFocusable(true);
            this.atv_ll.requestFocus();
        } else if (curInputSource == 1) {
            this.dtv_ll.setFocusable(true);
            this.dtv_ll.requestFocus();
        }
        if (SystemProperties.get("persist.sys.tv.hide", "false").equals("true")) {
            this.atv_ll.setVisibility(8);
            this.dtv_ll.setVisibility(8);
        }
        if (SystemProperties.get("persist.sys.ops.hide", "false").equals("true")) {
            this.ops_ll.setVisibility(8);
        }
        if (SystemProperties.get("persist.sys.fhdmi.hide", "false").equals("true")) {
            this.fhdmi_ll.setVisibility(8);
        }
        if (SystemProperties.get("persist.sys.av.hide", "false").equals("true")) {
            this.av_ll.setVisibility(8);
        }
        if (SystemProperties.get("persist.sys.ypbpr.hide", "true").equals("true")) {
            this.ypbpr_ll.setVisibility(8);
        }
        if (SystemProperties.get("persist.sys.typec.hide", "false").equals("true")) {
            this.type_ll.setVisibility(8);
        }
        if (SystemProperties.get("persist.sys.VGA.hide", "false").equals("true")) {
            this.vga_ll.setVisibility(8);
        }
        if (this.handler.hasMessages(0)) {
            this.handler.removeMessages(0);
        }*/
        this.handler.sendEmptyMessageDelayed(0, 5000L);
        this.mContext.sendBroadcast(new Intent(Constant.ACTION_TOUCH_TO_ANDROID));
    }

    public int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> cls = Class.forName("com.android.internal.R$dimen");
                statusBarHeight = getResources().getDimensionPixelSize(((Integer) cls.getField("status_bar_height").get(cls.newInstance())).intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    void finish() {
        if (this.handler.hasMessages(0)) {
            this.handler.removeMessages(0);
        }
        WindowManagerUitls.removeSourceWindmanger(this.mContext);
        this.mContext.sendBroadcast(new Intent(Constant.ACTION_TOUCH_TO_PC));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 4) {
            finish();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setParams(WindowManager.LayoutParams layoutParams) {
        this.mParams = layoutParams;
        this.iconImageWidth = this.menu_source_linear.getLayoutParams().width;
        this.iconImageHeight = this.menu_source_linear.getLayoutParams().height;
    }

    private String intArrayListToString(ArrayList<Integer> arrayList) {
        String str = "";
        for (int i = 0; i < arrayList.size(); i++) {
            str = str + arrayList.get(i).toString();
        }
        return str;
    }

    /* loaded from: classes2.dex */
    public class onSourceFocusChangeListener implements View.OnFocusChangeListener {
        public onSourceFocusChangeListener() {
        }

        @Override // android.view.View.OnFocusChangeListener
        public void onFocusChange(View view, boolean z) {
            if (z) {
                if (view.getId() == R.id.hdmi1_ll) {
                    SourceWindmanger.this.selectSource = 11;
                } else if (view.getId() == R.id.hdmi2_ll) {
                    SourceWindmanger.this.selectSource = 12;
                } else if (view.getId() == R.id.fhdmi_ll) {
                    SourceWindmanger.this.selectSource = 13;
                } else if (view.getId() == R.id.ops_ll) {
                    SourceWindmanger.this.selectSource = 14;
                } else if (view.getId() == R.id.type_ll) {
                    SourceWindmanger.this.selectSource = 15;
                } else if (view.getId() == R.id.vga_ll) {
                    SourceWindmanger.this.selectSource = 10;
                } else if (view.getId() == R.id.av_ll) {
                    SourceWindmanger.this.selectSource = 8;
                } else if (view.getId() == R.id.ypbpr_ll) {
                    SourceWindmanger.this.selectSource = 9;
                } else if (view.getId() == R.id.atv_ll) {
                    SourceWindmanger.this.selectSource = 0;
                } else if (view.getId() == R.id.dtv_ll) {
                    SourceWindmanger.this.selectSource = 1;
                } else {
                    SourceWindmanger.this.selectSource = 16;
                }
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            if (this.handler.hasMessages(0)) {
                this.handler.removeMessages(0);
            }
            this.handler.sendEmptyMessageDelayed(0, 5000L);
            this.mKeyQueue.add(Integer.valueOf(keyEvent.getKeyCode()));
            if (keyEvent.getKeyCode() == 166) {
                this.mCompNameIntent.Instrumentation(19);
                return true;
            } else if (keyEvent.getKeyCode() == 167) {
                this.mCompNameIntent.Instrumentation(20);
                return true;
            } else if (keyEvent.getKeyCode() == 4) {
                if (this.handler.hasMessages(0)) {
                    this.handler.removeMessages(0);
                }
                finish();
                return true;
            } else if (this.mKeyQueue.size() == 4) {
                String intArrayListToString = intArrayListToString(this.mKeyQueue);
                if (intArrayListToString.equals(FACTORYKEYCODES)) {
                    this.mKeyQueue.clear();
                    ComponentName componentName = new ComponentName("com.rtk.engmenu", "com.rtk.engmenu.MainActivity");
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    //intent.setFlags(268435456);
                    this.mContext.startActivity(intent);
                    finish();
                    return true;
                } else if (intArrayListToString.equals(MENUKEYCODES)) {
                    this.mKeyQueue.clear();
                    ComponentName componentName2 = new ComponentName("com.realtek.menu", "com.realtek.menu.MainActivity");
                    Intent intent2 = new Intent();
                    intent2.setComponent(componentName2);
                    //intent2.setFlags(268435456);
                    this.mContext.startActivity(intent2);
                    finish();
                    return true;
                } else if (intArrayListToString.equals(ENGMENUKEYCODES)) {
                    this.mKeyQueue.clear();
                    ComponentName componentName3 = new ComponentName("com.realtek.factorymenu", "com.realtek.factorymenu.ui.MainActivity");
                    Intent intent3 = new Intent();
                    intent3.setComponent(componentName3);
                    //intent3.setFlags(268435456);
                    this.mContext.startActivity(intent3);
                    finish();
                    return true;
                } else if (intArrayListToString.equals(ANDROIDSETTINGKEYCODES)) {
                    this.mKeyQueue.clear();
                    Intent intent4 = new Intent("android.settings.SETTINGS");
                    //intent4.setFlags(268435456);
                    this.mContext.startActivity(intent4);
                    finish();
                    return true;
                } else if (intArrayListToString.equals(SETTINGKEYCODES)) {
                    this.mKeyQueue.clear();
                    Utils.startAppPackageName(this.mContext, "com.android.tv.settings");
                    finish();
                    return true;
                } /*else if (intArrayListToString.equals(SETPANEL2K)) {
                    this.mKeyQueue.clear();
                    ExtTv.getInstance().extTvSetValueInt("panel_int_num", 28);
                    finish();
                    return true;
                } else if (intArrayListToString.equals(SETPANEL4K)) {
                    this.mKeyQueue.clear();
                    ExtTv.getInstance().extTvSetValueInt("panel_int_num", 0);
                    finish();
                    return true;
                } */else {
                    this.mKeyQueue.remove(0);
                }
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }
}