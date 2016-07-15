package com.yuyh.sprintnba.ui.Interactor.impl;

import com.yuyh.sprintnba.ui.Interactor.PlayersListInteractor;
import com.yuyh.sprintnba.http.api.tencent.TencentService;
import com.yuyh.sprintnba.http.bean.player.Players;
import com.yuyh.sprintnba.http.api.RequestCallback;

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
