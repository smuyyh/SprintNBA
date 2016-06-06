package com.yuyh.library.utils;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

/**
 * @author yuyh.
 * @date 16/4/9.
 */
public class WidgetUtils {

    /**
     * 测量控件
     *
     * @param view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        view.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 测量控件高度
     *
     * @param view
     * @return
     */
    public static int getMeasuredHeight(View view) {
        measureView(view);
        return view.getMeasuredHeight();
    }

    /**
     * 测量控件宽度
     *
     * @param view
     * @return
     */
    public static int getMeasuredWidth(View view) {
        measureView(view);
        return view.getMeasuredWidth();
    }
}
