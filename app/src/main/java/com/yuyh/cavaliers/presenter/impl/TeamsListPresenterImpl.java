package com.yuyh.cavaliers.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.cavaliers.Interactor.TeamsListInteractor;
import com.yuyh.cavaliers.Interactor.impl.TeamsListListInteractorImpl;
import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.api.RequestCallback;
import com.yuyh.cavaliers.presenter.Presenter;
import com.yuyh.cavaliers.ui.view.TeamsView;

/**
 * @author yuyh.
 * @date 16/6/7.
 */
public class TeamsListPresenterImpl implements Presenter {

    private Context mContext = null;
    private TeamsView mTeamsView = null;
    private TeamsListInteractor mTeamsListInteractor = null;

    public TeamsListPresenterImpl(Context context, @NonNull TeamsView teamsView) {
        mContext = context;
        this.mTeamsView = teamsView;
        mTeamsListInteractor = new TeamsListListInteractorImpl();
    }

    @Override
    public void initialized() {
        mTeamsListInteractor.getAllTeams(new RequestCallback<Teams>() {
            @Override
            public void onSuccess(Teams teams) {
                mTeamsView.showAllTeams(teams.data);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
