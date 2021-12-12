package com.example.alarm;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarm.utils.UIUtils;
import com.example.alarm.widget.fly.StellarMap;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐界面
 */

public class StellarActivity extends Activity {

    private ArrayList<String> mList;
    @BindView(R.id.st_rr)
    StellarMap stellarMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stellar);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("data " + i);
        }
        // 设置内部文字距边缘边距为10dip
        int padding = UIUtils.dip2px(10);
        stellarMap.setInnerPadding(padding, padding, padding, padding);
        // 设置数据源
        stellarMap.setAdapter(new RecommendAdapter());
        // 设定展示规则,9行6列(具体以随机结果为准)
        stellarMap.setRegularity(6, 9);
        // 设置默认组为第0组
        stellarMap.setGroup(0, true);

    }
    class RecommendAdapter implements StellarMap.Adapter {
        // 返回组的数量
        @Override
        public int getGroupCount() {
            return 2;
        }

        // 每组某个组下返回孩子的个数
        @Override
        public int getCount(int group) {
            int count = mList.size() / getGroupCount();// 用总数除以组个数就是每组应该展示的孩子的个数
            if (group == getGroupCount() - 1) {// 由于上一行代码不一定整除, 最后一组,将余数补上
                count += mList.size() % getGroupCount();
            }
            Log.e("=========","count = " + count);
            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            if (group > 0) {// 如果发现不是第一组,需要更新position, 要加上之前几页的总数,才是当前组的准确位置
                position = position + getCount(group - 1) * group;
            }

            TextView view = new TextView(UIUtils.getContext());
            view.setText(mList.get(position));
            final String str = mList.get(position);
            // 设置随机文字大小
            Random random = new Random();
            int size = 16 + random.nextInt(10);// 产生16-25的随机数
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);// 以sp为单位设置文字大小

            // 设置随机文字颜色
            int r = 30 + random.nextInt(210);// 产生30-239的随机颜色, 绕过0-29,
            // 240-255的值,避免颜色过暗或者过亮
            int g = 30 + random.nextInt(210);
            int b = 30 + random.nextInt(210);
            view.setTextColor(Color.rgb(r, g, b));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(StellarActivity.this,str,Toast.LENGTH_LONG).show();
                }
            });
            Log.e("=========","count = " + position);
            return view;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (!isZoomIn) {
                // 下一组
                if (group < getGroupCount() - 1) {
                    return ++group;
                } else {
                    return 0;// 如果没有下一页了,就跳到第一组
                }
            } else {
                // 上一组
                if (group > 0) {
                    return --group;
                } else {
                    return getGroupCount() - 1;// 如果没有上一页了,就跳到最后一组
                }
            }
        }
    }


}
