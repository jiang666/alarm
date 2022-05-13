package com.example.alarm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alarm.R;

import java.util.List;

public class RefreshTestAdapter extends RecyclerView.Adapter {
    private RefreshTestAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<String> mList;
    private Context mContent;
    private int ooooo;

    public RefreshTestAdapter(Context context, List<String> list) {
        this.mContent = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.recycleview_item, parent, false);
        return new CommonDialogHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CommonDialogHolder commonDialogHolder = (CommonDialogHolder) holder;;

        //GridLayoutManager layoutManager = new GridLayoutManager(this, 4);设置为4时 S型数据
        // 6->11   11->6

        //final int positiona;
        //S形数据
        /*if(position/4%2 == 1){
            positiona = (position/4+1)*4-position%4-1;
        }else {
            positiona = holder.getAdapterPosition();
        }*/
        //正常数据
        //positiona = holder.getAdapterPosition();
        if(position >= mList.size())return;
        String ddd = mList.get(position);
        if(ddd.length() > 1) ddd = phoneMask(ddd);
        commonDialogHolder.tvData.setText(ddd);
        if (ooooo == position) {
            commonDialogHolder.tvData.setTextColor(Color.GREEN);
        }else {
            commonDialogHolder.tvData.setTextColor(Color.BLUE);
        }
        commonDialogHolder.tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(position);
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
        public CommonDialogHolder(View itemView) {
            super(itemView);
            tvData  = (TextView) itemView.findViewById(R.id.tv_data);
        }
    }

    public interface onRecyclerViewItemClickListener{
        void onItemClick(int position);
    }
    //定义一个公用方法来实例化自定义接口
    public void setOnItemClickListener(RefreshTestAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnItem(int ooooo) {
        this.ooooo = ooooo;
    }
}
