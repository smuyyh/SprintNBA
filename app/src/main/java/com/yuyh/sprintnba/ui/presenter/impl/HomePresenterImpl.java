package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.sprintnba.ui.Interactor.HomeInteractor;
import com.yuyh.sprintnba.ui.Interactor.impl.HomeInteractorImpl;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.HomeView;

/**
 * @author yuyh.
 * @date 16/6/7.
 */
public class HomePresenterImpl implements Presenter {

    private Context mContext = null;
    private HomeView mHomeView = null;
    private HomeInteractor mHomeInteractor = null;

    public HomePresenterImpl(Context context, @NonNull HomeView homeView) {

        mContext = context;
        mHomeView = homeView;
        mHomeInteractor = new HomeInteractorImpl();
    }

    @Override
    public void initialized() {
        mHomeView.initializeViews(mHomeInteractor.getPagerFragments(), mHomeInteractor.getNavigationList(mContext));
    }
}
