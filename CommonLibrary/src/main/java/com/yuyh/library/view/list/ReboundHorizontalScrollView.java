package com.yuyh.library.view.list;

import android.content.Context;
import android.graphics.Rect;  
import android.util.AttributeSet;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.animation.TranslateAnimation;  
import android.widget.HorizontalScrollView;  
  
public class ReboundHorizontalScrollView extends HorizontalScrollView {
    private View inner;  
    private Rect normal = new Rect();  
    private float x;  
  
    public ReboundHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);  
    }  
  
    public ReboundHorizontalScrollView(Context context) {
        super(context);  
    }  
  
    @Override  
    protected void onFinishInflate() {  
        if (getChildCount() > 0) {  
            inner = getChildAt(0);  
        }  
        super.onFinishInflate();  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
        if (ev == null) {  
            return super.onTouchEvent(ev);  
        } else {  
            commOnTouchEvent(ev);  
        }  
        return super.onTouchEvent(ev);  
    }  
  
    private void commOnTouchEvent(MotionEvent ev) {  
        int action = ev.getAction();  
        switch (action) {  
        case MotionEvent.ACTION_DOWN:  
            x = ev.getX();  
            break;  
        case MotionEvent.ACTION_UP:  
            if (isNeedAnimation()) {  
                animation();  
            }  
            break;  
        case MotionEvent.ACTION_MOVE:  
            final float preX = x;  
            float nowX = ev.getX();  
            int distanceX = (int) (preX - nowX);  
            scrollBy(distanceX, 0);  
            x = nowX;  
            if (isNeedMove()) {  
                if (normal.isEmpty()) {  
                    normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());  
                }  
                inner.layout(inner.getLeft() - distanceX, inner.getTop(), inner.getRight() - distanceX, inner.getBottom());  
            }  
  
            break;  
  
        default:  
            break;  
        }  
    }  
  
    private void animation() {  
        TranslateAnimation mTranslateAnimation = new TranslateAnimation(inner.getLeft(), 0, normal.left, 0);  
        mTranslateAnimation.setDuration(50);  
        inner.setAnimation(mTranslateAnimation);  
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);  
        normal.setEmpty();  
  
    }  
  
    private boolean isNeedAnimation() {  
        return !normal.isEmpty();  
    }  
  
    private boolean isNeedMove() {  
        int offset = inner.getMeasuredWidth() - getWidth();  
        int scrollX = getScrollX();  
        if (scrollX == 0 || offset == scrollX)  
            return true;  
        return false;  
    }  
}  