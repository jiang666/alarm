package com.example.alarm.widget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
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
public class RightIconWindowView extends LinearLayout {
    private static int statusBarHeight;
    public static int viewHeight;
    public static int viewWidth;
    ImageView annotation_imageview;
    ImageView back_imageview;
    private long endTime;
    AbsoluteLayout half_ring_lay;
    boolean half_ring_layIsTure;
    ImageView home_imageview;
    private boolean isclick;
    private CompNameIntent mCompNameIntent;
    Context mContext;
    Handler mHandler;
    private View.OnClickListener mOnClickListener;
    private WindowManager.LayoutParams mParams;
    ImageView menu_imageview;
    View.OnTouchListener onDragTouchListener;
    ImageView right_icon_image;
    RelativeLayout right_icon_lay;
    LinearLayout right_icon_linearlayout;
    ImageView source_imageview;
    private long startTime;
    ImageView task_imageview;
    private float uxInView;
    private float uyInView;
    private View view;
    private WindowManager windowManager;
    private float xDownInScreen;
    private float xInScreen;
    private float xInView;
    private float yDownInScreen;
    private float yInScreen;
    private float yInView;

    public RightIconWindowView(Context context) {
        super(context);
        this.startTime = 0L;
        this.endTime = 0L;
        this.half_ring_layIsTure = false;
        this.mHandler = new Handler() { // from class: com.src.ui.kjdsidepull.View.RightIconWindowView.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                super.handleMessage(message);
                if (message.what == 0) {
                    RightIconWindowView.this.hideRightWindowView();
                }
            }
        };
        this.onDragTouchListener = new View.OnTouchListener() { // from class: com.src.ui.kjdsidepull.View.RightIconWindowView.2
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (!RightIconWindowView.this.half_ring_layIsTure) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        RightIconWindowView.this.xInView = motionEvent.getRawY();
                        RightIconWindowView.this.yInView = motionEvent.getY();
                        RightIconWindowView.this.xDownInScreen = motionEvent.getRawX();
                        RightIconWindowView.this.yDownInScreen = motionEvent.getRawY() - RightIconWindowView.this.getStatusBarHeight();
                        RightIconWindowView.this.xInScreen = motionEvent.getRawX();
                        RightIconWindowView.this.yInScreen = motionEvent.getRawY() - RightIconWindowView.this.getStatusBarHeight();
                    } else if (action == 1) {
                        RightIconWindowView.this.uxInView = motionEvent.getRawY();
                        RightIconWindowView.this.uyInView = motionEvent.getY();
                    } else if (action == 2) {
                        RightIconWindowView.this.xInScreen = motionEvent.getRawX();
                        RightIconWindowView.this.yInScreen = motionEvent.getRawY() - RightIconWindowView.this.getStatusBarHeight();
                        RightIconWindowView.this.updawPercent();
                    }
                }
                return RightIconWindowView.this.isclick;
            }
        };
        this.mOnClickListener = new View.OnClickListener() { // from class: com.src.ui.kjdsidepull.View.RightIconWindowView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                RightIconWindowView.this.mHandler.removeMessages(0);
                RightIconWindowView.this.mHandler.sendEmptyMessageDelayed(0, 5000L);
                int id = view.getId();
                switch (id) {
                    case R.id.annotation_imageview /* 2131361872 */:
                        RightIconWindowView.this.hideRightWindowView();
                        RightIconWindowView.this.mCompNameIntent.Annotation();
                        return;
                    case R.id.back_imageview /* 2131361878 */:
                        RightIconWindowView.this.mCompNameIntent.Back();
                        return;
                    case R.id.home_imageview /* 2131362006 */:
                        RightIconWindowView.this.mCompNameIntent.Home();
                        return;
                    case R.id.menu_imageview /* 2131362086 */:
                        /*WindowManagerUitls.createMenusWindowView(RightIconWindowView.this.mContext, 50, 320, 1, false);
                        RightIconWindowView.this.hideRightWindowView();*/
                        return;
                    case R.id.right_icon_linearlayout /* 2131362129 */:
                        Animation loadAnimation = AnimationUtils.loadAnimation(RightIconWindowView.this.mContext, R.anim.clockwise_rotation);
                        loadAnimation.setInterpolator(new LinearInterpolator());
                        if (loadAnimation != null) {
                            RightIconWindowView.this.right_icon_image.startAnimation(loadAnimation);
                        }
                        RightIconWindowView.this.hideRightWindowView();
                        return;
                    case R.id.source_imageview /* 2131362169 */:
                        WindowManagerUitls.createSourceWindmanger(RightIconWindowView.this.mContext, 50, 0, 1, false);
                        //WindowManagerUitls.removeMenusWindowView(RightIconWindowView.this.mContext);
                        RightIconWindowView.this.hideRightWindowView();
                        return;
                    case R.id.task_imageview /* 2131362190 */:
                        RightIconWindowView.this.mCompNameIntent.Task();
                        return;
                    default:
                        return;
                }
            }
        };
        this.mContext = context;
        this.windowManager = (WindowManager) context.getSystemService(Service.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.right_icon_window, this);
        init();
    }

    public void init() {
        this.view = findViewById(R.id.right_icon_layout);
        viewWidth = (int) getResources().getDimension(R.dimen.side_window_width);
        viewHeight = (int) getResources().getDimension(R.dimen.side_window_height);
        this.right_icon_lay = (RelativeLayout) findViewById(R.id.right_icon_layout);
        this.right_icon_linearlayout = (LinearLayout) findViewById(R.id.right_icon_linearlayout);
        this.right_icon_image = (ImageView) findViewById(R.id.right_icon_image);
        this.half_ring_lay = (AbsoluteLayout) findViewById(R.id.half_ring_lay);
        this.back_imageview = (ImageView) findViewById(R.id.back_imageview);
        this.home_imageview = (ImageView) findViewById(R.id.home_imageview);
        this.task_imageview = (ImageView) findViewById(R.id.task_imageview);
        this.source_imageview = (ImageView) findViewById(R.id.source_imageview);
        this.annotation_imageview = (ImageView) findViewById(R.id.annotation_imageview);
        this.menu_imageview = (ImageView) findViewById(R.id.menu_imageview);
        this.mCompNameIntent = new CompNameIntent(this.mContext);
        this.right_icon_linearlayout.setOnClickListener(this.mOnClickListener);
        this.back_imageview.setOnClickListener(this.mOnClickListener);
        this.home_imageview.setOnClickListener(this.mOnClickListener);
        this.task_imageview.setOnClickListener(this.mOnClickListener);
        this.source_imageview.setOnClickListener(this.mOnClickListener);
        this.annotation_imageview.setOnClickListener(this.mOnClickListener);
        this.menu_imageview.setOnClickListener(this.mOnClickListener);
        this.mHandler.sendEmptyMessageDelayed(0, 5000L);
        right_icon_linearlayout.setGravity(Gravity.TOP);
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
        windowManager.getDefaultDisplay().getHeight();
        this.mParams.gravity = 53;
        this.mParams.x = 0;
        this.mParams.y = (int) (this.yInScreen - this.yInView);
        windowManager.updateViewLayout(this, this.mParams);
    }

    public void hideRightWindowView() {

        WindowManagerUitls.createRightOneWindowView(this.mContext);
        WindowManagerUitls.removeRightIconWindowView(this.mContext);
        if (!WindowManagerUitls.isMenuShow && !WindowManagerUitls.isSourceShow) {
            this.mContext.sendBroadcast(new Intent(Constant.ACTION_TOUCH_TO_PC));
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessageDelayed(0, 5000L);
        Log.e("============"," MotionEvent.ACTION_OUTSID");
        if (motionEvent.getAction() == 4) {
            hideRightWindowView();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setParams(WindowManager.LayoutParams layoutParams) {
        this.mParams = layoutParams;
    }
}