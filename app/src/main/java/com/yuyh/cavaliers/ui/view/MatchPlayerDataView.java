package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.bean.match.MatchStat;
import com.yuyh.cavaliers.ui.view.base.BaseView;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/7/5.
 */
public interface MatchPlayerDataView extends BaseView {

    void showPlayerData(List<MatchStat.MatchStatInfo.StatsBean.PlayerStats> playerStatses);
}
