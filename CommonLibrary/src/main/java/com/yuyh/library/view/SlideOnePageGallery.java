package com.yuyh.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * 每次只滑动一页的Gallery
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class SlideOnePageGallery extends Gallery {
    public SlideOnePageGallery(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SlideOnePageGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideOnePageGallery(Context context) {
        super(context);
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int kEvent;
        if (isScrollingLeft(e1, e2)) {
            kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        } else {
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }

        onKeyDown(kEvent, null);
        return true;
    }
}
