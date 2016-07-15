package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;

import com.yuyh.sprintnba.event.RefreshCompleteEvent;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.match.MatchStat;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.MatchDataView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchDataPresenter implements Presenter {

    private Context context;
    private MatchDataView dataView;

    public MatchDataPresenter(Context context, MatchDataView dataView) {
        this.context = context;
        this.dataView = dataView;
    }

    @Override
    public void initialized() {
    }

    public void getMatchStats(String mid, String tabType) {
        TencentService.getMatchStat(mid, tabType, new RequestCallback<MatchStat>() {
            @Override
            public void onSuccess(MatchStat matchStat) {
                MatchStat.MatchStatInfo data = matchStat.data;
                if (data != null && data.stats != null) {
                    List<MatchStat.MatchStatInfo.StatsBean> stats = data.stats;
                    for (MatchStat.MatchStatInfo.StatsBean bean : stats) {
                        if (bean.type.equals("12")) {
                            if (bean.goals != null && !bean.goals.isEmpty()) {
                                dataView.showMatchPoint(bean.goals, data.teamInfo);
                            }
                        } else if (bean.type.equals("14")) {
                            if (bean.teamStats != null && !bean.teamStats.isEmpty()) {
                                dataView.showTeamStatistics(bean.teamStats);
                            }
                        }
                    }
                } else {
                    dataView.showError("暂无数据");
                }
                EventBus.getDefault().post(new RefreshCompleteEvent());
            }

            @Override
            public void onFailure(String message) {
                dataView.showError(message);
                EventBus.getDefault().post(new RefreshCompleteEvent());
            }
        });
    }
}
