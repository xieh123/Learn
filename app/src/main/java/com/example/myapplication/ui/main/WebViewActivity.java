package com.example.myapplication.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.R;
import com.example.myapplication.widget.ProgressWebView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xieH on 2017/1/2 0002.
 */
public class WebViewActivity extends AppCompatActivity {

    private String url = "https://www.eggou.com/mobile";

    private WebView mWebView;

    private ProgressWebView mProgressWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // initProgressView();

        initView();
    }


    public void initProgressView() {
        mProgressWebView = (ProgressWebView) findViewById(R.id.webView_progressWebView);
        mProgressWebView.setVisibility(View.VISIBLE);

        mProgressWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                WebResourceResponse response = null;
                if (url.contains(".png")) {
                    try {
                        InputStream localCopy = getAssets().open("test11.png");
                        response = new WebResourceResponse("image/png", "UTF-8", localCopy);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return response;
            }
        });

        mProgressWebView.loadUrl(url);
    }

    /////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////
    public void initView() {
        mWebView = (WebView) findViewById(R.id.webView_wv);
        mWebView.setVisibility(View.VISIBLE);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);

//        mWebView.getSettings().setBlockNetworkImage(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 对网页中超链接的响应
                if (true) {
                    // 在当前的 WebView 中跳转到新的url
                    view.loadUrl(url);
                } else {
                    // 启动浏览器打开 url
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }

                System.out.println("h----shouldOverrideUrlLoading");

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                // 开始加载
                System.out.println("h----onPageStarted----" + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // 加载完成
                System.out.println("h----onPageFinished----" + url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, final String url) {
//                if (url.contains(".jpg") || url.contains(".png")) {
//                    WebResourceResponse response = null;
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeFile(getImagePath(url));
//
//                        if (bitmap == null) {
//                            bitmap = Glide.with(WebViewActivity.this).load(url)
//                                    .asBitmap()
//                                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                                    .get();
//
//                            saveBitmap2File(bitmap, getImageName(url));
//                        } else {
//                            InputStream inputStream = Bitmap2InputStream(bitmap);
//
//                            if (url.contains(".png")) {
//                                response = new WebResourceResponse("image/png", "UTF-8", inputStream);
//                            }
//
//                            if (url.contains(".jpg")) {
//                                response = new WebResourceResponse("image/jpeg", "UTF-8", inputStream);
//                            }
//
//                            if (response != null) {
//                                return response;
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
                return super.shouldInterceptRequest(view, url);
            }

        });

        url = "http://partner.eggou.com/";

        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (mWebView != null) {
            // 处理 WebView 的返回事件
            if (this.mWebView.canGoBack()) {
                this.mWebView.goBack();
                return;
            }
        }

        super.onBackPressed();
    }


    /////////////////////////////////////////
    /////////////////////////////////////////
    private void initWebView() {

        WebSettings ws = mWebView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 缩放比例 1
        mWebView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        // 页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);

        // WebView从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 设置字体默认缩放大小(改变网页字体大小,setTextSize  api 14被弃用)
        ws.setTextZoom(100);

//        mWebChromeClient = new MyWebChromeClient(this);
//        mWebView.setWebChromeClient(mWebChromeClient);
//        // 与js交互
//        mWebView.addJavascriptInterface(new ImageClickInterface(this), "local_obj");
//        mWebView.setWebViewClient(new MyWebViewClient(this));
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {

            } else {

            }
            super.onProgressChanged(view, newProgress);
        }
    }

    public class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {

        }
    }


    /**
     * 保存图片
     *
     * @param bitmap
     */
    public static String saveBitmap2File(Bitmap bitmap, String fileName) {

        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filePath;
    }

    public static String getImageName(String url) {
        int lastSlashIndex = url.lastIndexOf("/");
        String imageName = url.substring(lastSlashIndex + 1);
        return imageName;
    }

    public static String getImagePath(String url) {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "HHH" + File.separator + "images" + File.separator + getImageName(url);
        return filePath;
    }

    public InputStream Bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

}
