package com.example.alarm.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.alarm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/9/22.
 * 时钟控件
 * calendar.get(Calendar.HOUR_OF_DAY); 24h
 * calendar.get(Calendar.HOUR); 12h
 *
 */
public class ClockView extends View {
    /**时钟白色背景颜色*/
    @ColorInt
    protected static final int CLOCK_BACKGROUND_COLOR= 0xFFF0F0F0;

    /**时钟黑色背景颜色*/
    @ColorInt
    protected static final int CLOCK_BACKGROUND_BACK_COLOR= 0xFF000000;

    /**时钟圆环颜色*/
    @ColorInt
    protected static final int CLOCK_RING_COLOR=0xFFc9c9c9;
    /**字体颜色*/
    @ColorInt
    protected static final int TEXT_COLOR = 0xFF141414;

    /**字体颜色*/
    @ColorInt
    protected static final int TEXT_BACK_COLOR = 0xFFFFFFFF;

    /**时钟和分钟的颜色*/
    protected static final int HOUR_MINUTE_COLOR = 0xFF5B5B5B;
    /**秒钟的颜色*/
    @ColorInt
    private static final int SECOND_COLOR = 0xFFB55050;
    @ColorInt
    private static final int CLOCK_SCALE_COLOR=0xffc9c9c9;
    /**时钟最小尺寸*/
    private static final int CLOCK_MIN_SIZE=200;
    /**时钟及分钟的宽度*/
    private static final int HOUR_MINUTE_WIDTH = 3;
    /**秒钟的宽度*/
    private static final int SECOND_WIDTH = 2;
    /**时钟刻度的宽度*/
    private static final int SCALE_WIDTH=1;
    //每秒 秒针移动6°
    private static final int DEGREE = 6;
    /**时钟文本*/
    private static final String[] CLOCK_TEXT={"12","1","2","3","4","5","6","7","8","9","10","11"};
    /**时*/
    private float hour=5;
    /**分*/
    private float minute=30;
    /**秒*/
    private float second=5;
    /**绘制时钟的Paint*/
    private Paint hourPaint;
    /**绘制分钟的Paint*/
    private Paint minutePaint;
    /**绘制秒钟的Paint*/
    private Paint secondPaint;
    /**圆环的宽度*/
    private int clockRingWidth=3;
    /**时钟大小*/
    private int clockSize;
    /**绘制时钟的Paint*/
    private Paint clockPaint;
    /**绘制时钟圆环的Paint*/
    private Paint clockRingPaint;
    /**时钟中心外部圆*/
    private Paint clockCenterOuterCirclePaint;
    /**时钟中心内部圆*/
    private Paint clockCenterInnerCirclePaint;
    /**绘制时钟刻度的Paint*/
    private Paint clockScalePaint;
    /**绘制时钟文本的Paint*/
    private Paint clockTextPaint;
    /**获取时间的日历工具*/
    private Calendar calendar=null;

