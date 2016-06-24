package com.yuyh.cavaliers.Interactor.impl;

import com.yuyh.cavaliers.Interactor.AllTeamsInteractor;
import com.yuyh.cavaliers.http.Request;
import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;

/**
 * @author yuyh.
 * @date 16/6/24.
 */
public class AllTeamsInteractorImpl implements AllTeamsInteractor {

    @Override
    public void getAllTeams(GetBeanCallback<Teams> callback) {
        Request.getTeamList(false, callback);
    }
}
