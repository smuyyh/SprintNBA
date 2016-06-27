package com.yuyh.cavaliers.Interactor.impl;

import com.yuyh.cavaliers.Interactor.TeamsListInteractor;
import com.yuyh.cavaliers.http.api.nba.TencentService;
import com.yuyh.cavaliers.http.bean.player.Teams;
import com.yuyh.cavaliers.http.api.RequestCallback;

/**
 * @author yuyh.
 * @date 16/6/24.
 */
public class TeamsListListInteractorImpl implements TeamsListInteractor {

    @Override
    public void getAllTeams(RequestCallback<Teams> callback) {
        TencentService.getTeamList(false, callback);
    }
}
