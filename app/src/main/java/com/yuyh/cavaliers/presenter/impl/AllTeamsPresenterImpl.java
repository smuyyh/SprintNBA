package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.cavaliers.Interactor.AllTeamsInteractor;
import com.yuyh.cavaliers.Interactor.impl.AllTeamsInteractorImpl;
import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.AllTeamsView;

/**
 * @author yuyh.
 * @date 16/6/7.
 */
public class AllTeamsPresenterImpl implements Presenter {

    private Context mContext = null;
    private AllTeamsView mAllTeamsView = null;
    private AllTeamsInteractor mAllTeamsInteractor = null;

    public AllTeamsPresenterImpl(Context context, @NonNull AllTeamsView allTeamsView) {
        mContext = context;
        this.mAllTeamsView = allTeamsView;
        mAllTeamsInteractor = new AllTeamsInteractorImpl();
    }

    @Override
    public void initialized() {
        mAllTeamsInteractor.getAllTeams(new GetBeanCallback<Teams>() {
            @Override
            public void onSuccess(Teams teams) {
                mAllTeamsView.showAllTeams(teams.data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
