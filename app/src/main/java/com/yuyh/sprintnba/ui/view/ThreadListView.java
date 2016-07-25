package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.http.bean.forum.ThreadListData;
import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface ThreadListView extends BaseView {

    void onScrollToTop();

    void showThreadList(List<ThreadListData.ThreadInfo> forumInfoList, boolean isRefresh);

    void showThreadInfo(ForumsData.Forum forum);

    void onFloatingVisibility(int visibility);

    void onLoadCompleted(boolean hasMore);

    void onRefreshCompleted();

}
