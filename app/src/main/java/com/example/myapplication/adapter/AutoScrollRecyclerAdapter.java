package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xieH on 2016/12/20 0020.
 */
public class AutoScrollRecyclerAdapter extends RecyclerView.Adapter<AutoScrollRecyclerAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<String> mData;


    public AutoScrollRecyclerAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_auto_poll, parent, false);

        View view = null;

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String data = mData.get(position % mData.size());
//        holder.setText(R.id.tv_content, data);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView contentTv;


        public MyViewHolder(View itemView) {
            super(itemView);
//            contentTv = (TextView) itemView.findViewById(R.id.chatting_item_text_content_tv);
        }
    }
}
