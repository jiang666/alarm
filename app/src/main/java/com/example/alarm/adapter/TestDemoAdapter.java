package com.example.alarm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alarm.R;
import com.example.alarm.utils.UIUtils;

import java.util.List;

public class TestDemoAdapter extends RecyclerView.Adapter {
    private TestDemoAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<String> mList;
    private Context mContent;
    private int onClickItem;

    public TestDemoAdapter(Context context, List<String> list) {
        this.mContent = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.recycleview_demo_item, parent, false);
        /*view.getLayoutParams().height = mRecyclerViewHeight;//设置单个条目高度
        RecognitionViewHolder holder = new RecognitionViewHolder(view);*/
        return new CommonDialogHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CommonDialogHolder commonDialogHolder = (CommonDialogHolder) holder;;
        final int positiona = holder.getAdapterPosition();

        if(positiona >= mList.size())return;
        String ddd = mList.get(positiona);
        if(ddd.equals("null"))return;
        if(ddd.length() > 1) ddd = phoneMask(ddd);
        commonDialogHolder.tvData.setText(ddd);
        if (onClickItem == positiona) {
            commonDialogHolder.tvData.setTextColor(Color.GREEN);
        }else {
            commonDialogHolder.tvData.setTextColor(Color.BLUE);
        }
        commonDialogHolder.tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("==========="," position = " + position + " " + "positiona = " + positiona);
                onRecyclerViewItemClickListener.onItemClick(positiona);
            }
        });
    }
    /**
     * 用户电话号码打码第二个字符*
     *
     * @return 处理完成电话号码
     */
    public static String phoneMask(String phone) {
        String res = "";
            StringBuilder stringBuilder = new StringBuilder(phone);
            res = stringBuilder.replace(1, 2, "*").toString();
        return res;
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class CommonDialogHolder extends RecyclerView.ViewHolder {
        private TextView tvData;
        private LinearLayout ll_aaaaa;
        public CommonDialogHolder(View itemView) {
            super(itemView);
            tvData  = (TextView) itemView.findViewById(R.id.tv_data);
            ll_aaaaa  = (LinearLayout) itemView.findViewById(R.id.ll_aaaaa);
        }
    }

    public interface onRecyclerViewItemClickListener{
        void onItemClick(int position);
    }
    //定义一个公用方法来实例化自定义接口
    public void setOnItemClickListener(TestDemoAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnItem(int onClickItem) {
        this.onClickItem = onClickItem;
    }

    //更新数据
    public void updateData(List<String> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

}
