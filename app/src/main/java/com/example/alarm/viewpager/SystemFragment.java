package com.example.alarm.viewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.alarm.BaseApplication;
import com.example.alarm.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * 系统配置
 */
public class SystemFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    @BindView(R.id.shl)
    StickyListHeadersListView shl;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.fragment_goods_tv_count)
    TextView fragmentGoodsTvCount;
    @BindView(R.id.cart)
    RelativeLayout cart;
    private View mRootView;
    private Unbinder unbinder;
    private MyGroupAdapter groupAdater;
    private MyHeardAdapter heardAdapter;


    public SystemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SystemFragment.
     */
    public static SystemFragment newInstance(String cardnum1) {
        SystemFragment fragment = new SystemFragment();
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
        mRootView = inflater.inflate(R.layout.system_set_layout, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        testData();
        groupAdater = new MyGroupAdapter();
        shl.setAdapter(groupAdater);
        heardAdapter = new MyHeardAdapter();
        lv.setAdapter(heardAdapter);
        shl.setOnScrollListener(this);
        lv.setOnItemClickListener(this);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * 普通条目
     */
    class Data{
        String info;
        int hearID;
        int heardIndex;
    }
    /**
     * 头条目
     */
    class Heard{
        String info;
        int groupFirstindex;
    }
    private ArrayList<Data> dataArrayList = new ArrayList<>();
    private ArrayList<Heard> heards = new ArrayList<>();
    private void testData(){
        for (int i = 0; i < 10; i++) {
            Heard data = new Heard();
            data.info = "头条目 " + i;
            heards.add(data);
        }
        for (int i = 0; i < heards.size(); i++) {
            heards.get(i).groupFirstindex = dataArrayList.size();
            for (int j = 0; j < 10; j++) {
                Data data = new Data();
                data.info = "普通条目 第" + i + "组"+ j;
                data.hearID = i;//任意值
                data.heardIndex = i;
                dataArrayList.add(data);
            }
        }

    }
    /**
     * 分组的适配器处理
     *
     *BaseAdapter 处理普通
     * StickyListHeadersAdapter 处理分组头信息
     */

    class MyGroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            Data data = dataArrayList.get(position);
            //data.heardIndex 头条目角标
            Heard heard = heards.get(data.heardIndex);
            TextView textView = new TextView(BaseApplication.getContext());
            textView.setText(heard.info);
            textView.setBackgroundColor(Color.WHITE);
            return textView;
        }

        @Override
        public long getHeaderId(int position) {
            return dataArrayList.get(position).hearID;
        }
        ///////////////////普通条目///////////////////////////

        @Override
        public int getCount() {
            return dataArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(BaseApplication.getContext());
            textView.setText(dataArrayList.get(position).info);
            return textView;
        }
    }

    class MyHeardAdapter extends BaseAdapter{
        private int selectedHeadIndex;
        @Override
        public int getCount() {
            return heards.size();
        }

        @Override
        public Object getItem(int position) {
            return heards.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        public void setSelectedPositon(int index) {
            selectedHeadIndex = index;
            notifyDataSetChanged();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(BaseApplication.getContext());
            textView.setText(heards.get(position).info);
            if(selectedHeadIndex == position)
                textView.setTextColor(Color.RED);
            return textView;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        System.out.println("scrollState:" + scrollState);
        // 用户在滚动
        isScroll = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 用户的滚动
        if (isScroll) {
            System.out.println("firstVisibleItem:" + firstVisibleItem);

            Data data = dataArrayList.get(firstVisibleItem);
            System.out.println("data.headIndex:" + data.heardIndex);
            // 当前正在置顶显示的头高亮处理
            heardAdapter.setSelectedPositon(data.heardIndex);

            // 判断头容器对应的条目是否处于可见状态
            // 获取到第一个可见，和最后一个可见的。比第一个小的，或者比最后一个大的均为不可见
            int firstVisiblePosition = lv.getFirstVisiblePosition();
            int lastVisiblePosition = lv.getLastVisiblePosition();
            if (data.heardIndex <= firstVisiblePosition || data.heardIndex >= lastVisiblePosition) {
                lv.setSelection(data.heardIndex);// 可见处理
            }
        }
    }
    private boolean isScroll = false;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        heardAdapter.setSelectedPositon(position);
        Heard head = heards.get(position);
        shl.setSelection(head.groupFirstindex);
        isScroll = false;
    }
}
