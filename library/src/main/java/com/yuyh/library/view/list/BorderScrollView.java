package com.yuyh.library.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 到达顶部或底部触发事件的ScrollView
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class BorderScrollView extends ScrollView {

    private OnBorderListener onBorderListener;
    private View contentView;

    public BorderScrollView(Context context) {
        super(context);
    }

    public BorderScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        doOnBorderListener();
    }

    /**
     * 须调用改方法设置监听
     *
     * @param onBorderListener
     */
    public void setOnBorderListener(final OnBorderListener onBorderListener) {
        this.onBorderListener = onBorderListener;
        if (onBorderListener == null) {
            return;
        }

        if (contentView == null) {
            contentView = getChildAt(0);
        }
    }

    /**
     * OnBorderListener，滚动到顶部或底部的监听
     */
    public interface OnBorderListener {
        void onBottom();

        void onTop();
    }

    private void doOnBorderListener() {
        if (contentView != null && contentView.getMeasuredHeight() <= getScrollY() + getHeight()) {
            if (onBorderListener != null) {
                onBorderListener.onBottom();
            }
        } else if (getScrollY() == 0) {
            if (onBorderListener != null) {
                onBorderListener.onTop();
            }
        }
    }
}
