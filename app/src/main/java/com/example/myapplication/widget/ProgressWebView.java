package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.myapplication.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 带进度条的WebView
 */
public class ProgressWebView extends WebView {

    private Context mContext;

    private ProgressBar mProgressbar;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        init();
    }

    public void init() {
        mProgressbar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
        mProgressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5, 0, 0));

        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.progress_bar_states);
        mProgressbar.setProgressDrawable(drawable);

        addView(mProgressbar);

        setWebChromeClient(new WebChromeClient());
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");
        addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

        setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean isShould;
                if (isNetworkUrl(url)) {
                    view.loadUrl(url);
                    isShould = true;
                } else {
                    if (onHtmlEventListener != null)
                        onHtmlEventListener.onUriLoading(Uri.parse(url));
                    isShould = false;
                }
                return isShould;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+" +
//                        "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                super.onPageFinished(view, url);
            }
        });

    }

    private OnHtmlEventListener onHtmlEventListener;

    public void setOnHtmlEventListener(OnHtmlEventListener onHtmlEventListener) {
        this.onHtmlEventListener = onHtmlEventListener;
    }

    public interface OnHtmlEventListener {
        void onFinished(String html);

        void onUriLoading(Uri uri);
    }

    public class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            if (onHtmlEventListener != null) onHtmlEventListener.onFinished(html);
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mProgressbar.setVisibility(GONE);
            } else {
                if (mProgressbar.getVisibility() == GONE)
                    mProgressbar.setVisibility(VISIBLE);
                mProgressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) mProgressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        mProgressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 检查 URL 是否合法
     *
     * @param url
     * @return true 合法，false 非法
     */
    public boolean isNetworkUrl(String url) {
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
}