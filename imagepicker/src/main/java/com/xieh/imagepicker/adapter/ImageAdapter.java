package com.xieh.imagepicker.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xieh.imagepicker.R;
import com.xieh.imagepicker.bean.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by xieH on 2017/1/9 0009.
 */
public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Image> itemList;

    private boolean isNeedToShow = true;


    public ImageAdapter(Context context, List<Image> itemList) {
        this.mContext = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;

        if (viewType == 0) {
            view = View.inflate(mContext, R.layout.imagepicker_item_recycler_camera, null);
            holder = new CamViewHolder(view);
        } else {
            view = View.inflate(mContext, R.layout.imagepicker_item_recycler_image, null);
            holder = new ImageViewHolder(view);
        }

        return holder;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {

        Image image = itemList.get(position);
        if (image.getId() == 0)
            return 0;
        return 1;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Image item = itemList.get(position);

        if (item.getId() != 0) {
            ImageViewHolder viewHolder = (ImageViewHolder) holder;

            if (isNeedToShow) {
                viewHolder.mCheckIv.setVisibility(View.VISIBLE);
                viewHolder.mCheckIv.setSelected(item.isSelect());
            } else {
                viewHolder.mCheckIv.setVisibility(View.GONE);
            }

            viewHolder.mMaskView.setVisibility(item.isSelect() ? View.VISIBLE : View.GONE);

            Glide.with(mContext)
                    .load(item.getPath())
                    .placeholder(R.drawable.ic_picker_default_placeholder)
                    .into(viewHolder.mImageView);
        }

        holder.itemView.setOnClickListener(getOnClickListener(position));
    }


    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, itemList.get(position), position);
                }
            }
        };
    }

    private static class CamViewHolder extends RecyclerView.ViewHolder {
        CamViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCheckIv, mImageView;
        private View mMaskView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mCheckIv = (ImageView) itemView.findViewById(R.id.item_list_image_selected_iv);
            mMaskView = itemView.findViewById(R.id.item_list_image_mask_view);
            mImageView = (ImageView) itemView.findViewById(R.id.item_list_image_iv);
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View view, Object data, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public void refresh(Collection<Image> itemList, boolean isShow) {
        if (itemList == null) {
            this.itemList = new ArrayList<>();
        } else if (itemList instanceof List) {
            this.itemList = (List<Image>) itemList;
        } else {
            this.itemList = new ArrayList<>(itemList);
        }

        this.isNeedToShow = isShow;

        notifyDataSetChanged();
    }
}
