package com.yuyh.sprintnba.ui.presenter.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import com.yuyh.sprintnba.ui.Interactor.TeamsListInteractor;
import com.yuyh.sprintnba.ui.Interactor.impl.TeamsListListInteractorImpl;
import com.yuyh.sprintnba.http.bean.player.Teams;
import com.yuyh.sprintnba.http.api.RequestCallback;
import com.yuyh.sprintnba.ui.presenter.Presenter;
import com.yuyh.sprintnba.ui.view.TeamsView;

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
