package com.example.myapplication.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication.R;
import com.example.myapplication.adapter.BaseRecyclerAdapter;
import com.example.myapplication.adapter.RecyclerHolder;
import com.example.myapplication.model.Item;
import com.example.myapplication.transform.ChatTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xieH on 2016/12/1 0001.
 */
public class GlideActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<Item> itemList = new ArrayList<>();
    BaseRecyclerAdapter<Item> baseRecyclerAdapter;


    private int[] cardIds = new int[]{R.drawable.card_01, R.drawable.card_02, R.drawable.card_03,
            R.drawable.card_04, R.drawable.card_05, R.drawable.card_06, R.drawable.card_07,
            R.drawable.card_08, R.drawable.card_09, R.drawable.card_10};

    private String[] urls = new String[]{
            "https://www.eggou.com/data/im/2017/08/02/1501651493.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501660286.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501661173.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501661462.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501661576.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501663764.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501663932.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501664707.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501664843.jpeg",
            "https://www.eggou.com/data/im/2017/08/02/1501666126.jpeg",


            "http://img01.sogoucdn.com/app/a/100520093/60d2f4fe0275d790-fbe7539243950f9f-7f669dbeead0ad667f21be96b5efd843.jpg",
            "http://pic19.nipic.com/20120324/3484432_092618805000_2.jpg",
            "http://img3.tuniucdn.com/images/2011-03-29/L/LFXLzoSGG9g753SH.jpg",
            "http://pic3.nipic.com/20090603/2781538_100414093_2.jpg",
            "http://pic.nipic.com/2008-03-01/2008319174451_2.jpg",
            "http://img01.sogoucdn.com/app/a/100520093/013d20860a59d114-e452007590d91dbc-e655209caf5a45fc1143eefe707a62ab.jpg",
            "http://pic24.nipic.com/20121029/3822951_090444696000_2.jpg",
            "http://img4.duitang.com/uploads/item/201209/20/20120920165508_EuenZ.jpeg",
            "http://img01.sogoucdn.com/app/a/100520093/ac75323d6b6de243-503c0c74be6ae02f-bd063b042a12e0bf0776668bccfddea3.jpg",
            "http://img4.duitang.com/uploads/item/201209/20/20120920165508_EuenZ.jpeg",
            "http://oda6c9108.bkt.clouddn.com/poster_01.png",
            "http://oda6c9108.bkt.clouddn.com/poster_02.png",
            "http://oda6c9108.bkt.clouddn.com/poster_03.png",
            "http://oda6c9108.bkt.clouddn.com/poster_04.png",
            "http://oda6c9108.bkt.clouddn.com/poster_05.png",
            "http://oda6c9108.bkt.clouddn.com/poster_06.png",
            "http://oda6c9108.bkt.clouddn.com/poster_07.png",
            "http://oda6c9108.bkt.clouddn.com/poster_08.png",
            "http://oda6c9108.bkt.clouddn.com/poster_09.png",
            "http://oda6c9108.bkt.clouddn.com/poster_10.png",
            "http://oda6c9108.bkt.clouddn.com/poster_11.png",
            "http://oda6c9108.bkt.clouddn.com/poster_12.png",
            "http://oda6c9108.bkt.clouddn.com/poster_13.png",
            "http://oda6c9108.bkt.clouddn.com/poster_14.png",
            "http://oda6c9108.bkt.clouddn.com/poster_15.png",
            "http://oda6c9108.bkt.clouddn.com/poster_16.png",
            "http://oda6c9108.bkt.clouddn.com/poster_17.png",
            "http://oda6c9108.bkt.clouddn.com/poster_18.png",
            "http://oda6c9108.bkt.clouddn.com/poster_19.png",
            "http://oda6c9108.bkt.clouddn.com/poster_20.png",
            "http://oda6c9108.bkt.clouddn.com/poster_21.jpg",
            "http://oda6c9108.bkt.clouddn.com/poster_22.jpg",
            "http://oda6c9108.bkt.clouddn.com/poster_23.png",
            "http://oda6c9108.bkt.clouddn.com/poster_24.png",
            "http://oda6c9108.bkt.clouddn.com/poster_25.png",
            "http://oda6c9108.bkt.clouddn.com/poster_26.png",
            "http://oda6c9108.bkt.clouddn.com/poster_27.png",
            "http://oda6c9108.bkt.clouddn.com/poster_28.jpg",
            "http://oda6c9108.bkt.clouddn.com/poster_29.png",
            "http://oda6c9108.bkt.clouddn.com/poster_30.png",
            "http://oda6c9108.bkt.clouddn.com/poster_31.png",
            "http://oda6c9108.bkt.clouddn.com/poster_32.png",
            "http://oda6c9108.bkt.clouddn.com/poster_33.png",
            "http://oda6c9108.bkt.clouddn.com/poster_34.png",
            "http://oda6c9108.bkt.clouddn.com/poster_35.png",
            /////////////
            "http://oda6c9108.bkt.clouddn.com/poster_01.png",
            "http://oda6c9108.bkt.clouddn.com/poster_02.png",
            "http://oda6c9108.bkt.clouddn.com/poster_03.png",
            "http://oda6c9108.bkt.clouddn.com/poster_04.png",
            "http://oda6c9108.bkt.clouddn.com/poster_05.png",
            "http://oda6c9108.bkt.clouddn.com/poster_06.png",
            "http://oda6c9108.bkt.clouddn.com/poster_07.png",
            "http://oda6c9108.bkt.clouddn.com/poster_08.png",
            "http://oda6c9108.bkt.clouddn.com/poster_09.png",
            "http://oda6c9108.bkt.clouddn.com/poster_10.png",
            "http://oda6c9108.bkt.clouddn.com/poster_11.png",
            "http://oda6c9108.bkt.clouddn.com/poster_12.png",
            "http://oda6c9108.bkt.clouddn.com/poster_13.png",
            "http://oda6c9108.bkt.clouddn.com/poster_14.png",
            "http://oda6c9108.bkt.clouddn.com/poster_15.png",
            "http://oda6c9108.bkt.clouddn.com/poster_16.png",
            "http://oda6c9108.bkt.clouddn.com/poster_17.png",
            "http://oda6c9108.bkt.clouddn.com/poster_18.png",
            "http://oda6c9108.bkt.clouddn.com/poster_19.png",
            "http://oda6c9108.bkt.clouddn.com/poster_20.png",
            "http://oda6c9108.bkt.clouddn.com/poster_21.jpg",
            "http://oda6c9108.bkt.clouddn.com/poster_22.jpg",
            "http://oda6c9108.bkt.clouddn.com/poster_23.png",
            "http://oda6c9108.bkt.clouddn.com/poster_24.png",
            "http://oda6c9108.bkt.clouddn.com/poster_25.png",
            "http://oda6c9108.bkt.clouddn.com/poster_26.png",
            "http://oda6c9108.bkt.clouddn.com/poster_27.png",
            "http://oda6c9108.bkt.clouddn.com/poster_28.jpg",
            "http://oda6c9108.bkt.clouddn.com/poster_29.png",
            "http://oda6c9108.bkt.clouddn.com/poster_30.png",
            "http://oda6c9108.bkt.clouddn.com/poster_31.png",
            "http://oda6c9108.bkt.clouddn.com/poster_32.png",
            "http://oda6c9108.bkt.clouddn.com/poster_33.png",
            "http://oda6c9108.bkt.clouddn.com/poster_34.png",
            "http://oda6c9108.bkt.clouddn.com/poster_35.png"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        initView();

        setData();
    }

    public void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.glide_rv);

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        initAdapter();
        mRecyclerView.setAdapter(baseRecyclerAdapter);


