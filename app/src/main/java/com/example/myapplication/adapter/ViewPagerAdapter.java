package com.example.myapplication.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.transform.ImageUtils;

import java.util.List;

/**
 * Created by xieH on 2017/7/4 0004.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    private List<Item> mItemList;

    public ViewPagerAdapter(Context context, List<Item> itemList) {
        this.mContext = context;
        this.mItemList = itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final Item item = mItemList.get(position);
        View view = View.inflate(mContext, R.layout.item_viewpager, null);

        final ImageView mImageView = (ImageView) view.findViewById(R.id.item_viewpager_iv);
        if (!TextUtils.isEmpty(item.getUrl())) {
            ImageUtils.loadImage(mContext, mImageView, item.getUrl());
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    public void refresh(List<Item> itemList) {
        if (itemList == null) {
            return;
        }

        this.mItemList = itemList;
        notifyDataSetChanged();
    }
}
