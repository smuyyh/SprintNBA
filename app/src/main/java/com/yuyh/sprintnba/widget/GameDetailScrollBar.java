package com.yuyh.sprintnba.widget;

import android.content.Context;

import com.yuyh.library.view.viewpager.indicator.slidebar.ColorBar;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class GameDetailScrollBar extends ColorBar {

    public GameDetailScrollBar(Context context, int color, int height) {
        super(context, color, height);
    }

    public GameDetailScrollBar(Context context, int color, int height, Gravity gravity) {
        super(context, color, height, gravity);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
}
