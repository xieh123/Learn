package com.example.myapplication.ui.tab2.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/8/1 0001.
 */
public class EmojiAdapter extends RecyclerView.Adapter<EmojiViewHolder> {
    private Context mContext;
    private OnEmojiClickListener mOnEmojiClickListener;

    public EmojiAdapter(Context context, OnEmojiClickListener onEmojiClickListener) {
        mContext = context;
        mOnEmojiClickListener = onEmojiClickListener;
    }

    @Override
    public EmojiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmojiViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_emoji, parent, false));
    }

    @Override
    public void onBindViewHolder(EmojiViewHolder holder, int position) {
        holder.render(position + 0x1F601 - 1, mOnEmojiClickListener);
    }

    @Override
    public int getItemCount() {
        return 0x1F64F - 0x1F601;
    }

    public interface OnEmojiClickListener {
        void onEmojiCLick(String emoji);
    }
}
