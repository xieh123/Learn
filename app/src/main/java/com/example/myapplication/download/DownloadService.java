package com.example.myapplication.download;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.example.myapplication.R;

import java.io.File;

import rx.Subscriber;

/**
 * Created by xieH on 2017/1/16 0016.
 */
public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    int downloadCount = 0;

   // private String apkUrl = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";

    private String apkUrl = "https://www.eggou.com/com.eggou.apk";


    private File outputFile;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        notificationManager.notify(0, notificationBuilder.build());

        download();
    }

    private void download() {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                //不频繁发送通知，防止通知栏下拉卡顿
                int progress = (int) ((bytesRead * 100) / contentLength);
                if ((downloadCount == 0) || progress > downloadCount) {
                    sendNotification(progress, bytesRead, contentLength);
                }
            }
        };
        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "file.apk");

        if (outputFile.exists()) {
            outputFile.delete();
        }


        new DownloadAPI(listener).downloadAPK(apkUrl, outputFile, new Subscriber() {
            @Override
            public void onCompleted() {
                downloadCompleted();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                downloadCompleted();
            }

            @Override
            public void onNext(Object o) {

            }
        });
    }

    private void downloadCompleted() {

        //安装apk
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(outputFile), "application/vnd.android.package-archive");
        startActivity(intent);

        // 更新参数,注意flags要使用FLAG_UPDATE_CURRENT // 点击通知栏跳到安装程序
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationBuilder.setContentIntent(contentIntent);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("下载完成，点击安装");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void sendNotification(int progress, long currentFileSize, long totalFileSize) {

        notificationBuilder.setProgress(100, progress, false);
        notificationBuilder.setContentText(StringUtils.getDataSize(currentFileSize) + "/" + StringUtils.getDataSize(totalFileSize));
        notificationManager.notify(0, notificationBuilder.build());
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }
}
