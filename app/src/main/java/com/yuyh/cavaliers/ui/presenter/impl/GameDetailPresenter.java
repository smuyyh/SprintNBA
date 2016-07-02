package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.GameDetailView;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class GameDetailPresenter implements Presenter {

    private Context context;
    private GameDetailView detailView;

    public GameDetailPresenter(Context context, GameDetailView detailView) {
        this.context = context;
        this.detailView = detailView;
    }

    @Override
    public void initialized() {
        String names[] = new String[]{"比赛前瞻", "图文直播"};
        detailView.showTabViewPager(names);
    }
}
