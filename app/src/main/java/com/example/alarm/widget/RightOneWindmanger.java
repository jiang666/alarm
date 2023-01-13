package com.example.alarm.widget;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.alarm.R;
import com.example.alarm.utils.CompNameIntent;
import com.example.alarm.utils.WindowManagerUitls;

/* loaded from: classes2.dex */
public class RightOneWindmanger extends LinearLayout {
    private static final int IMG_HIDE = 1;
    private static final int IMG_SHOW = 0;
    public static int paramY = 490;
    private static int statusBarHeight;
    public static int viewHeight;
    public static int viewWidth;
    private long endTime;
    AbsoluteLayout half_ring_lay;
    boolean half_ring_layIsTure;
    private boolean isclick;
    private int lastParamY;
    private CompNameIntent mCompNameIntent;
    Context mContext;
    Handler mHandler;
    private View.OnClickListener mOnClickListener;
    private WindowManager.LayoutParams mParams;
    private float mxInView;
    View.OnTouchListener onDragTouchListener;
    ImageView right_icon_image;
    RelativeLayout right_icon_lay;
    LinearLayout right_icon_linearlayout;
    private int screenHeight;
    private int screenWidth;
    private long startTime;
    private float uxInView;
    private View view;
    private WindowManager windowManager;
    private float xInView;

