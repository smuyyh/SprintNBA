package com.yuyh.library.view.common;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.lang.ref.WeakReference;

/**
 * 弹窗之前检测Activity是否销毁
 *
 * @author yuyh.
 * @date 16/4/11.
 */
public class SafePopupWindow extends PopupWindow {
    private WeakReference<Context> mContext = null;

    public SafePopupWindow(Context context) {
        super(context);
        mContext = new WeakReference<>(context);
    }

    public SafePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = new WeakReference<>(context);
    }

    public SafePopupWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = new WeakReference<>(context);
    }

    public SafePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        mContext = new WeakReference<>(contentView.getContext());
    }

    public SafePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        mContext = new WeakReference<>(contentView.getContext());
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        Context c = mContext.get();
        if (c instanceof Activity && ((Activity) c).isFinishing()) {
            return;
        }
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void showAsDropDown(View anchor) {
        Context c = mContext.get();
        if (c instanceof Activity && ((Activity) c).isFinishing()) {
            return;
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        Context c = mContext.get();
        if (c instanceof Activity && ((Activity) c).isFinishing()) {
            return;
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }
}
