package com.example.alarm;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.LinearLayout;

import com.example.alarm.utils.ImgUtil;
import com.example.alarm.widget.BezierCurveView;
import com.example.alarm.widget.DrawLineView;
import com.example.alarm.widget.LoveView;
import com.example.alarm.widget.StarView;

public class ImageMoveActivity extends AppCompatActivity {
    private static final String TAG = ImageMoveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_move);
    }
}
