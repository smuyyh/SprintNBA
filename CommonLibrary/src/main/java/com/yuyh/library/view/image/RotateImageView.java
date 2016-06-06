package com.yuyh.library.view.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * 自动旋转的imageveiw，可用于Loadding
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class RotateImageView extends ImageView {

    private RotateAnimation mAnimation;
    private boolean mIsHasAnimation;

    public RotateImageView(Context context) {
        super(context);
    }

    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setRotateAnimation();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearRotateAnimation();
    }

    /**
     * 设置动画效果
     */
    private void setRotateAnimation() {
        if (mIsHasAnimation == false && getWidth() > 0 && getVisibility() == View.VISIBLE) {
            mIsHasAnimation = true;
            mAnimation = new RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mAnimation.setDuration(1000L);
            mAnimation.setInterpolator(new LinearInterpolator());
            mAnimation.setRepeatCount(-1);
            mAnimation.setRepeatMode(Animation.RESTART);
            setAnimation(mAnimation);
        }
    }

    /**
     * 清除动画
     */
    private void clearRotateAnimation() {
        if (mIsHasAnimation) {
            mIsHasAnimation = false;
            setAnimation(null);
            mAnimation = null;
        }
    }

    /**
     * 开启动画
     */
    public void startAnimation() {
        if (mIsHasAnimation) {
            super.startAnimation(mAnimation);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0) {
            setRotateAnimation();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            clearRotateAnimation();
        } else {
            setRotateAnimation();
        }
    }
}
