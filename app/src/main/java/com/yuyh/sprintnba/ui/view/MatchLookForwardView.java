package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.match.MatchStat;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public interface MatchLookForwardView {

    void showTeamInfo(MatchStat.MatchStatInfo.MatchTeamInfo info);

    void showMaxPlayer(List<MatchStat.MatchStatInfo.StatsBean.MaxPlayers> maxPlayers);

    void showHistoryMatchs(List<MatchStat.MatchStatInfo.StatsBean.VS> vs);

    void showRecentMatchs(MatchStat.MatchStatInfo.StatsBean.TeamMatchs teamMatches);

    void showFutureMatchs(MatchStat.MatchStatInfo.StatsBean.TeamMatchs teamMatches);

    void showError(String message);

}
