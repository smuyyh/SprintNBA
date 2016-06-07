package com.yuyh.cavaliers.recycleview;

import android.view.View;

/**
 * Created by Kyrie.Y on 2016/6/6.
 */
public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View view, int position, T data);
}
