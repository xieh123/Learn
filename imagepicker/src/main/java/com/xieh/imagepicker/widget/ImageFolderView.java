package com.xieh.imagepicker.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xieh.imagepicker.R;
import com.xieh.imagepicker.adapter.BaseRecyclerAdapter;
import com.xieh.imagepicker.adapter.RecyclerHolder;
import com.xieh.imagepicker.bean.ImageFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2017/4/25 0025.
 */
public class ImageFolderView extends FrameLayout implements View.OnClickListener {

    private Context mContext;

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter<ImageFolder> mBaseRecyclerAdapter;
    private List<ImageFolder> mImageFolderList = new ArrayList<>();

    private Callback mCallback;

    public ImageFolderView(Context context) {
        this(context, null);
    }

    public ImageFolderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageFolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    public void init() {
        inflate(mContext, R.layout.imagepicker_popup_window_folder, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.popup_folder_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        initAdapter();
        mRecyclerView.setAdapter(mBaseRecyclerAdapter);

        setOnClickListener(this);
    }

    public void initAdapter() {
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<ImageFolder>(mRecyclerView, mImageFolderList, R.layout.imagepicker_item_recycler_folder) {
            @Override
            public void convert(RecyclerHolder holder, ImageFolder item, int position, boolean isScrolling) {

                holder.setText(R.id.folder_name_tv, item.getName());
                holder.setText(R.id.folder_size_tv, String.format("(%s)", item.getImages().size()));
                ImageView mImageView = holder.getView(R.id.folder_iv);

                Glide.with(mContext)
                        .load(item.getAlbumPath())
                        .into(mImageView);
            }
        };

        mBaseRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object data, int position) {
                if (mCallback != null) {
                    mCallback.onSelect(mImageFolderList.get(position));
                }

                onDismiss();
            }
        });
    }


    public void setAdapterData(List<ImageFolder> mImageFolderList) {
        this.mImageFolderList = mImageFolderList;
        mBaseRecyclerAdapter.refresh(mImageFolderList);
    }

    @Override
    public void onClick(View view) {
        onDismiss();
    }

    public void toggle() {
        if (getVisibility() == VISIBLE) {
            onDismiss();
        } else {
            show();
        }
    }

    public void show() {
        setVisibility(VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.popup_select_image_show);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setBackgroundColor(Color.parseColor("#80000000"));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        startAnimation(animation);
    }

    public void onDismiss() {
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.popup_select_image_hide);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(animation);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {
        void onSelect(ImageFolder model);
    }

}
