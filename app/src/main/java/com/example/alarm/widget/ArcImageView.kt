package com.example.alarm.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Path
import android.support.annotation.Nullable
import android.util.AttributeSet
import com.example.alarm.R

/**
 * https://blog.csdn.net/mq2856992713/article/details/78635790?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.edu_weight&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.edu_weight
 */
class ArcImageView(context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int) : android.support.v7.widget.AppCompatImageView(context, attrs, defStyleAttr) {
    /*
     *弧形高度
     */
    private val mArcHeight: Int

    constructor(context: Context) : this(context, null) {}
    constructor(context: Context, @Nullable attrs: AttributeSet?) : this(context, attrs, 0) {}

    override fun onDraw(canvas: Canvas) {
        val path = Path()
        path.moveTo(0F, 0F)
        path.lineTo(0F, height.toFloat())
        path.quadTo((width / 2).toFloat(), (height - 2 * mArcHeight).toFloat(), width.toFloat(), height.toFloat())
        path.lineTo(width.toFloat(), 0F)
        path.close()
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

    companion object {
        private const val TAG = "ArcImageView"
    }

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView)
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcHeight, 0)
    }
}