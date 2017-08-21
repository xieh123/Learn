package com.example.myapplication.ui.tab1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.adapter.FlipAdapter;

import se.emilsjolander.flipview.FlipView;
import se.emilsjolander.flipview.OverFlipMode;

/**
 * Created by xieH on 2017/6/7 0007.
 */
public class FlipViewActivity extends AppCompatActivity implements FlipAdapter.Callback, FlipView.OnFlipListener, FlipView.OnOverFlipListener {

    private FlipView mFlipView;
    private FlipAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flipview);

        mFlipView = (FlipView) findViewById(R.id.flip_view);
        mAdapter = new FlipAdapter(this);
        mAdapter.setCallback(this);
        mFlipView.setAdapter(mAdapter);
        mFlipView.setOnFlipListener(this);
        mFlipView.peakNext(false);
        mFlipView.setOverFlipMode(OverFlipMode.RUBBER_BAND);
        mFlipView.setEmptyView(findViewById(R.id.empty_view));
        mFlipView.setOnOverFlipListener(this);


//        mAdapter.addItemsBefore(5);
    }

    @Override
    public void onPageRequested(int page) {
        mFlipView.smoothFlipTo(page);
    }

    @Override
    public void onFlippedToPage(FlipView v, int position, long id) {
        Log.i("pageflip", "Page: " + position);
        if (position > mFlipView.getPageCount() - 3 && mFlipView.getPageCount() < 30) {
            mAdapter.addItems(5);
        }
    }

    @Override
    public void onOverFlip(FlipView v, OverFlipMode mode,
                           boolean overFlippingPrevious, float overFlipDistance,
                           float flipDistancePerPage) {
        Log.i("overflip", "overFlipDistance = " + overFlipDistance);
    }

}
