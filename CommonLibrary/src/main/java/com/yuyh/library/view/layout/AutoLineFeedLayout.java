package com.yuyh.library.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义ChildView自动换行的Layout
 *
 * @author yuyh.
 * @date 16/9/24.
 */
public class AutoLineFeedLayout extends ViewGroup {

    private static final int PADDING_HOR = 10;// 水平方向padding
    private static final int PADDING_VERTICAL = 5;// 垂直方向padding
    private static final int SIDE_MARGIN = 10;// 左右间距
    private static final int TEXT_MARGIN = 10;

    public AutoLineFeedLayout(Context context) {
        super(context);
    }

    public AutoLineFeedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        int x = 0;
        int y = 0;
        int row = 0;

        for (int index = 0; index < childCount; index++) {
            final View child = getChildAt(index);
            if (child.getVisibility() != View.GONE) {
                child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
                // 此处增加onlayout中的换行判断，用于计算所需的高度
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width + TEXT_MARGIN;
                y = row * (height + TEXT_MARGIN) + height;
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * (height + TEXT_MARGIN) + height;
                }
            }
        }
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        int maxWidth = r - l;
        int x = 0;
        int y = 0;
        int row = 0;
        for (int i = 0; i < childCount; i++) {
            final View child = this.getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                int height = child.getMeasuredHeight();
                x += width + TEXT_MARGIN;
                y = row * (height + TEXT_MARGIN) + height;
                if (x > maxWidth) {
                    x = width;
                    row++;
                    y = row * (height + TEXT_MARGIN) + height;
                }
                child.layout(x - width, y - height, x, y);
            }
        }
    }
}