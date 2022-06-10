package com.example.alarm.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.alarm.R;

import static com.example.alarm.widget.ClockView.CLOCK_BACKGROUND_COLOR;

/**
 * https://blog.csdn.net/fairy0000000/article/details/106085683/
 */

public class DrawLineView extends View{

    private static final int TEXT_BACK_COLOR= 0xFF000000;
    private  Paint paint;
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public DrawLineView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        paint=new Paint();//画笔
        paint.setColor(TEXT_BACK_COLOR);
        //设置抗锯齿
        paint.setAntiAlias(true);
    }

    public DrawLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public DrawLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("========","event.getAction " + event.getAction());
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://按下
                Log.e("========","按下");
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_MOVE://移动
                Log.e("========","移动");
                x2 = event.getX();
                y2 = event.getY();
                invalidate();
                return true;
                //break;
            case MotionEvent.ACTION_UP://弹起
                Log.e("========","弹起");
                break;
            case MotionEvent.ACTION_CANCEL://事件取消
                Log.e("========","事件取消");
                break;
            case MotionEvent.ACTION_OUTSIDE://外部点击
                Log.e("========",event.getDevice().getName() + "外部点击");
                break;
        }
        return super.onTouchEvent(event);
    }

    public void onDraw(Canvas canvas) {//重写draw方法
        super.onDraw(canvas);
        canvas.drawLine(x1, y1,x2,y2, paint);
    }
}
