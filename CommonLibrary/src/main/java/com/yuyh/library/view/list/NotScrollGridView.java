package com.yuyh.library.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 全部展开的GridView，解决与ScrollView的冲突
 * 由于这两款控件都自带滚动条，嵌套便会出问题，即GridView或ListView会显示不全。
 *
 * @author yuyh.
 * @date 16/4/10.
 */
public class NotScrollGridView extends GridView {
    public NotScrollGridView(Context context) {
        super(context);
    }

    public NotScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
