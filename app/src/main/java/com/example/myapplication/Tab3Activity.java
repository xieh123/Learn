package com.example.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myapplication.ui.tab3.Behavior22Activity;
import com.example.myapplication.ui.tab3.CoordinatorActivity;
import com.example.myapplication.ui.tab3.MediaProjectionActivity;
import com.example.myapplication.ui.tab3.SampleRecyclerViewActivity;

/**
 * Created by xieH on 2017/6/15 0015.
 */
public class Tab3Activity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);

        initView();
    }

    private void initView() {

    }

    public void showNotification(View v) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Intent mIntent = new Intent(this, MainActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);

        builder.setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis())
                .setTicker("ccccc")
                .setAutoCancel(true)
                .setContentText("ttttttttt")
                .setContentTitle("hhhhhhhhh")
                .setSmallIcon(R.mipmap.ic_launcher);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.soul);

        builder.setSound(null);


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // id 随意，正好使用定义的常量做id，0除外，0为默认的Notification
        notificationManager.notify(1, builder.build());

        playSound(this);
    }

    private static Ringtone mRingtone;
    private boolean allowMusic = true;

    /**
     * 播放自定义的声音
     *
     * @param context
     */
    public synchronized void playSound(Context context) {
        if (!allowMusic) {
            return;
        }
        if (mRingtone == null) {
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.kameng);
            mRingtone = RingtoneManager.getRingtone(context.getApplicationContext(), uri);
        }
        if (!mRingtone.isPlaying()) {
            mRingtone.play();
        }
    }


    public void mediaProjection(View v) {
        Intent intent = new Intent(this, MediaProjectionActivity.class);
        startActivity(intent);
    }

    public void sampleRecyclerView(View v) {
        Intent intent = new Intent(this, SampleRecyclerViewActivity.class);
        startActivity(intent);
    }

    public void coordinatorLayout(View v) {
        Intent intent = new Intent(this, CoordinatorActivity.class);
        startActivity(intent);
    }

    public void behavior(View v) {
        Intent intent = new Intent(this, Behavior22Activity.class);
        startActivity(intent);
    }

}
