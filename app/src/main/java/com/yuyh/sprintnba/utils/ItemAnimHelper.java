package com.yuyh.sprintnba.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.yuyh.sprintnba.R;

/**
 * @author yuyh.
 * @date 2016/7/25.
 */
public class ItemAnimHelper {

    private int mLastPosition = -1;

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }
}
