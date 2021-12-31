package com.example.alarm

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.app.Activity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.*

/**
 * 属性动画 文本内容收缩和展开  转kt
 */
class KotlinActivity : Activity() {
    private var tvDes: TextView? = null
    private var tvAuthor: TextView? = null
    private var ivArrow: ImageView? = null
    private var rlToggle: RelativeLayout? = null
    private var isOpen = false
    private var mParams: LinearLayout.LayoutParams? = null
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        tvDes = findViewById<View>(R.id.tv_detail_des) as TextView
        tvAuthor = findViewById<View>(R.id.tv_detail_author) as TextView
        ivArrow = findViewById<View>(R.id.iv_arrow) as ImageView
        rlToggle = findViewById<View>(R.id.rl_detail_toggle) as RelativeLayout
        rlToggle!!.setOnClickListener { toggle() }
        refreshView()
    }

    fun refreshView() {
        tvDes!!.text = longStr
        tvAuthor!!.text = "111111111"

        // 放在消息队列中运行, 解决当只有三行描述时也是7行高度的bug
        tvDes!!.post { // 默认展示7行的高度
            val shortHeight = shortHeight
            mParams = tvDes!!.layoutParams as LinearLayout.LayoutParams
            mParams!!.height = shortHeight
            tvDes!!.layoutParams = mParams
        }
    }

    protected fun toggle() {
        val shortHeight = shortHeight
        val longHeight = longHeight
        var animator: ValueAnimator? = null
        if (isOpen) {
            // 关闭
            isOpen = false
            if (longHeight > shortHeight) { // 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(longHeight, shortHeight)
            }
        } else {
            // 打开
            isOpen = true
            if (longHeight > shortHeight) { // 只有描述信息大于7行,才启动动画
                animator = ValueAnimator.ofInt(shortHeight, longHeight)
            }
        }
        if (animator != null) { // 只有描述信息大于7行,才启动动画
            animator.addUpdateListener(AnimatorUpdateListener { arg0 ->
                val height = arg0.animatedValue as Int
                mParams!!.height = height
                tvDes!!.layoutParams = mParams
            })
            animator.addListener(object : AnimatorListener {
                override fun onAnimationStart(arg0: Animator) {}
                override fun onAnimationRepeat(arg0: Animator) {}
                override fun onAnimationEnd(arg0: Animator) {
                    // ScrollView要滑动到最底部
                    val scrollView = scrollView

                    // 为了运行更加安全和稳定, 可以讲滑动到底部方法放在消息队列中执行
                    scrollView.post {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN) // 滚动到底部
                    }
                    if (isOpen) {
                        ivArrow!!.setImageResource(R.drawable.arrow_up)
                    } else {
                        ivArrow!!.setImageResource(R.drawable.arrow_down)
                    }
                }

                override fun onAnimationCancel(arg0: Animator) {}
            })
            animator.duration = 200
            animator.start()
        }
    }// 高度包裹内容, wrap_content;当包裹内容时,
    // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

    // 开始测量
    // 返回测量后的高度
// 宽不变, 确定值, match_parent// 宽度
    // 设置文字
    // 文字大小一致
    // 最大行数为7行
// 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高

    /**
     * 获取7行textview的高度
     */
    private val shortHeight: Int
        private get() {
            // 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
            val width = tvDes!!.measuredWidth // 宽度
            val view = TextView(this)
            view.text = longStr // 设置文字
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f) // 文字大小一致
            view.maxLines = 7 // 最大行数为7行
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                    View.MeasureSpec.EXACTLY) // 宽不变, 确定值, match_parent
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                    View.MeasureSpec.AT_MOST) // 高度包裹内容, wrap_content;当包裹内容时,
            // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

            // 开始测量
            view.measure(widthMeasureSpec, heightMeasureSpec)
            return view.measuredHeight // 返回测量后的高度
        }// 高度包裹内容, wrap_content;当包裹内容时,
    // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

    // 开始测量
    // 返回测量后的高度
// 宽不变, 确定值, match_parent// 宽度
    // 设置文字
    // 文字大小一致
    // view.setMaxLines(7);// 最大行数为7行
// 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高

    /**
     * 获取完整textview的高度
     */
    private val longHeight: Int
        private get() {
            // 模拟一个textview,设置最大行数为7行, 计算该虚拟textview的高度, 从而知道tvDes在展示7行时应该多高
            val width = tvDes!!.measuredWidth // 宽度
            val view = TextView(this)
            view.text = longStr // 设置文字
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f) // 文字大小一致
            // view.setMaxLines(7);// 最大行数为7行
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(width,
                    View.MeasureSpec.EXACTLY) // 宽不变, 确定值, match_parent
            val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(2000,
                    View.MeasureSpec.AT_MOST) // 高度包裹内容, wrap_content;当包裹内容时,
            // 参1表示尺寸最大值,暂写2000, 也可以是屏幕高度

            // 开始测量
            view.measure(widthMeasureSpec, heightMeasureSpec)
            return view.measuredHeight // 返回测量后的高度
        }

    // 获取ScrollView, 一层一层往上找,
    // 知道找到ScrollView后才返回;注意:一定要保证父控件或祖宗控件有ScrollView,否则死循环
    private val scrollView: ScrollView
        private get() {
            var parent = tvDes!!.parent
            while (parent !is ScrollView) {
                parent = parent.parent
            }
            min(10, 30)
            min("mark", "aa")
            val dd = ""
            return parent
        }

    companion object {
        private const val shortStr = "12月1日上午，上海市消保委就消费者所关心的加拿大鹅专门店的《更换条款》的公平性合理性问题，专门约谈了加拿大鹅公司。"
        private const val longStr = "12月1日上午，上海市消保委就消费者所关心的加拿大鹅专门店的《更换条款》的公平性合理性问题，专门约谈了加拿大鹅公司。\n" +
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
                "            \"据记者了解，12月2日下午2点，加拿大鹅公司派一人到上海消保委递交了材料"

        fun <T : Comparable<*>?> min(a: T, b: T): T {
            return if (a!!.compareTo(b as Nothing) > 0) a else b
        }
    }
}