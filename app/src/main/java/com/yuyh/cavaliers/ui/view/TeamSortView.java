package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.player.TeamsRank;
import com.yuyh.cavaliers.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public interface TeamSortView extends BaseView {

    void showTeamSort(List<TeamsRank.TeamBean> list);
}
