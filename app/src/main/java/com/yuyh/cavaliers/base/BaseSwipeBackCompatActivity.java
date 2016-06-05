package com.yuyh.cavaliers.base;

import android.graphics.Color;
import android.os.Bundle;

import com.yuyh.library.swipeback.SwipeBackHelper;

public abstract class BaseSwipeBackCompatActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);

        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeSensitivity(0.5f)
                .setSwipeSensitivity(1)
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)
                .setScrimColor(Color.TRANSPARENT);//底层阴影颜色;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

}
