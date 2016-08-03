package com.yuyh.sprintnba.ui.view;

import com.yuyh.sprintnba.http.bean.player.StatsRank;
import com.yuyh.sprintnba.app.Constant;
import com.yuyh.sprintnba.ui.view.base.BaseView;

import java.util.List;
import java.util.Map;

public interface StatsRankView extends BaseView{

    void showStatsRank(Map<String, Constant.TabType> tab, Map<String, Constant.StatType> stat);

    void showStatList(List<StatsRank.RankItem> list);
}
