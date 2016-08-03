package com.yuyh.sprintnba.ui.Interactor.impl;

import com.yuyh.sprintnba.ui.Interactor.StatsRankInteractor;
import com.yuyh.sprintnba.app.Constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatsRankInteractorImpl implements StatsRankInteractor {

    @Override
    public Map<String, Constant.TabType> getTabs() {
        Map<String, Constant.TabType> map = new LinkedHashMap<>();
        map.put("每日", Constant.TabType.EVERYDAY);
        map.put("季后赛", Constant.TabType.FINAL);
        map.put("常规赛", Constant.TabType.NORMAL);
        return map;
    }

    @Override
    public Map<String, Constant.StatType> getStats() {
        Map<String, Constant.StatType> map = new LinkedHashMap<>();
        map.put("得分", Constant.StatType.POINT);
        map.put("篮板", Constant.StatType.REBOUND);
        map.put("助攻", Constant.StatType.ASSIST);
        map.put("抢断", Constant.StatType.STEAL);
        map.put("盖帽", Constant.StatType.BLOCK);
        return map;
    }
}
