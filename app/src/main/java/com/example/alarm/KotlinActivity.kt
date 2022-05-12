package com.example.alarm
import android.app.Activity
import android.content.res.AssetManager
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.alarm.bean.Person

/**
 * 属性动画 文本内容收缩和展开  转kt
 */
class KotlinActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val detail = findViewById<TextView>(R.id.tv_detail_des);
        val name = "Kotlin"
        val mgr: AssetManager = getAssets()
        var tf = Typeface.createFromAsset(mgr, "front/lishu.ttf")
        detail.setTypeface(tf)
        print("Hello $name!")
        val ss = arrayOf(2,3,4)
        print("arrayOf ${ss[1]}")
        for (i in ss){
            print("arrayOf $i")
        }

        val s = """
        当我们的字符串有复杂的格式时
        原始字符串非常的方便
        因为它可以做到所见即所得。 """
        detail.text = s
        createUser(name = "333",age = 3,gender = 6)

        val inttest = 1
        when(inttest){
            1-> Log.e("=========","一")
            2-> print("二")
            else -> print("else")
        }

        val array = arrayOf(1,2,3)
        for (i in array){
            print(i)
        }
        val person = Person("leo")
        person.name
        Log.e("======","name = ${person.name} age = ${person.age}")
        person.age = 6
        Log.e("======","age = ${person.age}")

    }

    fun createUser(
            name: String,
            age: Int,
            gender: Int = 1,
            friendCount: Int = 0,
            feedCount: Int = 0,
            likeCount: Long = 0L,
            commentCount: Int = 0
    ) {
        //..
    }
    fun main(){
        Log.e("======","age = 90")
    }

}