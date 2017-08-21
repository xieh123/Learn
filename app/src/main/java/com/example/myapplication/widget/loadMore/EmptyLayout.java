package com.example.myapplication.widget.loadMore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/8/5 0005.
 */
public class EmptyLayout extends RelativeLayout {

    protected State mState = State.Normal;

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
        Normal,  // 正常
        Loading, // 加载中...
        End,     // 加载完成
        Error    // 加载失败
    }

    /**
     * 设置 加载中 View
     *
     * @param loadingView
     */
    public void setLoadingView(View loadingView) {
        ((ViewGroup) getChildAt(0)).removeView(mLoadingView);  // 先移除旧的View
        ((ViewGroup) getChildAt(0)).addView(loadingView);      // 再添加新的View
        this.mLoadingView = loadingView;
    }

    /**
     * 设置 加载完成 View
     *
     * @param endView
     */
    public void setEndView(View endView) {
        ((ViewGroup) getChildAt(0)).removeView(mEndView);  // 先移除旧的View
        ((ViewGroup) getChildAt(0)).addView(endView);      // 再添加新的View
        this.mEndView = endView;
        this.mEndView.setVisibility(GONE);  // 先隐藏
    }

    /**
     * 设置 加载失败 View
     *
     * @param errorView
     */
    public void setErrorView(View errorView) {
        ((ViewGroup) getChildAt(0)).removeView(mErrorView);  // 先移除旧的View
        ((ViewGroup) getChildAt(0)).addView(errorView);      // 再添加新的View
        this.mErrorView = errorView;
        this.mErrorView.setVisibility(GONE);  // 先隐藏
    }

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface OnReloadListener {
        void onReload();
    }
}
