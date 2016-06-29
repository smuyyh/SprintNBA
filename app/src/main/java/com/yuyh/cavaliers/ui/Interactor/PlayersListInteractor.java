
package com.yuyh.cavaliers.ui.Interactor;

import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.http.api.RequestCallback;

public interface PlayersListInteractor {

    void getAllPlayers(RequestCallback<Players> callback);
}
