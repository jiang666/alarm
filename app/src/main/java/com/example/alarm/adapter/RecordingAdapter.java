package com.example.alarm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alarm.R;

import java.util.Map;

/**
 * fqc,共用dialog的多选情况下的适配器
 */

public class RecordingAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Map<String, String> mData;
    private Context mContext;
    private OnCommonDialogAdapterListener mListener;

    public RecordingAdapter(Map<String, String> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sound_recyler_item, parent, false);
        return new CommonDialogHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommonDialogHolder commonDialogHolder = (CommonDialogHolder) holder;
        String name = (String) mData.keySet().toArray()[position];
        commonDialogHolder.name.setText(name + ":");
        String s = mData.get(name);
        if (s.equals("Y")) {
            commonDialogHolder.cancel.setTextColor(Color.parseColor("#000000"));
            commonDialogHolder.sure.setTextColor(Color.parseColor("#ff0000"));
        } else if (s.equals("N")) {
            commonDialogHolder.cancel.setTextColor(Color.parseColor("#ff0000"));
            commonDialogHolder.sure.setTextColor(Color.parseColor("#000000"));
        }
        commonDialogHolder.cancel.setTag(name);
        commonDialogHolder.cancel.setOnClickListener(this);
        commonDialogHolder.sure.setTag(name);
        commonDialogHolder.sure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.common_dialog_param_cancel:
                mListener.paramCancel((String) view.getTag());
                break;
            case R.id.common_dialog_param_sure:
                mListener.paramSure((String) view.getTag());
                break;
        }
    }

    public class CommonDialogHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView sure;
        private TextView cancel;

        public CommonDialogHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.common_dialog_param_name);
            sure = (TextView) itemView.findViewById(R.id.common_dialog_param_sure);
            cancel = (TextView) itemView.findViewById(R.id.common_dialog_param_cancel);
        }
    }

    public interface OnCommonDialogAdapterListener {
        void paramCancel(String name);
        void paramSure(String name);
    }

    public void setOnCommonDialogAdapterListener(OnCommonDialogAdapterListener listener) {
        this.mListener = listener;
    }


}
