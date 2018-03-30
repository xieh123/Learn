package com.example.myapplication.widget.loadmoreadapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/8/5 0005.
 */
public class EmptyLayout extends RelativeLayout {

    protected State mState = State.Normal;

    private FrameLayout mParentFl;

    private View mLoadingView;
    private TextView mLoadingText;

    private View mEndView;
    private View mErrorView;

    private OnReloadListener onReloadListener;

    public EmptyLayout(Context context) {
        this(context, null);
    }

    public EmptyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.layout_empty, this);
        setOnClickListener(null);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(params);

        mParentFl = (FrameLayout) findViewById(R.id.empty_parent_fl);

        setState(State.Loading, true);
    }

    public State getState() {
        return mState;
    }

    public void setState(State status) {
        setState(status, true);
    }

    /**
     * 设置状态
     *
     * @param status
     * @param showView 是否展示当前View
     */
    public void setState(State status, boolean showView) {
        if (mState == status) {
            return; // 如果状态已经相同 不做修改
        }
        mState = status;

        switch (status) {
            case Normal:
                this.setVisibility(GONE);

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                break;
            case Loading:
                this.setVisibility(VISIBLE);

                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.empty_loading_viewStub);
                    mLoadingView = viewStub.inflate();

                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.empty_loading_tv);
                } else {
                    mLoadingView.setVisibility(VISIBLE);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);

                break;
            case End:
                this.setVisibility(VISIBLE);

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                if (mEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.empty_end_viewStub);
                    mEndView = viewStub.inflate();
                } else {
                    mEndView.setVisibility(VISIBLE);
                }

                mEndView.setVisibility(showView ? VISIBLE : GONE);
                break;
            case Error:
                this.setVisibility(VISIBLE);

                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.empty_error_viewStub);
                    mErrorView = viewStub.inflate();
                } else {
                    mErrorView.setVisibility(VISIBLE);
                }

                mErrorView.setVisibility(showView ? VISIBLE : GONE);

                mErrorView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (onReloadListener != null) {
                            onReloadListener.onReload();
                            setState(State.Loading);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    public enum State {
        /**
         * 正常
         */
        Normal,

        /**
         * 加载中...
         */
        Loading,

        /**
         * 加载完成
         */
        End,

        /**
         * 加载失败
         */
        Error
    }

    /**
     * 设置 加载中 View
     *
     * @param view
     */
    public void setLoadingView(View view) {
        mParentFl.addView(view);

        this.mLoadingView = view;
        // 先隐藏
        this.mLoadingView.setVisibility(GONE);
    }

    /**
     * 设置 加载完成 View
     *
     * @param view
     */
    public void setEndView(View view) {
        mParentFl.addView(view);

        this.mEndView = view;
        // 先隐藏
        this.mEndView.setVisibility(GONE);
    }

    /**
     * 设置 加载失败 View
     *
     * @param view
     */
    public void setErrorView(View view) {
        mParentFl.addView(view);

        this.mErrorView = view;
        // 先隐藏
        this.mErrorView.setVisibility(GONE);
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface OnReloadListener {
        void onReload();
    }
}
