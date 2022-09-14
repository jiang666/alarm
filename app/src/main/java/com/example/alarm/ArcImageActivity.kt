package com.example.alarm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

/**
 * 图形切割Activity
 */
class ArcImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arcimage)
        Log.e(TAG,"========")
    }

    companion object {
        private val TAG = ArcImageActivity::class.java.simpleName
    }
}