package com.example.myapplication.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.ui.tab2.MaterialDesign11Activity;

/**
 * Created by xieH on 2018/1/24 0024.
 */
public class MaterialDesignFragment extends LazyFragment {


    private Button mButton;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_material_design;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        mButton = (Button) getContentView().findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MaterialDesign11Activity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                } else {
                    startActivity(intent);
                }

//                // 平滑地将多个控件平移的过渡到第二个activity
//                // 如下，将两个控件R.id.imageView与R.id.textView平移
//                // 根据需要，可以平移更多的控件。
//                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        getActivity(),
//                        new Pair<View, String>(getContentView().findViewById(R.id.imageView), MaterialDesign11Activity.VIEW_IMAGE),
//                        new Pair<View, String>(getContentView().findViewById(R.id.textView), MaterialDesign11Activity.VIEW_TEXT));
//
//                // Now we can start the Activity, providing the activity options as a bundle
//                ActivityCompat.startActivity(getActivity(), intent, activityOptions.toBundle());

            }
        });
    }
}
