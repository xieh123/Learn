package com.example.myapplication.fragment.dialog;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xieH on 2017/8/4 0004.
 */
public class ViewHolder {

    private SparseArray<View> views;
    private View convertView;

    private ViewHolder(View view) {
        convertView = view;
        views = new SparseArray<>();
    }

    public static ViewHolder create(View view) {
        return new ViewHolder(view);
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(text);
    }

    public void setText(int viewId, int textId) {
        TextView textView = getView(viewId);
        textView.setText(textId);
    }

    public void setTextColor(int viewId, int colorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(colorId);
    }

    public void setOnClickListener(int viewId, View.OnClickListener clickListener) {
        View view = getView(viewId);
        view.setOnClickListener(clickListener);
    }
}
