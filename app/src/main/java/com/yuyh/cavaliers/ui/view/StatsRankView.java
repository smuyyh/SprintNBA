package com.yuyh.cavaliers.ui.view;

import com.yuyh.cavaliers.http.constant.Constant;

import java.util.Map;

public interface StatsRankView {

    void showStatsRank(Map<String, Constant.TabType> tab, Map<String, Constant.StatType> stat);

}
