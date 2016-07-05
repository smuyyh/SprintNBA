package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.http.api.tencent.TencentService;
import com.yuyh.cavaliers.http.bean.player.StatsRank;
import com.yuyh.cavaliers.http.constant.Constant;
import com.yuyh.cavaliers.ui.Interactor.StatsRankInteractor;
import com.yuyh.cavaliers.ui.Interactor.impl.StatsRankInteractorImpl;
import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.StatsRankView;

import java.util.List;

/**
 * @author yuyh.
 * @date 2016/6/22.
 */
public class StatsRankPresenterImpl implements Presenter {

    private Context context;
    private StatsRankView rankView;
    private StatsRankInteractor interactor;

    public StatsRankPresenterImpl(Context context, StatsRankView rankView) {
        this.context = context;
        this.rankView = rankView;
        interactor = new StatsRankInteractorImpl();
    }


    @Override
    public void initialized() {
        rankView.showStatsRank(interactor.getTabs(), interactor.getStats());
    }

    public void requestStatsRank(Constant.StatType curStat, Constant.TabType curTab) {
        TencentService.getStatsRank(curStat, 20, curTab, "2015", true, new RequestCallback<StatsRank>() {
            @Override
            public void onSuccess(StatsRank statsRank) {
                List<StatsRank.RankItem> list = statsRank.rankList;
                if (list != null && !list.isEmpty()) {
                    rankView.showStatList(list);
                } else {
                    rankView.showError("暂无数据");
                }
            }

            @Override
            public void onFailure(String message) {
                rankView.showError(message);
            }
        });
    }
}
