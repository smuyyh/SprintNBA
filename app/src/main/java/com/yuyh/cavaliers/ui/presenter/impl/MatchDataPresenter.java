package com.yuyh.cavaliers.ui.presenter.impl;

import android.content.Context;

import com.yuyh.cavaliers.ui.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.MatchDataView;

/**
 * @author yuyh.
 * @date 16/7/2.
 */
public class MatchDataPresenter implements Presenter {

    private Context context;
    private MatchDataView forwardView;

    public MatchDataPresenter(Context context, MatchDataView forwardView) {
        this.context = context;
        this.forwardView = forwardView;
    }

    @Override
    public void initialized() {
    }
}
