package com.example.alarm;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * anim
 */

public class AnimationActivity extends AppCompatActivity {


    @BindView(R.id.main_edit)
    TextView mainEdit;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.iv_arrow_two)
    ImageView ivArrowTwo;
    private Map pagerMap = new ArrayMap();
    private Animation animation1 = null;
    private boolean iclick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);

        mainEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*//缩放---ofFloat用4个参数的ofFloat
                PropertyValuesHolder scaleXProper = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
                PropertyValuesHolder scaleYProper = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
                ValueAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(ivArrow, scaleXProper, scaleYProper);
                //ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ivArrow, "scaleX", 1, 2);
                ivArrow.setPivotX(0);
                ivArrow.setPivotY(0);
                objectAnimator1.setDuration(1000);
                objectAnimator1.start();*/
                //startScaleAnimation();
                if(iclick){
                    //scaleAnimation();
                }else {
                    //scaleBigAnimation();
                }
                ivArrow.measure(0, 0);
                int textWidth = ivArrow.getMeasuredWidth();
                Log.e("======"," textWidth " + textWidth);

            }
        });
        /**
         * 按下缩小 松开放大
         */
        /*LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainEdit.getLayoutParams();
        mainEdit.setLayoutParams(params);*/
        mainEdit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN://按下
                        //MyLogger.i(TAG,"按下");
                        oneSignalscaleAnimation();
                        Log.e("========",motionEvent.getDevice().getName() + " 按下 ");
                        break;
                    case MotionEvent.ACTION_MOVE://移动
                        Log.e("========","移动");
                        break;
                    case MotionEvent.ACTION_UP://弹起
                        oneSignalscaleBigAnimation();
                        Log.e("========","弹起");
                        break;
                    case MotionEvent.ACTION_CANCEL://事件取消
                        Log.e("========","事件取消");
                        break;
                    case MotionEvent.ACTION_OUTSIDE://外部点击
                        //Weida Hi-Tech                CoolTouchR System           外部点击 ---触摸屏
                        //PixA琀 USB Optical Mouse外部点击 ---鼠标
                        Log.e("========",motionEvent.getDevice().getName() + "外部点击");
                        break;
                }
                return false;
            }
        });
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("=======","ivArrow click");
                //rotateAnimation();
                translateAnimation();
                //scaleAnimation();
                //alphaAnimation();
                //startAttributeScaleAnimation();
                //startShakeByPropertyAnim(ivArrowTwo,2,4,3,1000);
            }
        });
        String name = "000-0000";
        name = name.split("-")[0];
        pagerMap.put(name, 000000000);

        String nnn = "000000";
        nnn = nnn.split("-")[0];
        pagerMap.put(nnn, 1111111);

        Log.e("=====", " name " + name + " nnn = " + nnn);
    }

    private void startAttributeScaleAnimation() {
        Log.e("=======","startAttributeScaleAnimation");
        animation1= AnimationUtils.loadAnimation(this, R.anim.values);
        ivArrowTwo.startAnimation(animation1);
    }

    public void startScaleAnimation() {
        /**
         * ScaleAnimation第一种构造
         *
         * @param fromX X方向开始时的宽度，1f表示控件原有大小
         * @param toX X方向结束时的宽度，
         * @param fromY Y方向上开的宽度，
         * @param toY Y方向结束的宽度
         * 这里还有一个问题：缩放的中心在哪里？ 使用这种构造方法，默认是左上角的位置，以左上角为中心开始缩放
         */
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f);
       /* *
         * ScaleAnimation第二种构造解决了第一种构造的缺陷， 无法指定缩放的位置
         *
         * @param fromX 同上
         * @param toX 同上
         * @param fromY 同上
         * @param toY 同上
         * @param pivotX 缩放的轴心X的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是mIvScale.getWidth() / 2f
         * @param pivotY 缩放的轴心Y的位置，取值类型是float，单位是px像素，比如：X方向控件中心位置是mIvScale.getHeight() / 2f*/

        ScaleAnimation scaleAnimation1 = new ScaleAnimation(1f, 2f, 1f, 2f, ivArrow.getWidth() / 2f, ivArrow.getHeight() / 2f);

       /* *
         * ScaleAnimation第三种构造在第二种构造的基础上，可以通过多种方式指定轴心的位置，通过Type来约束
         *
         * @param fromX 同上
         * @param toX 同上
         * @param fromY 同上T
         * @param toY 同上
         * @param pivotXType 用来约束pivotXValue的取值。取值有三种：Animation.ABSOLUTE，Animation.RELATIVE_TO_SELF，Animation.RELATIVE_TO_PARENT
         * Type：Animation.ABSOLUTE：绝对，如果设置这种类型，后面pivotXValue取值就必须是像素点；比如：控件X方向上的中心点，pivotXValue的取值mIvScale.getWidth() / 2f
         *            Animation.RELATIVE_TO_SELF：相对于控件自己，设置这种类型，后面pivotXValue取值就会去拿这个取值是乘上控件本身的宽度；比如：控件X方向上的中心点，pivotXValue的取值0.5f
         *            Animation.RELATIVE_TO_PARENT：相对于它父容器（这个父容器是指包括这个这个做动画控件的外一层控件）， 原理同上，
         * @param pivotXValue  配合pivotXType使用，原理在上面
         * @param pivotYType 原理同上
         * @param pivotYValue 原理同上*/

        //ScaleAnimation scaleAnimation2 = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.ABSOLUTE, ivArrow.getWidth() / 2f, ScaleAnimation.ABSOLUTE, ivArrow.getHeight() / 2f);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.RELATIVE_TO_SELF, 0, ScaleAnimation.RELATIVE_TO_SELF, 0);
        //设置动画持续时长
        scaleAnimation2.setDuration(3000);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        //scaleAnimation2.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        //scaleAnimation2.setFillBefore(true);
        scaleAnimation2.setInterpolator(new AccelerateInterpolator());
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        scaleAnimation2.setRepeatMode(ScaleAnimation.REVERSE);
        //设置动画播放次数
        //scaleAnimation2.setRepeatCount(ScaleAnimation.INFINITE);
        //开始动画
        ivArrowTwo.startAnimation(scaleAnimation2);
        //清除动画
        //ivArrow.clearAnimation();
        //同样cancel（）也能取消掉动画
        //scaleAnimation2.cancel();

        /*AnimationSet animationSet = new AnimationSet(false);
        Animation alphAnimation = new AlphaAnimation(1.0f, 0.0f);
        //ScaleAnimationscaleAnimation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(2000);
        alphAnimation.setDuration(2000);
        alphAnimation.setStartOffset(1000);
        animationSet.addAnimation(alphAnimation);
        animationSet.addAnimation(scaleAnimation);
        ivArrowTwo.startAnimation(animationSet);*/

    }
    private void startShakeByPropertyAnim(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        if (view == null) {
            return;
        }
        //TODO 验证参数的有效性

        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
    /*mCameralayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            if(isMove){
                isMove = false;
                int[] location = new int[2];

                // getLocationInWindow方法要在onWindowFocusChanged方法里面调用
                // 个人理解是onCreate时，View尚未被绘制，因此无法获得具体的坐标点
                mCameralayout.getLocationInWindow(location);

                // 模拟的mPreviewView的左右上下坐标坐标
                int left = mCameralayout.getLeft();
                int right = mCameralayout.getRight();
                int top = mCameralayout.getTop();
                int bottom = mCameralayout.getBottom();

                // 从上到下的平移动画
                verticalAnimation = new TranslateAnimation(left, left, top+80, bottom-100);
                verticalAnimation.setDuration(3000); // 动画持续时间
                verticalAnimation.setRepeatCount(Animation.INFINITE); // 无限循环

                // 播放动画
                mScanHorizontalLineImageView.setAnimation(verticalAnimation);
                verticalAnimation.startNow();
            }
        }
    });*/
    //旋转
    private void rotateAnimation(){
        Animation animation = new RotateAnimation(0, 359);
        animation.setDuration(500);
        animation.setRepeatCount(8);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrowTwo.startAnimation(animation);//開始动画
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //动画开始
                Log.e("======","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束
                Log.e("======","onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //动画重复
                Log.e("======","onAnimationRepeat");
            }
        });
    }
    //缩小
    private void scaleAnimation(){
/*<<<<<<< HEAD
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatCount(8);//动画的反复次数
        //scaleAnimation.setRepeatCount(Animation.INFINITE);//无限次重复
=======*/
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f);
        scaleAnimation.setDuration(5000);
        scaleAnimation.setRepeatCount(0);//动画的反复次数
        scaleAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrowTwo.startAnimation(scaleAnimation);//開始动画
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onescaleBigAnimation();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scaleBigAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    //放大
    private void scaleBigAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f);
        scaleAnimation.setDuration(5000);
        scaleAnimation.setRepeatCount(0);//动画的反复次数
        scaleAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrowTwo.startAnimation(scaleAnimation);//開始动画
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onescaleAnimation();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                scaleAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    //放大 添加基于图片中心放大
    private void oneSignalscaleBigAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1f, 0.8f, 1f,ivArrow.getWidth()/2f,ivArrow.getHeight()/2f);
        scaleAnimation.setDuration(5000);
        scaleAnimation.setRepeatCount(0);//动画的反复次数
        scaleAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrow.startAnimation(scaleAnimation);//開始动画
    }

    //放大
    private void onescaleBigAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f);
        scaleAnimation.setDuration(5000);
        scaleAnimation.setRepeatCount(0);//动画的反复次数
        scaleAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrow.startAnimation(scaleAnimation);//開始动画
    }
    //缩小
    private void onescaleAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0f, 1f, 0f);
        scaleAnimation.setDuration(5000);
        scaleAnimation.setRepeatCount(0);//动画的反复次数
        scaleAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrow.startAnimation(scaleAnimation);//開始动画
    }
    //缩小 添加基于图片中心缩小
    private void oneSignalscaleAnimation(){
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.8f, 1f, 0.8f,ivArrow.getWidth()/2f,ivArrow.getHeight()/2f);
        scaleAnimation.setDuration(5000);
        scaleAnimation.setRepeatCount(0);//动画的反复次数
        scaleAnimation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrow.startAnimation(scaleAnimation);//開始动画
    }
    //透明度
    private void alphaAnimation(){
        Animation animation = new AlphaAnimation(1f,0.5f);
        animation.setDuration(500);
        animation.setRepeatCount(8);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        ivArrowTwo.startAnimation(animation);//開始动画
    }
    //移动
    private void translateAnimation(){
        Animation animation = new TranslateAnimation(1,10,1,1);
        animation.setDuration(5000);
        animation.setRepeatCount(3);//动画的反复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用

//        Interpolator interpolator = new LinearInterpolator();//匀速
//        Interpolator interpolator = new AccelerateInterpolator();//先慢后快
//        Interpolator interpolator = new AnticipateInterpolator();//开始回弹效果
//        Interpolator interpolator = new BounceInterpolator();//结束回弹效果
//        Interpolator interpolator = new CycleInterpolator(2);//跳一跳效果
//        Interpolator interpolator = new OvershootInterpolator(1);//动画结束时向前弹一定距离再回到原来位置
//        Interpolator interpolator = new AccelerateDecelerateInterpolator();//系统默认的动画效果，先加速后减速
//        Interpolator interpolator = new AnticipateOvershootInterpolator();//开始之前向前甩，结束的时候向后甩
        Interpolator interpolator = new DecelerateInterpolator();//开始加速再减速
        animation.setInterpolator(interpolator);
        ivArrowTwo.startAnimation(animation);//開始动画
    }
}
