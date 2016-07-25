package com.yuyh.sprintnba.support;

import android.view.View;
import android.widget.AbsListView;

public class OnLvScrollListener implements OnBottomListener, AbsListView.OnScrollListener {

    private int mListViewHeight = 0;

    public OnLvScrollListener(int mListViewHeight) {
        this.mListViewHeight = mListViewHeight;
    }

    @Override
    public void onBottom() {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(final AbsListView mListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0) {
            View firstVisibleItemView = mListView.getChildAt(0);
            if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) { // 顶部
            }
        } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {         // 底部
            View lastVisibleItemView = mListView.getChildAt(mListView.getChildCount() - 1);
            if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == mListViewHeight) {
                onBottom();
            }
        }
    }
}