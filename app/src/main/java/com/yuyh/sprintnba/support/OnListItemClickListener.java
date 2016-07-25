package com.yuyh.sprintnba.support;

import android.view.View;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public interface OnListItemClickListener<T> {
    void onItemClick(View view, int position, T data);
}
