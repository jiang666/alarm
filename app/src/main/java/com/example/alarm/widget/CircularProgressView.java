package com.example.alarm.widget;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.alarm.R;

/**
 * Created by Android_Jian on 2018/11/3.
 * https://blog.csdn.net/weixin_35636570/article/details/117276228?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7Edefault-2-117276228-blog-121272660.pc_relevant_vip_default&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EOPENSEARCH%7Edefault-2-117276228-blog-121272660.pc_relevant_vip_default&utm_relevant_index=3
 */
public class CircularProgressView extends View {

    //根据数据显示的圆弧Paint
    private Paint mArcPaint;
    //文字描述的paint
    private Paint mTextPaint;
    //圆弧开始的角度
    private final float startAngle = 180;
    //圆弧结束的角度
    private final float endAngle = 0;
    //圆弧背景的开始和结束间的夹角大小
    private final float mAngle = 180;
    //当前进度夹角大小
    private float mIncludedAngle = 0;
    //圆弧的画笔的宽度
    private final float mStrokeWith = 10;

    private int margin = 20;
    //中心的文字描述
    private String mDes = "";
    //动画效果的数据及最大/小值
    private int mAnimatorValue, mMinValue, mMaxValue;
    //中心点的XY坐标
    private float centerX, centerY;
    private Paint.FontMetrics fontMetrics;
    private int radiusDial;
    private float mRealRadius;
    private RectF mRect;
    private int mOutLineColor = 0; //外线颜色

    public CircularProgressView(Context context) {
        this(context, null);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();
        if(childHeightSize >= childWidthSize){
            //高度和宽度一样
            heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        }else {
            //高度和宽度一样
            heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        radiusDial = Math.min((getMeasuredWidth() - getPaddingLeft() - getPaddingRight()),
                (getMeasuredHeight() - getPaddingTop() - getPaddingBottom())) / 2;
        mRealRadius = radiusDial - mStrokeWith / 2;//真实的半径
        Log.e("=======","mRealRadius = " + mRealRadius);
        mRect = new RectF(-mRealRadius + 50, -mRealRadius+50, mRealRadius-50, mRealRadius-50);
    }

    private void initPaint() {
        //圆弧的paint
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(Color.parseColor("#666666"));
        //设置透明度(数值为0-255)
        mArcPaint.setAlpha(100);
        mOutLineColor = getResources().getColor(R.color.green);
        //设置画笔的画出的形状
        //mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        //mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        //设置画笔类型
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(dp2px(mStrokeWith));
        //中心文字的paint
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.parseColor("#FF4A40"));
        //设置文本的对齐方式
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        //mTextPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.dp_12));
        mTextPaint.setTextSize(dp2px(25));
        fontMetrics = mTextPaint.getFontMetrics();//获得字体度量
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        margin = getWidth()/10;
        //初始化paint
        initPaint();
        //绘制弧度
        drawArc(canvas);
        //画指针线
        drawPointerLine(canvas);
        //绘制文本
        drawText(canvas);
    }

    /**
     * 绘制文本
     *
     * @param canvas
     */

    private void drawText(Canvas canvas) {
        mTextPaint.setColor(mOutLineColor);
        mTextPaint.setTextSize(dp2px(centerX/4));
        canvas.rotate( -36f);       //恢复坐标系为起始中心位置
        canvas.drawText(String.valueOf(mAnimatorValue), 0, -radiusDial / 3, mTextPaint);
        mTextPaint.setColor(getResources().getColor(R.color.black));
        mTextPaint.setTextSize(dp2px(centerX/10));
        canvas.drawText(mDes,0, 0, mTextPaint);
    }
    //画数字标度
    private void drawPointerLine(Canvas canvas){
        mTextPaint.setColor(getResources().getColor(R.color.black));
        //画布旋转
        canvas.rotate(180);
        for (int i=0; i< 6; i++){     //一共需要绘制101个表针
            drawPointerText(canvas, i);
            canvas.rotate(36f);
        }
    }

    //画刻度文字
    private void drawPointerText(Canvas canvas, int i){
        canvas.save();
        int currentCenterX = (int) (radiusDial - mStrokeWith - dp2px(21) - mTextPaint.measureText(String.valueOf(i)) / 2);
        canvas.translate(currentCenterX+15, 0);
        canvas.rotate(360 - 180 - 36f * i);        //坐标系总旋转角度为360度
        int textBaseLine = (int) (0 + (fontMetrics.bottom - fontMetrics.top) /2 - fontMetrics.bottom);
        canvas.drawText(String.valueOf(i*100), 0, textBaseLine, mTextPaint);
        canvas.restore();
    }
    /**
     * 绘制当前的圆弧
     *
     * @param canvas
     */

    private void drawArc(Canvas canvas) {
        //绘制圆弧背景
        canvas.translate(getPaddingLeft() + radiusDial, getPaddingTop() + radiusDial);
        canvas.drawArc(mRect, startAngle, mAngle, false, mArcPaint);
        //绘制当前数值对应的圆弧
        mArcPaint.setColor(mOutLineColor);
        //根据当前数据绘制对应的圆弧
        canvas.drawArc(mRect, startAngle, mIncludedAngle, false, mArcPaint);
    }

    /**
     * 为绘制弧度及数据设置动画
     *
     * @param startAngle   开始的弧度
     * @param currentAngle 需要绘制的弧度
     * @param currentValue 需要绘制的数据
     * @param time         动画执行的时长
     */

    private void setAnimation(float startAngle, float currentAngle, int currentValue, int time) {

        //绘制当前数据对应的圆弧的动画效果
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(startAngle, currentAngle);
        progressAnimator.setDuration(time);
        progressAnimator.setTarget(mIncludedAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mIncludedAngle = (float) animation.getAnimatedValue();
                //重新绘制，不然不会出现效果
                postInvalidate();
            }
        });

        //开始执行动画
        progressAnimator.start();
        //中心数据的动画效果
        ValueAnimator valueAnimator = ValueAnimator.ofInt(mAnimatorValue, currentValue);
        valueAnimator.setDuration(2500);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }
    /**
     * 设置数据
     *
     * @param minValue     最小值
     * @param maxValue     最大值
     * @param currentValue 当前绘制的值
     * @param des          描述信息
     */

    public void setValues(int minValue, int maxValue, int currentValue, String des) {
        mDes = des;
        mMaxValue = maxValue;
        mMinValue = minValue;
        //完全覆盖
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
        mOutLineColor = getResources().getColor(R.color.green);
        if(currentValue > 400){
            mOutLineColor = getResources().getColor(R.color.red);
        }else if(currentValue > 300){
            mOutLineColor = getResources().getColor(R.color.yellow);
        }
        //计算弧度比重
        float scale = (float) currentValue / maxValue;
        //计算弧度
        float currentAngle = scale * mAngle;
        //开始执行动画
        setAnimation(0, currentAngle, currentValue, 2500);
    }
    public float dp2px(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return dp * metrics.density;
    }
}
