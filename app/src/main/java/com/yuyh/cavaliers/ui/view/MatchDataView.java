package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.yuyh.cavaliers.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public interface MatchDataView extends BaseView {

    void showMatchPoint(List<MatchStat.MatchStatInfo.StatsBean.Goals> list, MatchStat.MatchStatInfo.MatchTeamInfo teamInfo);

    void showTeamStatistics(List<MatchStat.MatchStatInfo.StatsBean.TeamStats> teamStats);
}
