package com.example.myapplication.ui.tab1;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.util.SpannableStringUtils;

import java.util.HashMap;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by xieH on 2017/4/19 0019.
 */
public class View11Activity extends AppCompatActivity {

    private TextView mTextView;

    private ImageView mImageView;

    private TextView mTextView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view11);

        initView();
    }

    public void initView() {
        // 根据本地视频文件源、Bitmap 对象生成缩略图 (比较耗时？)
//        ThumbnailUtils.createVideoThumbnail(String filePath, int kind);
        // 图片缩略图
//        ThumbnailUtils.extractThumbnail(Bitmap source, int width, int height);


        String url = "http://mpv.videocc.net/ce0812b122/a/ce0812b122bf0fb49d79ebd97cbe98fa_1.mp4";

        // kind表示类型，可以有两个选项，分别是Images.Thumbnails.MICRO_KIND和Images.Thumbnails.MINI_KIND，其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
        ThumbnailUtils.createVideoThumbnail("filePath", MediaStore.Images.Thumbnails.MINI_KIND);

        mImageView = (ImageView) findViewById(R.id.imageView);
        createVideoThumbnail(mImageView, url, 150, 200);


        mTextView2 = (TextView) findViewById(R.id.textView2);

        String content = "看到这样的效果，可能你会不假思索地选择 LinearLayout 容器，同时分配 children 的 weight 属性。不错，这样实现确实很简单。但是，通常界面上还有其他元素，父容器一般使用的是 RelativeLayout ，如果再选择使用一层 LinearLayout 包裹这两个 Button 的话，无疑会额外增加视图层次（View Hierarchy），加大性能渲染压力。其实，大可不必这样做，RelativeLayout 也能让两个 children 水平居中等分宽度。";

        String[] keys = new String[]{"LinearLayout", "确实很简单", "无疑会额外增加视图层次", "RelativeLayout"};
        SpannableStringBuilder builder = SpannableStringUtils.matcherTextStyle(content, keys, ContextCompat.getColor(this, R.color.text_select_color));
        mTextView2.setText(builder);

//        view.performClick();  // 自动调用 View 点击事件


        mTextView = (TextView) findViewById(R.id.textView);
        String html = "<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p><p><a href=\\\"http://www.dreamdu.com/xhtml/\\\">超链接HTML入门</a>学习HTML!</p><p><font color=\\\"#aabb00\\\">颜色1</p><p><font color=\\\"#00bbaa\\\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>下面是网络图片</p><img src=\\\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\\\"/></body></html>";
        mTextView.setText(Html.fromHtml(html));

    }


    private void createVideoThumbnail(final ImageView imageView, final String url, final int width, final int height) {
        Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
                try {
                    if (Build.VERSION.SDK_INT >= 14) {
                        retriever.setDataSource(url, new HashMap<String, String>());
                    } else {
                        retriever.setDataSource(url);
                    }
                    bitmap = retriever.getFrameAtTime();
                } catch (IllegalArgumentException ex) {
                    // Assume this is a corrupt video file
                } catch (RuntimeException ex) {
                    // Assume this is a corrupt video file.
                } finally {
                    try {
                        retriever.release();
                    } catch (RuntimeException ex) {
                        // Ignore failures while cleaning up.
                    }
                }
                if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
                    bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
                }

                subscriber.onNext(bitmap);
            }
        });

        observable.subscribeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }
}
