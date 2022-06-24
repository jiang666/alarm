package com.example.alarm;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.lang.reflect.Field;


/**
 * 图案移动
 */
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.alarm.bean.DateEntity;
import com.example.alarm.utils.ImgUtil;
import com.example.alarm.widget.DrawLineView;
import com.example.alarm.widget.StarView;

public class ImageMoveActivity extends AppCompatActivity {
    private static final String TAG = ImageMoveActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_move);
        FrameLayout layout=(FrameLayout)findViewById(R.id.layout);//获得帧布局
        ImageView iv_click=(ImageView)findViewById(R.id.iv_click);
        iv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("======","点击");
            }
        });
        //DrawLineView star=new DrawLineView(this);
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
        //layout.addView(star);//向布局中添加组件
        /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        star.setLayoutParams(params);
        star.setBackgroundColor(Color.BLUE);
        star.setClickable(true);//解决只接收 down 事件
        layout.addView(star);*/

        //反射获取参数值
        /*Class clazz = null;
        try {
            clazz = RecycleViewActivity;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Field spanCount = null;
        try {
            spanCount = clazz.getDeclaredField("spanCount");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        spanCount.setAccessible(true);
        try {
            // 获取字段名称
            String fieldName = spanCount.getName();

            // 获取指定对象的当前字段的值
            Object fieldVal = spanCount.get(RecycleViewActivity.class);
            System.out.println(fieldName+"="+fieldVal);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.e("========","反射获取 spanCount = " + spanCount);*/
        getFieldsValue(new DateEntity());
    }


    public void getFieldsValue(Object obj){
        Class<?> objClass = obj.getClass();
        //获取对象的所有属性(包括私有属性)
        Field[] fields = objClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            try{
                //反射对象在使用时抑制Java语言访问检查(私有也可以访问)，使得可以获取属性值
                field.setAccessible(true);
                Object value = field.get(obj);
                Log.e("======","value = " +field.getName()+ " " +  value);
            }catch(Exception e){
                Log.e("======","获取【{}】字段值失败，原因:{}"+field.getName(),e);
            }
        }

    }


     /*这里有一个知识点：
        getField 和 getDeclaredField 的区别：
        getField 获取到的是公共字段，包括当前类创建的对象可以直接调用的属性（public）包括从 父类继承的
        getDeclaredField 获取到的是声明字段：当前类原始声明的所有属性，包括私有的，但是不包括从父类继承的
————————————————
        版权声明：本文为CSDN博主「_努力努力再努力_」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/weixin_45962741/article/details/120515611*/

}
