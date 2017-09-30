package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.util.List;

/**
 * Created by xieH on 2016/12/21 0021.
 */
public class CycleRecyclerAdapter extends RecyclerView.Adapter<CycleRecyclerAdapter.MyViewHolder> {

    private  Context mContext;
    private  List<Item> itemList;


    public CycleRecyclerAdapter(Context context, List<Item> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.item_recyclerview, null);

        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = itemList.get(position % itemList.size());

        holder.imageIv.setImageResource(item.getResourceId());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageIv;


        public MyViewHolder(View itemView) {
            super(itemView);
            imageIv = (ImageView) itemView.findViewById(R.id.recyclerview_item_iv);
        }
    }
}