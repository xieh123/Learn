package com.example.myapplication.ui.tab2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.DensityUtils;

/**
 * Created by xieH on 2017/7/5 0005.
 */
public class TransitionActivity extends AppCompatActivity {

    private LinearLayout mSearchLl;
    private TextView mSearchTv;

    private ScrollView mScrollView;
    private ImageView mImageView;

    private boolean isExpand = false;

    private TransitionSet mTransitionSet;

    ////////////////
    private LinearLayout mLinearLayout;
    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        initView();
    }

    private void initView() {
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mImageView = (ImageView) findViewById(R.id.imageView);

        mSearchLl = (LinearLayout) findViewById(R.id.search_ll);
        mSearchTv = (TextView) findViewById(R.id.search_tv);

        mScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                // 滚动距离>=大图高度-toolbar高度 即toolbar完全盖住大图的时候 且不是伸展状态 进行伸展操作
                if (mScrollView.getScrollY() >= mImageView.getHeight() && !isExpand) {
                    expand();
                    isExpand = true;
                }
                //滚动距离<=0时 即滚动到顶部时  且当前伸展状态 进行收缩操作
                else if (mScrollView.getScrollY() <= 0 && isExpand) {
                    reduce();
                    isExpand = false;
                }
            }
        });

        /////////////
        mLinearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        mButton = (Button) findViewById(R.id.button);
        mTextView = (TextView) findViewById(R.id.textView);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(mLinearLayout);
                mTextView.setVisibility(mTextView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });
    }


    private void expand() {
        // 设置伸展状态时的布局
        mSearchTv.setText("搜索简书的内容和朋友");
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) mSearchLl.getLayoutParams();
        LayoutParams.width = LayoutParams.MATCH_PARENT;
        LayoutParams.setMargins(DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));
        mSearchLl.setLayoutParams(LayoutParams);
        // 开始动画
        beginDelayedTransition(mSearchLl);
    }

    private void reduce() {
        // 设置收缩状态时的布局
        mSearchTv.setText("搜索");
        RelativeLayout.LayoutParams LayoutParams = (RelativeLayout.LayoutParams) mSearchLl.getLayoutParams();
        LayoutParams.width = DensityUtils.dp2px(this, 80);
        LayoutParams.setMargins(DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10), DensityUtils.dp2px(this, 10));
        mSearchLl.setLayoutParams(LayoutParams);
        // 开始动画
        beginDelayedTransition(mSearchLl);
    }

    private void beginDelayedTransition(ViewGroup view) {
        mTransitionSet = new AutoTransition();
        mTransitionSet.setDuration(300);
        TransitionManager.beginDelayedTransition(view, mTransitionSet);
    }
}