    public RightOneWindmanger(Context context) {
        super(context);
        this.half_ring_layIsTure = false;
        this.startTime = 0L;
        this.endTime = 0L;
        this.mHandler = new Handler() { // from class: com.src.ui.kjdsidepull.View.RightOneWindmanger.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                int i = message.what;
                if (i != 0) {
                    if (i == 1 && !RightOneWindmanger.this.mCompNameIntent.isRunPackage(RightOneWindmanger.this.mContext, "com.kjd.draw")) {
                        RightOneWindmanger.this.right_icon_linearlayout.setVisibility(GONE);
                        return;
                    }
                    return;
                }
                RightOneWindmanger.this.right_icon_linearlayout.setVisibility(VISIBLE);
            }
        };
        this.onDragTouchListener = new View.OnTouchListener() { // from class: com.src.ui.kjdsidepull.View.RightOneWindmanger.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (RightOneWindmanger.this.mCompNameIntent.isForeground(RightOneWindmanger.this.mContext, "AnnotationActivity")) {
                    return true;
                }
                RightOneWindmanger.this.mHandler.removeMessages(1);
                RightOneWindmanger.this.mHandler.sendEmptyMessageDelayed(1, 5000L);
                int action = motionEvent.getAction();
                if (action == 0) {
                    RightOneWindmanger.this.mContext.sendBroadcast(new Intent(Constant.ACTION_TOUCH_TO_ANDROID));
                    RightOneWindmanger.this.isclick = false;
                    RightOneWindmanger.this.startTime = System.currentTimeMillis();
                    RightOneWindmanger.this.xInView = motionEvent.getRawY();
                    RightOneWindmanger rightOneWindmanger = RightOneWindmanger.this;
                    rightOneWindmanger.lastParamY = rightOneWindmanger.mParams.y;
                } else if (action == 1) {
                    RightOneWindmanger.this.endTime = System.currentTimeMillis();
                    RightOneWindmanger.this.uxInView = motionEvent.getRawY();
                    float abs = Math.abs(RightOneWindmanger.this.uxInView - RightOneWindmanger.this.xInView);
                    if (RightOneWindmanger.this.endTime - RightOneWindmanger.this.startTime >= 300.0d) {
                        RightOneWindmanger.this.isclick = true;
                    } else if (abs > 250.0f) {
                        RightOneWindmanger.this.isclick = true;
                    } else {
                        RightOneWindmanger.this.isclick = false;
                    }
                    RightOneWindmanger.paramY = RightOneWindmanger.this.mParams.y;
                    Utils.setNonThroughRegion(RightOneWindmanger.this.mContext, 2, RightOneWindmanger.this.screenWidth - RightOneWindmanger.viewWidth, RightOneWindmanger.this.mParams.y, RightOneWindmanger.this.screenWidth, RightOneWindmanger.this.mParams.y + RightOneWindmanger.viewHeight);
                    RightOneWindmanger.this.mContext.sendBroadcast(new Intent(Constant.ACTION_TOUCH_TO_PC));
                } else if (action == 2) {
                    RightOneWindmanger.this.isclick = true;
                    RightOneWindmanger.this.mxInView = motionEvent.getRawY();
                    RightOneWindmanger.this.updawPercent();
                }
                return RightOneWindmanger.this.isclick;
            }
        };
        this.mOnClickListener = new View.OnClickListener() { // from class: com.src.ui.kjdsidepull.View.RightOneWindmanger.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                int id = view.getId();
                if (RightOneWindmanger.this.mCompNameIntent.isForeground(RightOneWindmanger.this.mContext, "AnnotationActivity")) {
                    return;
                }
                RightOneWindmanger.this.mHandler.removeMessages(1);
                if (id == R.id.right_icon_linearlayout) {
                    Animation loadAnimation = AnimationUtils.loadAnimation(RightOneWindmanger.this.mContext, R.anim.anticlockwise_rotation);
                    loadAnimation.setInterpolator(new LinearInterpolator());
                    if (loadAnimation != null) {
                        RightOneWindmanger.this.right_icon_image.startAnimation(loadAnimation);
                    }
                    WindowManagerUitls.createRightIconWindowView(RightOneWindmanger.this.mContext);
                    WindowManagerUitls.removeRightOneWindowView(RightOneWindmanger.this.mContext);
                    RightOneWindmanger.this.mContext.sendBroadcast(new Intent(Constant.ACTION_TOUCH_TO_ANDROID));
                }
            }
        };
        this.mContext = context;
        this.windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.right_one_window, this);
        init();
    }

    public void init() {
        this.view = findViewById(R.id.right_icon_layout);
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Service.WINDOW_SERVICE);
        this.screenWidth = windowManager.getDefaultDisplay().getWidth();
        this.screenHeight = windowManager.getDefaultDisplay().getHeight();
        viewWidth = (int) getResources().getDimension(R.dimen.side_button_width);
        viewHeight = (int) getResources().getDimension(R.dimen.side_button_height);
        this.right_icon_image = (ImageView) findViewById(R.id.right_icon_image);
        this.right_icon_lay = (RelativeLayout) findViewById(R.id.right_icon_layout);
        this.right_icon_linearlayout = (LinearLayout) findViewById(R.id.right_icon_linearlayout);
        this.mCompNameIntent = new CompNameIntent(this.mContext);
        this.right_icon_linearlayout.setOnClickListener(this.mOnClickListener);
        this.right_icon_linearlayout.setOnTouchListener(this.onDragTouchListener);
        this.mHandler.sendEmptyMessageDelayed(1, 5000L);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void updawPercent() {
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService(Service.WINDOW_SERVICE);
        this.mParams.gravity = 53;
        this.mParams.x = 0;
        this.mParams.y = this.lastParamY + ((int) (this.mxInView - this.xInView));
        if (this.mParams.y < 0) {
            this.mParams.y = 0;
        }
        int i = this.mParams.y;
        int i2 = viewHeight;
        if (i + i2 > 1080) {
            this.mParams.y = 1080 - i2;
        }
        windowManager.updateViewLayout(this, this.mParams);
    }

    public void setParams(WindowManager.LayoutParams layoutParams) {
        this.mParams = layoutParams;
        Utils.setNonThroughRegion(this.mContext, 2, this.screenWidth - viewWidth, layoutParams.y, this.screenWidth, this.mParams.y + viewHeight);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Log.e("============"," MotionEvent.ACTION = " + motionEvent.getAction());
        if (this.right_icon_linearlayout.getVisibility() == VISIBLE) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, 5000L);
        } else {
            this.mHandler.sendEmptyMessage(0);
        }
        motionEvent.getAction();
        return super.onTouchEvent(motionEvent);
    }
}