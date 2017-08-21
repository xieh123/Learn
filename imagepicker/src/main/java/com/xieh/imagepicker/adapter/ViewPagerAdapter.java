package com.xieh.imagepicker.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.xieh.imagepicker.widget.ImagePreviewView;
import com.xieh.imagepicker.R;

/**
 * Created by xieH on 2017/4/19 0019.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mImageSources;

    private View.OnClickListener mFinishClickListener;

    public ViewPagerAdapter(Context context, String[] mImageSources) {
        this.mContext = context;
        this.mImageSources = mImageSources;
    }


    @Override
    public int getCount() {
        return mImageSources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_gallery, container, false);
        ImagePreviewView previewView = (ImagePreviewView) view.findViewById(R.id.item_preview_iv);

        Glide.with(mContext)
                .load(mImageSources[position])
                .placeholder(R.drawable.ic_default_image)
                .into(previewView);

        previewView.setOnClickListener(getListener());
        container.addView(view);
        return view;
    }

    private View.OnClickListener getListener() {
        if (mFinishClickListener == null) {
            mFinishClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity) mContext).finish();
                }
            };
        }
        return mFinishClickListener;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
