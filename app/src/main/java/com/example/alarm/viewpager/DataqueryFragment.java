package com.example.alarm.viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.alarm.R;
import com.example.alarm.adapter.DateMonthAdapter;
import com.example.alarm.utils.DataUtils;

/**
 * 数据查询
 */
public class DataqueryFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = DataqueryFragment.class.getSimpleName();

    private View mRootView;
    private GridView gridView;
    private TextView frontMonthTv,nextMonthTv,ok;
    private GestureDetector gestureDetector;
    private TextView currentDateTv;
    private String date;
    private DateMonthAdapter adapter;
    final int RIGHT = 0;
    final int LEFT = 1;

    public DataqueryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SystemFragment.
     */
    public static DataqueryFragment newInstance(String cardnum1) {
        DataqueryFragment fragment = new DataqueryFragment();
        Bundle args = new Bundle();
        args.putString("cardnum", cardnum1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.pop_layout, container, false);
        gridView = (GridView) mRootView.findViewById(R.id.list);
        frontMonthTv = (TextView)mRootView.findViewById(R.id.front_month);
        frontMonthTv.setOnClickListener(this);
        nextMonthTv = (TextView)mRootView.findViewById(R.id.next_month);
        nextMonthTv.setOnClickListener(this);
        ok = (TextView)mRootView.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        gestureDetector = new GestureDetector(getContext(),onGestureListener);
        currentDateTv = (TextView)mRootView.findViewById(R.id.now_month);
        this.date = "2022-01-11" ;
        if (TextUtils.isEmpty(date)){
            this.date = DataUtils.getCurrDate("yyyy-MM-dd");
        }
        currentDateTv.setText("当前月份："+ DataUtils.formatDate(date,"yyyy-MM"));
        adapter = new DateMonthAdapter(getContext());
        adapter.setData(DataUtils.getMonth(date));
        gridView.setAdapter(adapter);
        adapter.setDateString(date);
        adapter.setSelectedPosition(DataUtils.getSelectPosition());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //if (onItemClick!=null && !TextUtils.isEmpty(adapter.getItem(position).date)){
                if (!TextUtils.isEmpty(adapter.getItem(position).date)){
                    adapter.setSelectedPosition(position);
                    currentDateTv.setText("当前月份："+DataUtils.formatDate(adapter.getItem(position).date,"yyyy-MM"));
                    date = adapter.getItem(position).date ;
                }
            }
        });
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        return mRootView;
    }
    /**
     * 手势监听是否是左右滑动
     */
    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

                    if (x > 100) {
                        doResult(RIGHT);
                    } else if (x < -100) {
                        doResult(LEFT);
                    }
                    return true;
                }
            };

    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                date = DataUtils.getSomeMonthDay(date,-1);
                adapter.setData(DataUtils.getMonth(date));
                adapter.setDateString(date);
                adapter.setSelectedPosition(DataUtils.getSelectPosition());
                currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
                Log.e("wenzihao","go right");
                break;
            case LEFT:
                date = DataUtils.getSomeMonthDay(date,+1);
                adapter.setData(DataUtils.getMonth(date));
                adapter.setDateString(date);
                adapter.setSelectedPosition(DataUtils.getSelectPosition());
                currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
                Log.e("wenzihao","go left");
                break;

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==frontMonthTv.getId()){
            date = DataUtils.getSomeMonthDay(date,-1);
            adapter.setData(DataUtils.getMonth(date));
            adapter.setDateString(date);
            adapter.setSelectedPosition(DataUtils.getSelectPosition());
            currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
        }else if (id==nextMonthTv.getId()){
            date = DataUtils.getSomeMonthDay(date,+1);
            adapter.setData(DataUtils.getMonth(date));
            adapter.setDateString(date);
            adapter.setSelectedPosition(DataUtils.getSelectPosition());
            currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
        }else if (id==ok.getId()){
            if (onItemClick!=null){
                onItemClick.onItemClick(date);
            }
        }
    }
    /**
     * 点击回调接口
     */
    public interface OnItemClick{
        void onItemClick(String date);
    }
    public OnItemClick onItemClick ;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
