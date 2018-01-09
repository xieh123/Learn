package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.widget.SlideLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xieH on 2017/10/17 0017.
 */
public class SlideRecyclerAdapter extends RecyclerView.Adapter<SlideRecyclerAdapter.SlideViewHolder> {

    private static final int NORMAL = 1;
    private static final int SLIDE = 2;
    private int mState = NORMAL;

    private Context mContext;
    private List<Item> mItemList;

    private List<SlideViewHolder> mSlideViewHolders = new ArrayList<>();

    public SlideRecyclerAdapter(Context context, List<Item> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
    }

    @Override
    public SlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler_slide, parent, false);

        SlideViewHolder slideViewHolder = new SlideViewHolder(view);
        mSlideViewHolders.add(slideViewHolder);

        return slideViewHolder;
    }

    @Override
    public void onBindViewHolder(SlideViewHolder holder, int position) {
        Item item = mItemList.get(position);

        holder.mCheckBox.setChecked(item.isSelected());
        switch (mState) {
            case NORMAL:
                holder.mItemSlideLayout.close();
                break;
            case SLIDE:
                holder.mItemSlideLayout.open();
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    /**
     * 遍历所有
     */
    public void openItemsAnimation() {
        mState = SLIDE;
        for (SlideViewHolder holder : mSlideViewHolders) {
            holder.openItemAnimation();
        }
    }

    public void closeItemsAnimation() {
        mState = NORMAL;
        for (SlideViewHolder holder : mSlideViewHolders) {
            holder.closeItemAnimation();
        }
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mCheckBox;
        private SlideLayout mItemSlideLayout;

        public SlideViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkbox);
            mItemSlideLayout = (SlideLayout) itemView.findViewById(R.id.item_slideLayout);
        }

        public void openItemAnimation() {
            mItemSlideLayout.openAnimation();
        }

        public void closeItemAnimation() {
            mItemSlideLayout.closeAnimation();
        }
    }

    public void refresh(Collection<Item> datas) {
        if (datas == null) {
            mItemList = new ArrayList<>();
        } else if (datas instanceof List) {
            mItemList = (List<Item>) datas;
        } else {
            mItemList = new ArrayList<>(datas);
        }

        notifyDataSetChanged();
    }
}
