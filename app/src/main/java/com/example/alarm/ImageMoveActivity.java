package com.example.alarm;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;


/**
 * 图案移动
 */
import android.widget.FrameLayout;

import com.example.alarm.utils.ImgUtil;
import com.example.alarm.widget.StarView;

public class ImageMoveActivity extends AppCompatActivity {
    private static final String TAG = ImageMoveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_move);
        FrameLayout layout=(FrameLayout)findViewById(R.id.layout);//获得帧布局

        final StarView star=new StarView(this);//创建一个自定义的starView的View对象
        String filepath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "photos" + File.separator + "0138a6d52af2419088d5f78a9368df75.jpg";
        Log.e(TAG, filepath);
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bitmap = ImgUtil.getBitmapByPath(filepath);
            if (bitmap != null) {
                star.setBitmap(bitmap);
            }
        }
        star.setOnTouchListener(new View.OnTouchListener() {//给view对象创建一个触摸的监听事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                star.setBitmapX(event.getX());//设置view的坐标为手指触碰的坐标
                star.setBitmapY(event.getY());
                star.invalidate();//使原来的整个窗口无效，对view进行刷新重绘
                return true;
            }
        });
        layout.addView(star);//向布局中添加组件
    }
}
