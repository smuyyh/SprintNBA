package com.yuyh.cavaliers.Interactor.impl;

import com.yuyh.cavaliers.Interactor.PlayersListInteractor;
import com.yuyh.cavaliers.http.api.nba.TencentService;
import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.http.api.RequestCallback;

/**
 * @author yuyh.
 * @date 16/6/24.
 */
public class PlayersListListInteractorImpl implements PlayersListInteractor {

    @Override
    public void getAllPlayers(RequestCallback<Players> callback) {
        TencentService.getPlayerList(false, callback);
    }
}
