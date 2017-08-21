package com.example.myapplication.ui.tab1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.List;

/**
 * Created by xieH on 2017/2/13 0013.
 */
public class GotoQQActivity extends AppCompatActivity {

    private String url = "http://eggou.com/mobile";

    private WebView mWebView;

    private String qq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goto_qq);

        initView();
    }

    public void initView() {
        mWebView = (WebView) findViewById(R.id.goto_qq_wv);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setDefaultTextEncodingName("utf-8");

        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);

        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("wpa.qq.com/msgrd")) {
                    String[] arr = url.split("&");

                    if (arr.length > 0) {
                        qq = arr[1].replace("uin=", "");
                    }

                    // 判断并启动QQ
                    if (isQQAvailable(GotoQQActivity.this)) {
                        String url1 = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;

                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
                        startActivity(intent);
                    } else {
                        Toast.makeText(GotoQQActivity.this, "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
                    }

                    return true;   // true表示不加载默认的url;
                }

                return false;
            }
        });

        mWebView.loadUrl(url);
    }


    /**
     * 判断系统是否安装有QQ客户端
     *
     * @param context
     * @return
     */
    public static boolean isQQAvailable(Context context) {
        final PackageManager mPackageManager = context.getPackageManager();

        List<PackageInfo> pInfo = mPackageManager.getInstalledPackages(0);
        if (pInfo != null) {
            for (int i = 0; i < pInfo.size(); i++) {

                String pn = pInfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 打开qq聊天页面，需要开启qq推广功能才能发送消息
     */
    public void gotoQQ(View view) {

//      String url = "mqqwpa://im/chat?chat_type=wpa&uin=你的QQ号码";

        String url = "mqqwpa://im/chat?chat_type=wpa&uin=938066090";

        if (isQQAvailable(GotoQQActivity.this)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Toast.makeText(GotoQQActivity.this, "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开qq客服页面
     */
    public void gotoCustomerService(View view) {

        String url = "http://wpa.b.qq.com/cgi/wpa.php?ln=2&uin=4009229888";

        if (isQQAvailable(GotoQQActivity.this)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Toast.makeText(GotoQQActivity.this, "您的手机暂未安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
    }

}
