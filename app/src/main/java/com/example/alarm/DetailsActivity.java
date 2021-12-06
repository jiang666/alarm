package com.example.alarm;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 属性动画 文本内容收缩和展开
 */

public class DetailsActivity extends Activity {

    private TextView tvDes;
    private TextView tvAuthor;
    private ImageView ivArrow;
    private RelativeLayout rlToggle;
    private boolean isOpen = false;
    private LinearLayout.LayoutParams mParams;
    private static final String shortStr = "12月1日上午，上海市消保委就消费者所关心的加拿大鹅专门店的《更换条款》的公平性合理性问题，专门约谈了加拿大鹅公司。";
    private static final String longStr = "12月1日上午，上海市消保委就消费者所关心的加拿大鹅专门店的《更换条款》的公平性合理性问题，专门约谈了加拿大鹅公司。\n" +
            "\n" +
            "12月1日下午，上海市消保委副秘书长唐健盛接受央视财经记者专访，认为从约谈情况来看，加拿大鹅公司对于《更换条款》的具体的含义，包括跟实际操作的相匹配度并不是非常明确，因此上海市消保委要求加拿大鹅公司就相关条款及问题，在12月2日中午以前要给到上海市消保委，给消费者一个明确说明。\n" +
            "\n" +
            "据记者了解，12月2日下午2点，加拿大鹅公司派一人到上海消保委递交了材料" +
            "12月1日上午，上海市消保委就消费者所关心的加拿大鹅专门店的《更换条款》的公平性合理性问题，专门约谈了加拿大鹅公司。\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"12月1日下午，上海市消保委副秘书长唐健盛接受央视财经记者专访，认为从约谈情况来看，加拿大鹅公司对于《更换条款》的具体的含义，包括跟实际操作的相匹配度并不是非常明确，因此上海市消保委要求加拿大鹅公司就相关条款及问题，在12月2日中午以前要给到上海市消保委，给消费者一个明确说明。\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"据记者了解，12月2日下午2点，加拿大鹅公司派一人到上海消保委递交了材料" +
            "12月1日上午，上海市消保委就消费者所关心的加拿大鹅专门店的《更换条款》的公平性合理性问题，专门约谈了加拿大鹅公司。\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"12月1日下午，上海市消保委副秘书长唐健盛接受央视财经记者专访，认为从约谈情况来看，加拿大鹅公司对于《更换条款》的具体的含义，包括跟实际操作的相匹配度并不是非常明确，因此上海市消保委要求加拿大鹅公司就相关条款及问题，在12月2日中午以前要给到上海市消保委，给消费者一个明确说明。\\n\" +\n" +
            "            \"\\n\" +\n" +
            "            \"据记者了解，12月2日下午2点，加拿大鹅公司派一人到上海消保委递交了材料";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvDes = (TextView) findViewById(R.id.tv_detail_des);
        tvAuthor = (TextView) findViewById(R.id.tv_detail_author);
        ivArrow = (ImageView) findViewById(R.id.iv_arrow);
        rlToggle = (RelativeLayout) findViewById(R.id.rl_detail_toggle);

        rlToggle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        refreshView();
    }
    public void refreshView() {
        tvDes.setText(longStr);
        tvAuthor.setText("111111111");

        // 放在消息队列中运行, 解决当只有三行描述时也是7行高度的bug
        tvDes.post(new Runnable() {

            @Override
            public void run() {
                // 默认展示7行的高度
                int shortHeight = getShortHeight();
                mParams = (LinearLayout.LayoutParams) tvDes.getLayoutParams();
                mParams.height = shortHeight;

                tvDes.setLayoutParams(mParams);
            }
        });
    }
    protected void toggle() {
        int shortHeight = getShortHeight();
        int longHeight = getLongHeight();

        ValueAnimator animator = null;
        if (isOpen) {
            // 关闭
            isOpen = false;
            if (longHeight > shortHeight) {// 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight);
            }
        } else {
            // 打开
            isOpen = true;
            if (longHeight > shortHeight) {// 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(shortHeight, longHeight);
            }
        }

        if (animator != null) {// 只有描述信息大于7行,才启动动画
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator arg0) {
                    Integer height = (Integer) arg0.getAnimatedValue();
                    mParams.height = height;
                    tvDes.setLayoutParams(mParams);
                }

            });

            animator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator arg0) {

                }

                @Override
                public void onAnimationRepeat(Animator arg0) {

                }

                @Override
                public void onAnimationEnd(Animator arg0) {
                    // ScrollView要滑动到最底部
                    final ScrollView scrollView = getScrollView();

                    // 为了运行更加安全和稳定, 可以讲滑动到底部方法放在消息队列中执行
                    scrollView.post(new Runnable() {

                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);// 滚动到底部
                        }
                    });

                    if (isOpen) {
                        ivArrow.setImageResource(R.drawable.arrow_up);
                    } else {
                        ivArrow.setImageResource(R.drawable.arrow_down);
                    }

                }

                @Override
                public void onAnimationCancel(Animator arg0) {

                }
            });

            animator.setDuration(200);
            animator.start();
        }
    }

    /**
     * 获取7行textview的高度
     */
    private int getShortHeight() {
        // 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
        int width = tvDes.getMeasuredWidth();// 宽度

        TextView view = new TextView(this);
        view.setText(longStr);// 设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);// 文字大小一致
        view.setMaxLines(7);// 最大行数为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);// 高度包裹内容, wrap_content;当包裹内容时,
        // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

        // 开始测量
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();// 返回测量后的高度
    }

    /**
     * 获取完整textview的高度
     */
    private int getLongHeight() {
        // 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
        int width = tvDes.getMeasuredWidth();// 宽度

        TextView view = new TextView(this);
        view.setText(longStr);// 设置文字
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);// 文字大小一致
        // view.setMaxLines(7);// 最大行数为7行

        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                View.MeasureSpec.EXACTLY);// 宽不变, 确定值, match_parent
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                View.MeasureSpec.AT_MOST);// 高度包裹内容, wrap_content;当包裹内容时,
        // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

        // 开始测量
        view.measure(widthMeasureSpec, heightMeasureSpec);
        return view.getMeasuredHeight();// 返回测量后的高度
    }

    // 获取ScrollView, 一层一层往上找,
    // 知道找到ScrollView后才返回;注意:一定要保证父控件或祖宗控件有ScrollView,否则死循环
    private ScrollView getScrollView() {
        ViewParent parent = tvDes.getParent();

        while (!(parent instanceof ScrollView)) {
            parent = parent.getParent();
        }

        return (ScrollView) parent;
    }

}
