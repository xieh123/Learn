package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.example.myapplication.util.SPUtils;
import com.example.myapplication.util.ScreenUtils;


/**
 * Created by xieH on 2018/1/3 0003.
 */
public class Floating22View extends FrameLayout {

    private Context mContext;

    private ViewGroup.LayoutParams mLayoutParams;

    ///////////

    private RelativeLayout mRelativeLayout;

    private WebView mWebView;

    private int hostType = 0;
    private String host = "https://www.baidu.com/s?wd=";

    private String url = "https://m.sogou.com/";

    private WindowManager mWindowManager;

    private Point prePoint, curPoint;

    /**
     * View最大的高度
     */
    private int mViewMaxHeight = 0;

    /**
     * View最小的高度
     */
    private int mViewMinHeight = 0;

    /**
     * 滑动最小距离
     */
    private static final float FLING_MIN_DISTANCE = 0.2f;

    private int mDownY;

    public Floating22View(Context context) {
        this(context, null);
    }

    public Floating22View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Floating22View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.view_floating22, this);

        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mRelativeLayout = (RelativeLayout) findViewById(R.id.floating_rl);
        mWebView = (WebView) findViewById(R.id.floating_wv);

        mLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ScreenUtils.getScreenHeight(context) / 3 + dp2px(30));
        mRelativeLayout.setLayoutParams(mLayoutParams);

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

        hostType = SPUtils.getInt("hostType", 0);
        host = SPUtils.getString("host", host);

        if (hostType == 0) {
            mWebView.loadUrl("https://www.baidu.com/");
        } else {
            mWebView.loadUrl("https://m.sogou.com/");
        }

    }

    public void loadUrl(String text) {
        url = host + text;
        mWebView.setVisibility(VISIBLE);
        mWebView.loadUrl(url);
    }

    private int dp2px(int dp) {
        return (int) (dp * mContext.getResources().getDisplayMetrics().density);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int mCurrentY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = mCurrentY;
                prePoint = new Point((int) event.getRawX(), (int) event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentY - mDownY != 0 && Math.abs(mCurrentY - mDownY) > FLING_MIN_DISTANCE) {
                    curPoint = new Point((int) event.getRawX(), (int) event.getRawY());

                    int dx = curPoint.x - prePoint.x;
                    int dy = curPoint.y - prePoint.y;

                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) this.getLayoutParams();
                    layoutParams.x += dx;
                    layoutParams.y += dy;

                    if (layoutParams.y > mViewMinHeight && layoutParams.y < mViewMaxHeight) {
                        mWindowManager.updateViewLayout(this, layoutParams);
                    }

                    prePoint = curPoint;
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
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
