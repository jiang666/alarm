package com.example.alarm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class TestAdapter extends RecyclerView.Adapter {
    private TestAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<String> mList;
    private Context mContent;

    public TestAdapter(Context context, List<String> list) {
        this.mContent = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.recycleview_item, parent, false);
        return new CommonDialogHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CommonDialogHolder commonDialogHolder = (CommonDialogHolder) holder;
        commonDialogHolder.tvData.setText(mList.get(position));
        commonDialogHolder.tvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecyclerViewItemClickListener.onItemClick(position);
            }
        });
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
    public void setOnItemClickListener(TestAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
