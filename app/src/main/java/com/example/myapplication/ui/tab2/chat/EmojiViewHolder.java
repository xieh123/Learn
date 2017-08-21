package com.example.myapplication.ui.tab2.chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/8/1 0001.
 */
public class EmojiViewHolder extends RecyclerView.ViewHolder {

    private TextView mEmoji;

    public EmojiViewHolder(View itemView) {
        super(itemView);
        mEmoji = (TextView) itemView.findViewById(R.id.tv_emoji);
    }

    public void render(int unicodeEmoji, final EmojiAdapter.OnEmojiClickListener onEmojiClickListener) {
        mEmoji.setText(EmojiUtils.getEmojiStringByUnicode(unicodeEmoji));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmojiClickListener.onEmojiCLick(mEmoji.getText().toString());
            }
        });
    }
}
