package com.example.myapplication.ui.tab1;

import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by xieH on 2017/4/19 0019.
 */
public class View11Activity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view11);

        initView();
    }

    public void initView() {
        // 根据本地视频文件源、Bitmap 对象生成缩略图 (比较耗时？)
//        ThumbnailUtils.createVideoThumbnail(String filePath, int kind);
//        ThumbnailUtils.extractThumbnail(Bitmap source, int width, int height);

        // kind表示类型，可以有两个选项，分别是Images.Thumbnails.MICRO_KIND和Images.Thumbnails.MINI_KIND，其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
        ThumbnailUtils.createVideoThumbnail("filepath", MediaStore.Images.Thumbnails.MINI_KIND);

//        view.performClick();  // 自动调用 View 点击事件


        mTextView = (TextView) findViewById(R.id.textView);

        String html = "<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p><p><a href=\\\"http://www.dreamdu.com/xhtml/\\\">超链接HTML入门</a>学习HTML!</p><p><font color=\\\"#aabb00\\\">颜色1</p><p><font color=\\\"#00bbaa\\\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>下面是网络图片</p><img src=\\\"http://avatar.csdn.net/0/3/8/2_zhang957411207.jpg\\\"/></body></html>";

        mTextView.setText(Html.fromHtml(html));

    }
}
