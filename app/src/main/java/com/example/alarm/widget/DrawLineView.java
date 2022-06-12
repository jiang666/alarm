package com.example.alarm.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.alarm.R;

import static com.example.alarm.widget.ClockView.CLOCK_BACKGROUND_COLOR;

/**
 * https://blog.csdn.net/fairy0000000/article/details/106085683/
 * https://github.com/Lowae/DiyViewPracticeDemo
 */

public class DrawLineView extends View{

    private static final int TEXT_BACK_COLOR= 0xFF000000;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private Paint mPaint;
    private Paint paint;
    private Path path;

    public DrawLineView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        path = new Path();

        mPaint=new Paint();//画笔
        //mPaint.setColor(TEXT_BACK_COLOR);
        //设置抗锯齿
        mPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
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
                //清空原有的轨道
                path.reset();
                //移动到起始点
                path.moveTo(x1, y1);
                break;
            case MotionEvent.ACTION_MOVE://移动
                Log.e("========","移动");
                x2 = event.getX();
                y2 = event.getY();
                final float endX = (x1 + event.getX()) / 2;
                final float endY = (y1 + event.getY()) / 2;
                //画出轨道曲线，quad是二次曲线
                path.quadTo(x1, y1, endX, endY);//绘制触摸线
                x1 = event.getX();
                y1 = event.getY();
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
        //画出轨道线
        canvas.drawPath(path, mPaint);//绘制触摸线
        //canvas.drawLine(x1, y1,x2,y2, paint);
        /**
         *画圆弧  空心或者实心 Paint.Style.STROKE 空心  Paint.Style.FULL 实心
         * 第四个参数：boolean useCenter
         * true 经过圆心
         * false 不经过圆心
         *
         * 第一个参数：oval为确定圆弧区域的矩形，圆弧的中心点为矩形的中心点
         * 第二个参数：startAngle为圆弧的开始角度（时钟3点的方向为0度，顺时钟方向为正）
         * 第三个参数：sweepAngle为圆弧的扫过角度（正数为顺时钟方向，负数为逆时钟方向）
         * 第四个参数：useCenter表示绘制的圆弧是否与中心点连接成闭合区域
         * 第五个参数：paint为绘制圆弧的画笔
         * ————————————————
         * 版权声明：本文为CSDN博主「盛大人很低调」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
         * 原文链接：https://blog.csdn.net/sqf251877543/article/details/88043691
         */
        /*Paint p = new Paint();//这个是画矩形的画笔，方便大家理解这个圆弧
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.RED);

        mPaint.setAntiAlias(true);//取消锯齿
        mPaint.setStyle(Paint.Style.FILL);//full//设置画圆弧的画笔的属性为描边(空心)，个人喜欢叫它描边，叫空心有点会引起歧义
        mPaint.setStrokeWidth(6);
        mPaint.setColor(Color.CYAN);

        //这是一个居中的圆
        float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;
        RectF oval = new RectF( x, y,
                getWidth() - x, getHeight() - y);
        canvas.drawArc(oval,0,270,false,mPaint);//画圆弧，这个时候，绘制没有经过圆心
        canvas.drawRect(oval, p);//画矩形*/

       /* path.moveTo(50, 50);
        path.lineTo(100, 150);//直线
        path.lineTo(150,170);//折线
        canvas.drawPath(path, mPaint);*/
    }
}
