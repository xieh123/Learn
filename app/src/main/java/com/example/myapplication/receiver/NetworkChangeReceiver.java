package com.example.myapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.myapplication.base.MyApplication;

/**
 * 网络状态监听
 * <p/>
 * Created by xieH on 2017/11/7 0007.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            Toast.makeText(MyApplication.getInstance(), "network is available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MyApplication.getInstance(), "network is unavailable", Toast.LENGTH_SHORT).show();
        }


    }
}
