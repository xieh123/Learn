package com.example.myapplication.receiver;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.myapplication.base.ActivityCollector;
import com.example.myapplication.ui.main.LoginActivity;

/**
 * 广播监听强制下线
 * <p/>
 * Created by xieH on 2017/11/7 0007.
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Warning");
        builder.setMessage("You are forced to be offline. Please try to login again.");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finishAllActivity();

                Intent intent1 = new Intent(context, LoginActivity.class);
                context.startActivity(intent1);
            }
        });
        builder.show();
    }
}
