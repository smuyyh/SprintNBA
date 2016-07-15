package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.forum.ForumsData;
import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/6/25.
 */
public interface ForumListView extends BaseView{

    void showForumList(List<ForumsData.Forum> forumList);
}
