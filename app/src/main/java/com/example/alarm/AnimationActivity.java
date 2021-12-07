package com.example.alarm;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * java 调用js
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);

        mainEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("=======","mainEdit click");
                //缩放---ofFloat用4个参数的ofFloat
                PropertyValuesHolder scaleXProper = PropertyValuesHolder.ofFloat("scaleX", 1f, 2f);
                PropertyValuesHolder scaleYProper = PropertyValuesHolder.ofFloat("scaleY", 1f, 2f);
                ValueAnimator objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(ivArrow, scaleXProper, scaleYProper);
                //ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(ivArrow, "scaleX", 1, 2);
                ivArrow.setPivotX(0);
                ivArrow.setPivotY(0);
                objectAnimator1.setDuration(1000);
                objectAnimator1.start();
                //startScaleAnimation();
            }
        });
        ivArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("=======","ivArrow click");
                startAttributeScaleAnimation();
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
        animation1= AnimationUtils.loadAnimation(this, R.anim.fade_in);
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
        scaleAnimation2.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        scaleAnimation2.setFillBefore(true);
        //设置动画的重复模式：反转REVERSE和重新开始RESTART
        scaleAnimation2.setRepeatMode(ScaleAnimation.REVERSE);
        //设置动画播放次数
        //scaleAnimation2.setRepeatCount(ScaleAnimation.INFINITE);
        //开始动画
        ivArrow.startAnimation(scaleAnimation2);
        //清除动画
        //ivArrow.clearAnimation();
        //同样cancel（）也能取消掉动画
        //scaleAnimation2.cancel();
    }

}
