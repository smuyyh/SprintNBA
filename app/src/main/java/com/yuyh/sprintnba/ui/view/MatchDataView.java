package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public interface MatchDataView extends BaseView {

    void showMatchPoint(List<MatchStat.Goals> list, MatchStat.MatchTeamInfo teamInfo);

    void showTeamStatistics(List<MatchStat.TeamStats> teamStats);

    void showGroundStats(MatchStat.GroundStats groundStats);
}
