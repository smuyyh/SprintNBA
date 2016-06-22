package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.Interactor.StatsRankInteractor;
import com.yuyh.cavaliers.Interactor.impl.StatsRankInteractorImpl;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.StatsRankView;

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
}
