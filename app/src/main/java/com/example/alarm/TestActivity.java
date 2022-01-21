package com.example.alarm;

import android.app.PendingIntent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Toast;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.alarm.widget.LockPatterView;

/**
 * 测试Activity
 */
public class TestActivity extends AppCompatActivity {
    private PendingIntent pi;
    private static String TAG = TestActivity.class.getSimpleName();

    private TextView pwdTv_;
    private LockPatterView mLockPatterView;
    private SharedPreferences sharedPreferences;
    private String pwdStr="";
    private String setPwd="";//第一次设置的密码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        pwdTv_=(TextView)findViewById(R.id.pwd);
        mLockPatterView=(LockPatterView)findViewById(R.id.lock_patter_view);
        sharedPreferences =getSharedPreferences("wusw", Context.MODE_PRIVATE);
        pwdStr=sharedPreferences.getString("pwd","");
        if(pwdStr.equals("")){
            pwdTv_.setText("请先绘制图案密码");
        }else{
            pwdTv_.setText("请输入图案密码");
        }

        mLockPatterView.SetOnPatterChangeLister(new LockPatterView.OnPatterChangeLister() {
            @Override
            public void onPatterChange(String passwordStr) {
                if (passwordStr != null) {
                    if(pwdStr.equals("")){//设置密码
                        if(setPwd.equals("")){//第一次设置密码
                            setPwd=passwordStr;
                            pwdTv_.setText("请绘制确认密码");
                            mLockPatterView.resetPoint();
                        }else{
                            if(setPwd.equals(passwordStr)){
                                sharedPreferences.edit().putString("pwd",setPwd).commit();
                                Toast.makeText(TestActivity.this, "密码设置成功", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                setPwd="";
                                pwdTv_.setText("两次密码绘制不一致，请重新绘制");
                                mLockPatterView.resetPoint();
                            }
                        }
                    }else{//验证密码
                        if(pwdStr.equals(passwordStr)){
                            Toast.makeText(TestActivity.this, "恭喜你，密码输入正确", Toast.LENGTH_LONG).show();
                            pwdStr="";
                            mLockPatterView.resetPoint();
                            pwdTv_.setText("请先绘制图案密码");
                        }else{
                            pwdTv_.setText("密码绘制错误，请重新绘制");
                            mLockPatterView.resetPoint();
                        }
                    }

                } else {
                    pwdTv_.setText("至少5个图案");
                }
            }

            @Override
            public void onPatterStart(boolean isStart) {

            }
        });
    }


}

