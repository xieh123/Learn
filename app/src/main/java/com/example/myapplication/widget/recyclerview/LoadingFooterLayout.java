package com.example.myapplication.widget.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * Created by xieh on 2016/11/21.
 * <p>
 * RecyclerView 分页加载时使用到的FooterView
 */
public class LoadingFooterLayout extends RelativeLayout {

    protected State mState = State.Normal;

    private View mLoadingView;
    private ProgressBar mLoadingProgress;
    private TextView mLoadingText;

    private View mEndView;
    private View mErrorView;

    private OnReloadListener onReloadListener;

    public LoadingFooterLayout(Context context) {
        this(context, null);
    }

    public LoadingFooterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingFooterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.recycler_footer, this);
        setOnClickListener(null);

        setState(State.Normal, true);
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
                setOnClickListener(null);
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
                setOnClickListener(null);
                if (mEndView != null) {
                    mEndView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                if (mLoadingView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.footer_loading_viewStub);
                    mLoadingView = viewStub.inflate();

                    mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.footer_loading_pb);
                    mLoadingText = (TextView) mLoadingView.findViewById(R.id.footer_loading_tv);
                } else {
                    mLoadingView.setVisibility(VISIBLE);
                }

                mLoadingView.setVisibility(showView ? VISIBLE : GONE);
                mLoadingProgress.setVisibility(View.VISIBLE);

                break;
            case End:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(GONE);
                }

                if (mErrorView != null) {
                    mErrorView.setVisibility(GONE);
                }

                if (mEndView == null) {
                    ViewStub viewStub = (ViewStub) findViewById(R.id.footer_end_viewStub);
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
                    ViewStub viewStub = (ViewStub) findViewById(R.id.footer_error_viewStub);
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

    public void setOnReloadListener(OnReloadListener onReloadListener) {
        this.onReloadListener = onReloadListener;
    }

    public interface OnReloadListener {
        void onReload();
    }
}