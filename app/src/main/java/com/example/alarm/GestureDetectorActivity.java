package com.example.alarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.utils.FloatWindowManager;

public class GestureDetectorActivity extends AppCompatActivity {

    //定义滑动的最小距离
    private static final int MIN_DISTANCE=100;
    private GestureDetector gestureDetector;
    private MyGestureDetector myGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView main_button = (TextView)findViewById(R.id.main_button);

        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FloatWindowManager.getInstance().showFloatWindow();
            }
        });
        //实例化MyGestureDetector
        myGestureDetector=new MyGestureDetector();
        //实例化GestureDetector并将MyGestureDetector实例传入
        gestureDetector=new GestureDetector(this,myGestureDetector);
    }

    /**
     * 重写onTouchEvent返回一个gestureDetector的屏幕触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * 自定义MyGestureDetector类继承SimpleOnGestureListener
     */
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX()-e2.getX()>MIN_DISTANCE){
                Toast.makeText(GestureDetectorActivity.this,"左滑",Toast.LENGTH_SHORT).show();
            }else if(e2.getX()-e1.getX()>MIN_DISTANCE){
                Toast.makeText(GestureDetectorActivity.this,"右滑",Toast.LENGTH_SHORT).show();
            }else if(e1.getY()-e2.getY()>MIN_DISTANCE){
                Toast.makeText(GestureDetectorActivity.this,"上滑", Toast.LENGTH_SHORT).show();
            }else if(e2.getY()-e1.getY()>MIN_DISTANCE){
                Toast.makeText(GestureDetectorActivity.this,"下滑",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }
}

