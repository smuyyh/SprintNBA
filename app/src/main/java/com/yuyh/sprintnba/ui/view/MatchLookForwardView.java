package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.match.MatchStat;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public interface MatchLookForwardView {

    void showTeamInfo(MatchStat.MatchTeamInfo info);

    void showMaxPlayer(List<MatchStat.MaxPlayers> maxPlayers);

    void showHistoryMatchs(List<MatchStat.VS> vs);

    void showRecentMatchs(MatchStat.TeamMatchs teamMatches);

    void showFutureMatchs(MatchStat.TeamMatchs teamMatches);

    void showError(String message);

}
