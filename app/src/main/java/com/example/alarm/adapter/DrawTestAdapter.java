package com.example.alarm.adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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

public class DrawTestAdapter extends RecyclerView.Adapter {
    private DrawTestAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener;
    private List<String> mList;
    private Context mContent;
    private int onClickItem;
    private int rowSize =6;

    public DrawTestAdapter(Context context, List<String> list) {
        this.mContent = context;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContent).inflate(R.layout.recycleview_item, parent, false);
        /*view.getLayoutParams().height = mRecyclerViewHeight;//设置单个条目高度
        RecognitionViewHolder holder = new RecognitionViewHolder(view);*/
        return new CommonDialogHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final CommonDialogHolder commonDialogHolder = (CommonDialogHolder) holder;;
        /**
         * 解决方案　１、添加空数据补全
         * 　　　　　２、
         */
        //GridLayoutManager layoutManager = new GridLayoutManager(this, 4);设置为4时 S型数据
        // 6->11   11->6
        final int positiona;
        //S形数据
        if(position/rowSize%2 == 1){
            positiona = (position/rowSize+1)*rowSize-position%rowSize-1;
        }else {
            positiona = holder.getAdapterPosition();
        }

        //正常数据
        //positiona = holder.getAdapterPosition();
        if((position+1)%rowSize == 0){
            String ddd = mList.get(positiona);
            Log.e("=====",ddd);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = UIUtils.dip2Px(10);
            commonDialogHolder.ll_aaaaa.setLayoutParams(params);
        }
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
        commonDialogHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence) (positiona+""));
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(positiona+"", mimeTypes, item);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                    commonDialogHolder.itemView.startDragAndDrop(dragData, new View.DragShadowBuilder(commonDialogHolder.itemView), commonDialogHolder.itemView, View.DRAG_FLAG_OPAQUE);//步骤1

                } else {

                    //View.DRAG_FLAG_OPAQUE 该属性 表示不使用半透明属性，可以点进源码查看，有对应好几种不同的Flag，可以使用自己想用的
                    commonDialogHolder.itemView.startDrag(dragData, new View.DragShadowBuilder(commonDialogHolder.tvData), commonDialogHolder.itemView, View.DRAG_FLAG_OPAQUE);//步骤1

                }
                return false;
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
    public void setOnItemClickListener(DrawTestAdapter.onRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnItem(int onClickItem) {
        this.onClickItem = onClickItem;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }
}
