
package com.yuyh.sprintnba.ui.Interactor;

import com.yuyh.sprintnba.http.constant.Constant;

import java.util.Map;

public interface StatsRankInteractor {

    Map<String, Constant.TabType> getTabs();

    Map<String, Constant.StatType> getStats();
}
