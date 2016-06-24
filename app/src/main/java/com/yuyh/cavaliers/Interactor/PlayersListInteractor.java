
package com.yuyh.cavaliers.Interactor;

import com.yuyh.cavaliers.http.bean.player.Players;
import com.yuyh.cavaliers.http.callback.GetBeanCallback;

public interface PlayersListInteractor {

    void getAllPlayers(GetBeanCallback<Players> callback);
}