//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                switch (newState) {
//                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
//                        ImageUtils.resumeRequests(GlideActivity.this); // 停止滑动时，恢复下载
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
//                        ImageUtils.resumeRequests(GlideActivity.this); // 滑动时，恢复下载
//                        break;
//                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
//                        ImageUtils.pauseRequests(GlideActivity.this); // 快速滑动时，暂停下载
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

//        mRecyclerView.setOnBottomListener(new SuperRecyclerView.OnBottomListener() {
//            @Override
//            public void onBottom() {
//                Toast.makeText(GlideActivity.this, "我是有底线的", Toast.LENGTH_LONG).show();
//            }
//        });

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (isSlideToBottom(recyclerView)) {
//                    Toast.makeText(GlideActivity.this, "我是有底线的", Toast.LENGTH_LONG).show();
//                }
//            }
//        });


        // 由于BaseRecyclerAdapter设置了itemView的监听，所以这里点击item没反应了,但滑动RecyclerView时还是可以有效触发的
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("h-----------------");
                return false;
            }
        });


        String url = "http://oda6c9108.bkt.clouddn.com/poster_35.png";

        // 对图片进行预加载，先缓存起来，后面调用时就直接从缓存中读取
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();


    }

    public void initAdapter() {
        baseRecyclerAdapter = new BaseRecyclerAdapter<Item>(mRecyclerView, itemList, R.layout.wx_chatting_item_to_image) {
            @Override
            public void convert(RecyclerHolder holder, Item item, int position, boolean isScrolling) {
//                ImageView mImageView = holder.getView(R.id.recyclerview_item_iv);
//                ImageUtils.loadImage(GlideActivity.this, mImageView, item.getUrl() + ImageUtils.THUMB_300X);

                //   ImageUtils.loadCornersImage(GlideActivity.this, mImageView, item.getResourceId());


                ImageView mImageView = holder.getView(R.id.chatting_item_image_iv);
//                ImageUtils.loadImage(GlideActivity.this, mImageView, item.getUrl());

                Glide.with(GlideActivity.this)
                        .load(item.getUrl())
                        .transform(new ChatTransformation(GlideActivity.this, R.drawable.chat_img_to_bg_mask_normal))
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(mImageView);
            }
        };
    }

    public void setData() {

        for (int i = 0; i < urls.length; i++) {
            Item item = new Item();

            //    item.setResourceId(cardIds[i]);
            item.setUrl(urls[i]);

            itemList.add(item);
        }

        baseRecyclerAdapter.refresh(itemList);
    }


    /**
     * 方法1
     * <p>
     * 是否滑到底部
     *
     * @return
     */
    public boolean isSlideToBottom(RecyclerView recyclerView) {

        System.out.println("extent---" + recyclerView.computeVerticalScrollExtent()); // 当前屏幕显示的区域高度     838
        System.out.println("offset---" + recyclerView.computeVerticalScrollOffset()); // 当前屏幕之前滑过的距离     0~8642
        System.out.println("range---" + recyclerView.computeVerticalScrollRange());   // 整个View控件的高度        9480

        if (recyclerView != null) {
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                    >= recyclerView.computeVerticalScrollRange())
                return true;
        }
        return false;
    }

    /**
     * 方法2
     * <p>
     * 原理跟方法1一样，都是根据computeVerticalScrollExtent()，computeVerticalScrollOffset()，computeVerticalScrollRange()来判断的
     *
     * @param recyclerView
     */
    public void isBottom(RecyclerView recyclerView) {

        boolean canDown = recyclerView.canScrollVertically(1);  // 表示是否能向上滚动，false表示已经滚动到底部
        boolean canUp = recyclerView.canScrollVertically(-1);   // 表示是否能向下滚动，false表示已经滚动到顶部

    }


    /**
     * 方法3
     * <p>
     * 是否滑到底部
     * <p>
     * 当屏幕中最后一个子项lastVisibleItemPosition等于所有子项个数totalItemCount - 1，那么RecyclerView就到达了底部。
     * 但是，我在这种方法中发现了极为极端的情况，就是当totalItemCount等于1，而这个子项的高度比屏幕还要高
     *
     * @return
     */
    public static boolean isVisBottom(RecyclerView recyclerView) {

        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

        //当前屏幕所看到的子项个数
        int visibleItemCount = layoutManager.getChildCount();

        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();

        //RecyclerView的滑动状态
        int state = recyclerView.getScrollState();
        if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
            return true;
        } else {
            return false;
        }
    }
}
