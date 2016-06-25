package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.forum.ForumsData;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface ForumListView {

    void showForumList(List<ForumsData.Forum> forumList);
}