    private String timezone = "GMT+8";
    public ClockView(Context context) {
        super(context);
        initView();
    }
    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    protected void initView(){
        clockPaint=new Paint();
        clockPaint.setColor(CLOCK_BACKGROUND_COLOR);
        clockPaint.setAntiAlias(true);
        clockRingPaint=new Paint();
        clockRingPaint.setColor(CLOCK_RING_COLOR);
        clockRingPaint.setStrokeWidth(dp2px(clockRingWidth));
        clockRingPaint.setStyle(Paint.Style.STROKE);
        clockRingPaint.setAntiAlias(true);
        //添加阴影 0x80000000
        clockRingPaint.setShadowLayer(4, 2, 2, 0x80000000);
        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(HOUR_MINUTE_COLOR);
        hourPaint.setStrokeWidth(HOUR_MINUTE_WIDTH);
        //设置为圆角
        hourPaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        hourPaint.setShadowLayer(4, 0, 0, 0x80000000);
        minutePaint = new Paint();
        minutePaint.setAntiAlias(true);
        minutePaint.setColor(HOUR_MINUTE_COLOR);
        minutePaint.setStrokeWidth(HOUR_MINUTE_WIDTH);
        //设置为圆角
        minutePaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        minutePaint.setShadowLayer(4, 0, 0, 0x80000000);
        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setColor(SECOND_COLOR);
        secondPaint.setStrokeWidth(SECOND_WIDTH);
        //设置为圆角
        secondPaint.setStrokeCap(Paint.Cap.ROUND);
        //添加阴影
        secondPaint.setShadowLayer(4, 3, 0, 0x80000000);
        clockCenterOuterCirclePaint = new Paint();
        clockCenterOuterCirclePaint.setAntiAlias(true);
        clockCenterOuterCirclePaint.setColor(HOUR_MINUTE_COLOR);
        //添加阴影
        clockCenterOuterCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);
        clockCenterInnerCirclePaint = new Paint();
        clockCenterInnerCirclePaint.setAntiAlias(true);
        clockCenterInnerCirclePaint.setColor(SECOND_COLOR);
        //添加阴影
        clockCenterInnerCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);
        clockScalePaint=new Paint();
        clockScalePaint.setAntiAlias(true);
        clockScalePaint.setColor(CLOCK_SCALE_COLOR);
        //设置为圆角
        clockScalePaint.setStrokeCap(Paint.Cap.ROUND);
        clockScalePaint.setStrokeWidth(SCALE_WIDTH);
        clockTextPaint = new Paint();
        clockTextPaint.setAntiAlias(true);
        clockTextPaint.setStrokeWidth(1f);
        clockTextPaint.setColor(TEXT_COLOR);
        clockTextPaint.setTextSize(sp2px(13));
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        clockSize=dp2px(CLOCK_MIN_SIZE);
        if(clockSize>width){
            width=clockSize;
        }else{
            clockSize = width;
        }
        setMeasuredDimension(width, width);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w!=oldw||h!=oldh){
            clockSize=w;
        }
        int minSize=dp2px(CLOCK_MIN_SIZE);
        if(clockSize<minSize){
            clockSize=minSize;
        }
    }
    private void getTime() {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat d_f = new SimpleDateFormat(format);//设置日期格式
        d_f.setTimeZone(TimeZone.getTimeZone(timezone));  //设置时区，+08是北京时间
        String date = d_f.format(new Date());
        Log.e("=========","data  " + date);
        Date zonedate = new Date();
        try {
            zonedate = stringToDate(date,format);
        }catch (ParseException e){

        }
        calendar = Calendar.getInstance();
        calendar.setTime(zonedate);

        //calendar=Calendar.getInstance();
        int judgehour = calendar.get(Calendar.HOUR_OF_DAY);
        if(judgehour >= 18 || judgehour < 6){
            clockPaint.setColor(CLOCK_BACKGROUND_BACK_COLOR);
            clockTextPaint.setColor(TEXT_BACK_COLOR);

        }else {
            clockPaint.setColor(CLOCK_BACKGROUND_COLOR);
            clockTextPaint.setColor(TEXT_COLOR);
        }
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        System.out.println(hour + ":" + minute + ":" + second);
    }

    private Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = formatter.parse(strTime);
        return date;
    }
    public void setTimezone(String timezone){
         this.timezone = timezone;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        getTime();
        canvas.translate(clockSize / 2, clockSize / 2);
        drawClock(canvas);
        drawClockRing(canvas);
        drawClockScale(canvas);
        drawClockScaleText(canvas);
        drawHourPointer(canvas);
        drawMinutePointer(canvas);
        drawCenterOuterCircle(canvas);
        drawSecondPointer(canvas,second*DEGREE);
        drawCenterInnerCircle(canvas);
        postInvalidateDelayed(1000);
    }
    /**
     * 画表盘背景
     *
     * @param canvas 画布
     */
    private void drawClock(Canvas canvas) {
        canvas.drawCircle(0, 0, clockSize / 2 - 4, clockPaint);
        canvas.save();
    }
    /**
     * 画表盘最外层圆环
     *
     * @param canvas 画布
     */
    private void drawClockRing(Canvas canvas) {
        canvas.save();
        float radius =clockSize / 2 - dp2px(clockRingWidth + 6) / 2;
        RectF rectF = new RectF(-radius, -radius, radius, radius);
        clockRingPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, 0, 360, false, clockRingPaint);
        canvas.restore();
    }
    /**
     * 画时针
     *
     * @param canvas 画布
     */
    private void drawHourPointer(Canvas canvas) {
        int length = clockSize / 4;
        canvas.save();
        //这里没有算秒钟对时钟的影响
        float degree = hour * 5 * DEGREE + minute / 2f;
        canvas.rotate(degree, 0, 0);
        canvas.drawLine(0, 0, 0, -length, hourPaint);
        canvas.restore();
        /*Bitmap bitmap;
        Resources res = getResources();
        //bitmap = BitmapFactory.decodeResource(res, R.drawable.arrow_down);
        bitmap = BitmapFactory.decodeResource(res, R.drawable.common_listview_headview_red_arrow).copy(Bitmap.Config.ARGB_8888, true);
        //BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.drawable.arrow_down);
        //bitmap = bd.getBitmap();
        //Canva canvas2 = new Canvas(bitmap);
        //Log.e("==========","bitmap = " + bitmap);
        canvas.save();
        float degree = hour * 5 * DEGREE + minute / 2f;
        canvas.rotate(degree, 0, 0);
        canvas.drawBitmap(bitmap,0,0,null);
        canvas.save();
        canvas.restore();*/

    }
    /**
     * 画分针
     *
     * @param canvas 画布
     */
    private void drawMinutePointer(Canvas canvas) {
        int length = clockSize / 3-dp2px(2);
        canvas.save();
        float degree = minute * DEGREE + second / 10f;
        canvas.rotate(degree, 0, 0);
        canvas.drawLine(0, 0, 0, -length, minutePaint);
        canvas.restore();
    }
    /**
     * 画秒针
     *
     * @param canvas 画布
     */
    private void drawSecondPointer(Canvas canvas, float degrees) {
        int length = clockSize / 2-dp2px(15);
        canvas.save();
        canvas.rotate(degrees);
        canvas.drawLine(0, length / 5, 0, -length * 4 / 5, secondPaint);
        canvas.restore();
    }
    /**
     * 绘制时钟刻度
     * @param canvas
     */
    private void drawClockScale(Canvas canvas){
        canvas.save();
        int startY=clockSize / 2 - dp2px(clockRingWidth + 6) / 2-dp2px(clockRingWidth)/2;
        int endY=startY-dp2px(3);
        int endY2=startY-dp2px(5);
        //canvas.rotate(-180);
        for (int i=0; i<=360; i+=DEGREE){
            if(i%5==0) {
                canvas.drawLine(0, startY, 0, endY2, clockScalePaint);
            }else{
                canvas.drawLine(0, startY, 0, endY, clockScalePaint);
            }
            canvas.rotate(DEGREE);
        }
        canvas.restore();
    }
    /**
     * 绘制时钟刻度文本
     * @param canvas
     */
    private void drawClockScaleText(Canvas canvas){
        canvas.save();
        //canvas.rotate(-180f);
        float dis=clockTextPaint.measureText(CLOCK_TEXT[1])/2;
        Paint.FontMetrics fontMetrics=clockTextPaint.getFontMetrics();
        float fontHeight=fontMetrics.descent-fontMetrics.ascent;
        float radius=clockSize / 2 - dp2px(clockRingWidth + 6) / 2-dp2px(clockRingWidth)/2-dp2px(10)-fontHeight/2;
        for(int i=0;i<CLOCK_TEXT.length;i++){
            float x=(float) (Math.sin(Math.PI - Math.PI / 6 * i) * radius - dis);
            if(i==0){
                x -=dis;
            }
            float y= (float) (Math.cos(Math.PI-Math.PI/6*i)*radius+dis);
            canvas.drawText(CLOCK_TEXT[i],x,y,clockTextPaint);
        }
        canvas.restore();
    }

    /**
     * 画中心黑圆
     *
     * @param canvas 画布
     */
    private void drawCenterOuterCircle(Canvas canvas) {
        int radius = clockSize / 30;
        canvas.save();
        canvas.drawCircle(0, 0, radius, clockCenterOuterCirclePaint);
        canvas.restore();
    }
    /**
     * 红色中心圆
     *
     * @param canvas 画布
     */
    private void drawCenterInnerCircle(Canvas canvas) {
        int radius = clockSize / 55;
        canvas.save();
        canvas.drawCircle(0, 0, radius, clockCenterInnerCirclePaint);
        canvas.restore();
    }
    /**
     * 将 dp 转换为 px
     *
     * @param dp 需转换数
     * @return 返回转换结果
     */
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }
}