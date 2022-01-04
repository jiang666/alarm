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

    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val name = "Kotlin"
        print("Hello $name!")
        val ss = arrayOf(2,3,4)
        print("arrayOf ${ss[1]}")
        for (i in ss){
            print("arrayOf $i")
        }
    }
}