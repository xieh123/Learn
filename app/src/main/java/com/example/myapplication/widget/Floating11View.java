package com.example.myapplication.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.SPUtils;
import com.example.myapplication.util.ScreenUtils;


/**
 * Created by xieH on 2018/1/3 0003.
 */
public class Floating11View extends FrameLayout {

    private Context mContext;

    private ScrollView mScrollView;

    /**
     * 滑动最小距离
     */
    private static final float FLING_MIN_DISTANCE = 0.5f;

    /**
     * View最大的高度
     */
    private int mViewMaxHeight = 0;

    /**
     * View最小的高度
     */
    private int mViewMinHeight = 0;

    private ViewGroup.LayoutParams mLayoutParams;

    /**
     * 是否改变
     */
    private boolean isChange;

    private int mDownY;

    ///////////

    private WebView mWebView;
    private TextView mTextView;
    private TextView mTextView11;

    private int hostType = 0;
    private String host = "https://www.baidu.com/s?wd=";

    private boolean isShowWeb;

    private String url = "https://m.sogou.com/";

    private boolean isWebViewScrolled = false;

    public Floating11View(Context context) {
        this(context, null);
    }

    public Floating11View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Floating11View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_floating, this);

        mScrollView = (ScrollView) findViewById(R.id.floating_sv);
        mWebView = (WebView) findViewById(R.id.floating_wv);
        mTextView = (TextView) findViewById(R.id.floating_11_tv);
        mTextView11 = (TextView) findViewById(R.id.floating_22_tv);

        mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(context) / 3 + dp2px(30));
        mScrollView.setLayoutParams(mLayoutParams);

        mViewMaxHeight = getScreenHeight() * 2 / 3;
        mViewMinHeight = getScreenHeight() / 3;

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheMaxSize(Long.MAX_VALUE);
        settings.setPluginState(WebSettings.PluginState.ON_DEMAND);
        settings.setAllowFileAccess(true);
        // 将图片下载阻塞
        settings.setBlockNetworkImage(true);

        mWebView.setWebViewClient(
                new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return true;
                    }

                    @Override
                    public void onPageFinished(WebView webView, String s) {
                        super.onPageFinished(webView, s);
                        if (hostType == 0) {
                            // 隐藏html标签
                            // mWebView.loadUrl("javascript:function hideLabel(){document.getElementById('page-hd').style.display=\"none\";document.getElementById('page-ft').style.display=\"none\";} hideLabel();");
                        } else {
                            // 隐藏html标签
                            mWebView.loadUrl("javascript:function hideLabel(){document.getElementById('header').style.display=\"none\";document.getElementById('footer').style.display=\"none\";} hideLabel();");
                        }
                    }
                }
        );

        isShowWeb = SPUtils.getBoolean("showWeb", true);
        boolean isShowText = SPUtils.getBoolean("showText", true);
        hostType = SPUtils.getInt("hostType", 0);
        host = SPUtils.getString("host", host);

        if (hostType == 0) {
            mWebView.loadUrl("https://www.baidu.com/");
        } else {
            mWebView.loadUrl("https://m.sogou.com/");
        }

        setViewVisibility(isShowWeb, isShowText);

        mWebView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mWebView.requestDisallowInterceptTouchEvent(false);
                        mScrollView.requestDisallowInterceptTouchEvent(true);
                        isWebViewScrolled = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        mWebView.requestDisallowInterceptTouchEvent(true);
                        mScrollView.requestDisallowInterceptTouchEvent(false);
                        isWebViewScrolled = false;
                        break;
                    default:
                        break;
                }

                return false;
            }
        });

    }

    public void setViewVisibility(boolean isShowWeb, boolean isShowText) {
        if (isShowWeb) {
            mWebView.setVisibility(VISIBLE);
        } else {
            mWebView.setVisibility(GONE);
        }

        if (isShowText) {
            mTextView.setVisibility(VISIBLE);
            mTextView11.setVisibility(VISIBLE);
        } else {
            mTextView.setVisibility(GONE);
            mTextView11.setVisibility(GONE);
        }
    }

    public void loadUrl(String text, boolean isShow) {
        url = host + text;
        if (isShowWeb) {
            if (true) {
                mWebView.setVisibility(VISIBLE);
                mWebView.loadUrl(url);
            } else {
                mWebView.setVisibility(GONE);
            }
        }
    }

    public void setViewText(SpannableStringBuilder text) {
        if (mTextView.getVisibility() == VISIBLE) {
            mTextView.setText(text);
        }
    }

    public void setView11Text(SpannableStringBuilder text) {
        if (mTextView11.getVisibility() == VISIBLE) {
            mTextView11.setText(text);
        }
    }

    private int dp2px(int dp) {
        return (int) (dp * mContext.getResources().getDisplayMetrics().density);
    }

    /////////////////////////
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 不在顶部时，需要重新获取原点坐标
        int scrollY;
        if (isWebViewScrolled) {
            scrollY = mWebView.getScrollY();
        } else {
            scrollY = mScrollView.getScrollY();
        }

        if (scrollY > 0) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                isChange = true;
            }
            // 继续传递下去
            return super.dispatchTouchEvent(event);
        } else {
            // 原点已发生改变，需要重新获取坐标
            if (isChange) {
                mDownY = (int) event.getY();
                isChange = false;
            }
        }

        int mCurrentY = (int) event.getY();
        switch (MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                mDownY = mCurrentY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mCurrentY - mDownY;

                // 向上滑动
                if (mCurrentY - mDownY < 0 && Math.abs(mCurrentY - mDownY) > FLING_MIN_DISTANCE) {
                    // 控件的高度 + 滑动的距离
                    int height = mScrollView.getHeight() + Math.abs(dy);

                    if (height >= mViewMaxHeight) {
                        adjustViewHeight(mViewMaxHeight);
                    } else {
                        adjustViewHeight(height);
                    }
                } else if (mCurrentY - mDownY > 0 && Math.abs(mCurrentY - mDownY) > FLING_MIN_DISTANCE) {
                    // 向下滑动
                    // 控件的高度 - 滑动的距离
                    int height = mScrollView.getHeight() - dy;

                    if (height <= mViewMinHeight) {
                        adjustViewHeight(mViewMinHeight);
                    } else {
                        adjustViewHeight(height);
                    }

                    // 拦截事件
                    return true;
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * 动态设置 View的高度
     *
     * @param viewHeight
     */
    private void adjustViewHeight(int viewHeight) {
        mLayoutParams.height = viewHeight;
        mScrollView.setLayoutParams(mLayoutParams);
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    private int getScreenHeight() {
        return getResources().getDisplayMetrics().heightPixels;
    }

}
