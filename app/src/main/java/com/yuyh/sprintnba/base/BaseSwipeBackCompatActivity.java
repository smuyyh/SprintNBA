package com.yuyh.sprintnba.base;

import android.graphics.Color;
import android.os.Bundle;

import com.jude.swipbackhelper.SwipeBackHelper;

public abstract class BaseSwipeBackCompatActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(0.2f)//0.2 mean left 20% of screen can touch to begin swipe.
                .setSwipeSensitivity(1)//sensitiveness of the gesture。0:slow  1:sensitive
                .setScrimColor(Color.TRANSPARENT)//color of Scrim below the activity
                .setClosePercent(0.8f)//close activity when swipe over this
                .setSwipeRelateEnable(true)//if should move together with the following Activity
                .setSwipeRelateOffset(500)//the Offset of following Activity when setSwipeRelateEnable(true)
                .setDisallowInterceptTouchEvent(false);
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
