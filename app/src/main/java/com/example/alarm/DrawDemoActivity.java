package com.example.alarm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.alarm.utils.UIUtils;
import com.example.alarm.viewpager.FragmentFactory;
import com.example.alarm.widget.BezierCurveView;
import com.example.alarm.widget.DrawLineView;
import com.example.alarm.widget.PathView;
import com.example.alarm.widget.Pie;
import com.example.alarm.widget.PieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 绘制图形  点 线 圆 圆环 扇形
 * 点 canvas.drawCircle((float) towBean.TowControlPointX, (float) towBean.TowControlPoinY, 10, paint);
 * 线  BezierCurveView
 *   1、canvas.drawLine(x1, y1,x2,y2, paint);//DrawLineView
 *   2、 paint.setColor(Color.RED);
 *         path.moveTo(50, 50);
 *         path.lineTo(100, 150);//直线
 *         //path.lineTo(200, 250);//折线
 *         canvas.drawPath(path, paint);//DrawLineView
 * 圆；canvas.drawCircle((float) towBean.TowControlPointX, (float) towBean.TowControlPoinY, 10, paint);//BezierCurveView
 * 圆环：canvas.drawArc(rectF, 0, 360, false, clockRingPaint);//ClockView
 * https://blog.csdn.net/qq_18432309/article/details/51811546?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-51811546-blog-88043691.pc_relevant_downloadblacklistv1&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7Edefault-1-51811546-blog-88043691.pc_relevant_downloadblacklistv1&utm_relevant_index=2
 * https://blog.csdn.net/sqf251877543/article/details/88043691
 *
 *
 * 贝塞尔线
 * https://blog.csdn.net/weixin_44819566/article/details/110553100
 */
public class DrawDemoActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private int[] colors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    private Pie pie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_demo);
        FrameLayout layout=(FrameLayout)findViewById(R.id.layout);//获得帧布局
        final PathView pathView =(PathView)findViewById(R.id.anim_view);

        TextView textView =(TextView)findViewById(R.id.tv_run);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathView.startAnim();
            }
        });
        //DrawLineView  pie =new DrawLineView(this);
        /*pie=new Pie(this);//
            List<PieEntity> entities = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                entities.add(new PieEntity(i + 1, colors[i]));
            }
            pie.setDatas(entities);*/

        //LoveView loveView = new LoveView(this);
        //BezierCurveView bezierCurveView = new BezierCurveView(this);//贝塞尔线
        /*final StarView star=new StarView(this);//创建一个自定义的starView的View对象
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
        layout.addView(star);//向布局中添加组件*/
       /* LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        star.setLayoutParams(params);
        star.setBackgroundColor(Color.BLUE);
        star.setClickable(true);//解决只接收 down 事件
        layout.addView(star);*/
        //layout.addView(loveView);
        //pie.setClickable(true);
        //layout.addView(pie);
    }

}
