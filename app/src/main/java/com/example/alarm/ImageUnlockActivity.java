package com.example.alarm;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * 图案解锁
 */
public class ImageUnlockActivity extends AppCompatActivity {
    private static String TAG = ImageUnlockActivity.class.getSimpleName();

    //定义⼀个数组 保存每个点的控件
    ArrayList<ImageView> dotsList;
    ArrayList<Integer> lineTagsList;
    ArrayList<ImageView> selectedList;
    int tag;
    //保存上⼀次被点亮的点的对象
    ImageView lastSelectedDot;
    //记录滑动的密码
    StringBuilder password;
    //保存原始密码
    String orgPassword;
    //保存第⼀次输⼊的密码
    String firstPassword;
    //提示的⽂本视图
    TextView alertTextView;
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //判断是否已经显示
        if (hasFocus){
            //获取容器
            RelativeLayout rl = findViewById(R.id.root_layout);
            //获取背景视图
            ImageView iv = findViewById(R.id.opView);
            //获取x 和 y坐标
            int x = iv.getLeft();
            int y = iv.getTop();
            //获取屏幕密度
            float scale = getResources().getDisplayMetrics().density;
            //创建横线 6条
            //12 23
            //45 56
            //78 89
            tag = 12;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 2; j++) {
                    //创建⼀个视图⽤于显示线
                    ImageView lineView = new ImageView(this);
                    lineView.setBackgroundResource(R.drawable.tuya_ao_tab_bg);
                    lineView.setVisibility(View.INVISIBLE);
                    lineView.setTag(tag);
                    lineTagsList.add(tag);//保存线的tag值
                    tag += 11; //同⼀⾏相差11
                    //创建布局参数
                    RelativeLayout.LayoutParams params = new
                            RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int)(x + 46.6*scale) + (int)(99*scale*j);
                    params.topMargin = (int)(y + 170*scale) + (int)(99*scale*i);
                    rl.addView(lineView, params);
                }
                //换⼀⾏ 相差22
                tag += 11;
            }
            //创建竖线 4条
            //14 25 36
            //47 58 69
            tag = 14;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    //创建⼀个视图⽤于显示线
                    ImageView lineView = new ImageView(this);
                    lineView.setBackgroundResource(R.drawable.tuya_ao_tab_bg);
                    lineView.setVisibility(View.INVISIBLE);
                    lineView.setTag(tag);
                    lineTagsList.add(tag);//保存线的tag值
                    tag += 11;
                    //创建布局参数
                    RelativeLayout.LayoutParams params = new
                            RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int)(x + 42*scale) + (int)(99*scale*j);
                    params.topMargin = (int)(y + 170*scale) + (int)(99*scale*i);
                    rl.addView(lineView, params);
                }
            }
            //创建斜线
            //左斜
            // 24 35
            // 57 68
            // 右斜
            // 15 26
            // 48 59
            tag = 24;
            int rTag = 15;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    //创建⼀个视图⽤于显示线
                    ImageView rLineView = new ImageView(this);
                    rLineView.setTag(rTag);
                    lineTagsList.add(rTag);//保存线的tag值
                    rTag += 11;
                    //设置图⽚
                    rLineView.setBackgroundResource(R.drawable.tuya_ao_tab_bg);
                    //创建布局参数
                    rLineView.setVisibility(View.INVISIBLE);
                    RelativeLayout.LayoutParams params = new
                            RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int)(x + 42*scale) + (int)(99*scale*j);
                    params.topMargin = (int)(y + 170*scale) + (int)(99*scale*i);
                    rl.addView(rLineView, params);
                    ImageView lLineView = new ImageView(this);
                    lLineView.setTag(tag);
                    lineTagsList.add(tag);//保存线的tag值
                    tag += 11;
                    lLineView.setVisibility(View.INVISIBLE);
                    lLineView.setBackgroundResource(R.drawable.tuya_ao_tab_bg);
                    params.leftMargin = (int)(x + 53.3*scale) + (int)(99*scale*j);
                    params.topMargin = (int)(y + 170*scale) + (int)(99*scale*i);
                    rl.addView(lLineView,params);
                }
                tag += 11;
                rTag += 11;
            }
            //创建9个点
            tag = 1;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //创建⽤于显示点的视图
                    ImageView dotView = new ImageView(this);;
                    //设置对应的tag值
                    dotView.setTag(tag);
                    tag++;
                    //隐藏视图
                    dotView.setVisibility(View.INVISIBLE);
                    //显示对应的图⽚
                    dotView.setBackgroundResource(R.drawable.applybt_bg);
                    //创建控件的尺⼨
                    RelativeLayout.LayoutParams params = new
                            RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.leftMargin = (int)(x + 35.33*scale) + (int)(98.66*scale*j);
                    params.topMargin = (int)(y + 162*scale) + (int)(98.66*scale*i);
                    //将控件添加到容器中
                    rl.addView(dotView, params);
                    //将这个控件添加到数组
                    dotsList.add(dotView);
                }
            }
        }
    }
    //监听触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取事件的类型
        int action = event.getAction();
        ImageView selected;
        float x;
        float y;
        //判断是什么事件
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //按下
                //获取触摸点的坐标
                x = event.getX();
                y = event.getY();
                //判断x y是不是在某个点的范围内
                selected = dotOfTouch(x, y);
                if (selected != null) {
                    //点亮
                    selected.setVisibility(View.VISIBLE);
                    //记录当前这个点
                    lastSelectedDot = selected;
                    //将tag值拼接到密码中
                    password.append(selected.getTag());
                    //将点亮的点添加到数组中
                    selectedList.add(selected);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //移动
                //获取触摸点的坐标
                x = event.getX();
                y = event.getY();
                //判断x y是不是在某个点的范围内
                selected = dotOfTouch(x, y);
                if (selected != null) {
                    //判断这个点是不是第⼀个点
                    if (lastSelectedDot == null){
                        //第⼀个点
                        selected.setVisibility(View.VISIBLE);
                        //记录
                        lastSelectedDot = selected;
                        //将tag值拼接到密码中
                        password.append(selected.getTag());
                        //将点亮的点添加到数组中
                        selectedList.add(selected);
                    } else{
                        //不是第⼀个点
                        //获取上⼀个点和当前点的tag
                        int lTag = (Integer) lastSelectedDot.getTag();
                        int cTag = (Integer) selected.getTag();
                        //获取两个线的tag值 small * 10 + big
                        int lineTag = lTag > cTag ? cTag*10+lTag: lTag*10+cTag;
                        //判断这条线是否存在
                        if (lineTagsList.contains(lineTag)){
                            //线存在
                            //点亮点
                            selected.setVisibility(View.VISIBLE);
                            //将tag值拼接到密码中
                            password.append(selected.getTag());
                            //点亮这条线
                            //获取容器对象
                            RelativeLayout rl = findViewById(R.id.root_layout);
                            //通过tag查找⼦控件
                            ImageView iv = rl.findViewWithTag(lineTag);
                            //点亮线
                            iv.setVisibility(View.VISIBLE);
                            //记录这个点
                            lastSelectedDot = selected;
                            //将点亮的点添加到数组中
                            selectedList.add(selected);
                            //将点亮的线添加到数组中
                            selectedList.add(iv);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //离开
                // 1.绘制密码 和原始密码⽐较
                // 2.设置密码 第⼀次
                // 3.设置密码 第⼆次
                if (orgPassword != null){
                    //有密码了
                    if (password.toString().equals(orgPassword)){
                        alertTextView.setText("解锁密码成功");
                    } else{
                        alertTextView.setText("解锁密码失败");
                        alertTextView.setTextColor(Color.BLUE);
                    }
                } else{
                    //设置密码
                    //判断是第⼀次还是第⼆次确认密码
                    if (firstPassword == null){
                        //设置密码的第⼀次
                        firstPassword = password.toString();
                        //提示确认密码
                        alertTextView.setText("请确认密码图案");
                    } else{
                        //第⼆次确认密码
                        //判断两次是否⼀致
                        if (firstPassword.equals(password.toString())){
                            //设置成功
                            alertTextView.setText("设置密码成功");
                            //保存密码
                            SharedPreferences sp = getSharedPreferences("password",0);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("pwd",firstPassword);
                            editor.commit();
                        } else{
                            //设置失败
                            alertTextView.setText("两次密码不⼀致 请重新设置");
                            firstPassword = null;
                        }
                    }
                }
                clean();
                break;
            default:
                break;
        }
        return true;
    }
    //清空
    public void clean(){
        password.setLength(0);
        //隐藏所有选中的视图 点 线
        for (ImageView iv:selectedList){
            iv.setVisibility(View.INVISIBLE);
        }
        //清空数组
        selectedList.clear();
    }
    //写⼀个⽅法 处理 判断触摸点是否在某个控件内部
    public ImageView dotOfTouch(float x, float y){
        //计算状态栏或者标题栏的距离
/*
1. 让触摸点 切换到控件的⽗视图中
Point p = new Point();
getWindowManager().getDefaultDisplay().getSize(p);
//获取容器本身的宽⾼
RelativeLayout rl = findViewById(R.id.root_layout);
//计算状态栏的⾼度
float padding = p.y - rl.getHeight();
*/
/*
//2.让控件切换到屏幕坐标系
ImageView firt = dotsList.get(0);
int[] loc = new int[2];
firt.getLocationOnScreen(loc);
System.out.println("相对屏幕y:"+loc[1]);
System.out.println("相对容器y:"+firt.getY());
System.out.println("原本宽度:"+firt.getWidth());
*/
        //遍历数组
        for (ImageView dot:dotsList){
            //获取这个dot相对于屏幕的x y
            int[] loc = new int[2];
            dot.getLocationOnScreen(loc);
            int dx = loc[0];
            int dy = loc[1];
            //获取右边的偏移量
            int r = dx + dot.getWidth();
            //获取最底部的偏移量
            int b = dy + dot.getHeight();
            //判断这个点是否在这个范围内
            if ((x <= r && x >= dx) &&
                    (y <= b && y >= dy)){
                return dot;
            }
        }
        return null;
    }
/**
* 安卓 在容器中添加的控件需要被window计算/测量
* window -> viewGroup -> ⼦控件
* 通常在onCreate、onStart、onResume⽆法获取到控件本身的尺⼨
*
* 所有的测量都是在另外⼀个线程操作
* 如果想要获取控件的尺⼨
*/
    /**
     * Android数据存储4种
     * 1. sharedPreference 偏好设置
     * 2. file
     * 3. sqlite3
     * 4. network
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_unlock);
        //准备好数组 实例化对象
        dotsList = new ArrayList<>();
        lineTagsList = new ArrayList<>();
        password = new StringBuilder();
        selectedList = new ArrayList<>();
        //获取xml的⽂本控件
        alertTextView = findViewById(R.id.tv_alert);
        //获取偏好设置对象 整个程序共享 单例
        //key-value Map
        //name:⽂件的路劲
        //mode:模式
        //只能读
/*
SharedPreferences sp = getSharedPreferences("abc",MODE_PRIVATE);
String result = sp.getString("pwd",null);
System.out.println(result);
*/
/*
//如果需要存储数据 必须获取Editor对象
SharedPreferences.Editor editor = sp.edit();
editor.putString("pwd", "123");
//保存
editor.commit(); //⽴刻保存
//editor.apply(); //异步 让⼀个线程处理保存 不是⻢上
*/
        //查找偏好设置⾥⾯是否有保存的密码pwd
        SharedPreferences sp =
                getSharedPreferences("password",MODE_PRIVATE);
        //获取pwd对应密码
        orgPassword = sp.getString("pwd",null);
        if (orgPassword == null){
            alertTextView.setText("请设置密码图案");
        }else{
            alertTextView.setText("请绘制密码图案");
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        float w = p.x;
        float h = p.y;
        if (w > h){
            System.out.println("横屏");
        } else{
            System.out.println("竖屏");
        }
    }
}
