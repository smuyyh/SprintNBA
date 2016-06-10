package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.cavaliers.Interactor.NBANewsInteractor;
import com.yuyh.cavaliers.Interactor.impl.NBAVidepInteractorImpl;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.NBANewsView;

/**
 * @author yuyh.
 * @date 16/6/7.
 */
public class NBAVideoPresenterImpl implements Presenter {

    private Context mContext = null;
    private NBANewsView mNewsView = null;
    private NBANewsInteractor mNewsInteractor = null;

    public NBAVideoPresenterImpl(Context context, @NonNull NBANewsView newsView) {
        mContext = context;
        mNewsView = newsView;
        mNewsInteractor = new NBAVidepInteractorImpl();
    }

    @Override
    public void initialized() {
        mNewsView.initializeViews(mNewsInteractor.getTabs());
    }
}
