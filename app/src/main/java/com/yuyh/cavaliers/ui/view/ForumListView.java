package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.forum.ForumsData;
import com.yuyh.cavaliers.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface ForumListView extends BaseView{

    void showForumList(List<ForumsData.Forum> forumList);
}
