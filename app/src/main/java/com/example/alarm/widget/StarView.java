package com.example.alarm.widget;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.alarm.R;

/**
 * https://blog.csdn.net/fairy0000000/article/details/106085683/
 */

public class StarView extends View{
    private final Paint paint;
    private Bitmap bitmap;
    private float bitmapX;//设置view的X坐标
    private float bitmapY;//设置view的Y坐标
    //自定义View需要实现一个显示的构造方法，并且重写onDraw方法，一切操作都将在这个方法上执行
    public StarView(Context context)
    {
        super(context);
        bitmapX=200;//设置view的初始位置
        bitmapY=100;
        paint=new Paint();//画笔
        bitmap= BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
    }
    //getters and setters
    public float getBitmapX() {
        return bitmapX;
    }

    public void setBitmapX(float bitmapX) {
        this.bitmapX = bitmapX;
    }

    public float getBitmapY() {
        return bitmapY;
    }

    public void setBitmapY(float bitmapY) {
        this.bitmapY = bitmapY;
    }

    public void setBitmap(Bitmap nBitmap){
        this.bitmap = nBitmap;
    }

    public void onDraw(Canvas canvas) {//重写draw方法
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);//canvas画布绘制图片
        if(bitmap.isRecycled())
            bitmap.recycle();
    }
}
